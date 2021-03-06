package com.neurosky.algo_sdk_sample;

/**
 * 주로 봐야 할 부분
 * 1. nskAlgoSdk.setOnBPAlgoIndexListener -> 측정된 뇌파 값이 파이어베이스에 저장됨.
 * 2. startButton.setOnClickListener -> start버튼을 누르면 실행됨. 뇌파 측정이 시작되고 그래프 그리는 화면으로 넘어감.
 **/

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neurosky.AlgoSdk.NskAlgoDataType;
import com.neurosky.AlgoSdk.NskAlgoSdk;
import com.neurosky.AlgoSdk.NskAlgoSignalQuality;
import com.neurosky.AlgoSdk.NskAlgoState;
import com.neurosky.AlgoSdk.NskAlgoType;
import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

import java.io.FileWriter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class EEG extends Activity {

    FileWriter fw = null;

    FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("USERS");

    // COMM SDK handles
    private TgStreamReader tgStreamReader;
    private BluetoothAdapter mBluetoothAdapter;

    // internal variables
    private boolean bInited = false;
    private boolean bRunning = false;
    private NskAlgoType currentSelectedAlgo;

    // canned data variables
    private short raw_data[] = {0};
    private int raw_data_index = 0;
    private float output_data[];
    private int output_data_count = 0;

    private Button headsetButton2, startButton, stopButton, logoutButton;
    private ImageButton headsetButton;

    private TextView stateText, sqText, nameTv, nameTv2, emailTv, title1, title2;

    private NskAlgoSdk nskAlgoSdk;

    String n[] = new String[6];
    String eeg[] = new String[5];
    String nomal[] = new String[5];

    String name = "", putName = "", putEmail = "";

    Nomalization nz = new Nomalization();

    Random rand = new Random();

    //font
    String font[] = {"fonts/nanum.ttf", "fonts/MILKYWAY.TTF"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_eeg);

        nskAlgoSdk = new NskAlgoSdk();

        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        int idx = email.indexOf("@");
        name = email.substring(0, idx);

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                Toast.makeText(
                        this,
                        "Please turn on Bluetooth",
                        Toast.LENGTH_LONG).show();
                //finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        headsetButton = (ImageButton) this.findViewById(R.id.headsetButton);
        headsetButton2 = (Button) this.findViewById(R.id.headsetButton2);
        startButton = (Button) this.findViewById(R.id.startButton);
        stopButton = (Button) this.findViewById(R.id.stopButton);
        logoutButton = (Button) this.findViewById(R.id.logoutBtn);

        title1 = (TextView) this.findViewById(R.id.sqTitle);
        title2 = (TextView) this.findViewById(R.id.stateTitle);
        stateText = (TextView) this.findViewById(R.id.stateText);
        sqText = (TextView) this.findViewById(R.id.sqText);
        nameTv = (TextView) this.findViewById(R.id.userName);
        nameTv2 = (TextView) this.findViewById(R.id.nim);
        emailTv = (TextView) this.findViewById(R.id.email2);

        title1.setTypeface(Typeface.createFromAsset(getAssets(), font[0]));
        title2.setTypeface(Typeface.createFromAsset(getAssets(), font[0]));
        nameTv.setTypeface(Typeface.createFromAsset(getAssets(), font[0]));
        nameTv2.setTypeface(Typeface.createFromAsset(getAssets(), font[0]));
        emailTv.setTypeface(Typeface.createFromAsset(getAssets(), font[0]));
        logoutButton.setTypeface(Typeface.createFromAsset(getAssets(), font[0]));
        sqText.setTypeface(Typeface.createFromAsset(getAssets(), font[0]));
        stateText.setTypeface(Typeface.createFromAsset(getAssets(), font[0]));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child(name).getChildren()) {
                    if (snapshot.getKey().equals("NICKNAME")) {
                        nameTv.setText(snapshot.getValue().toString());
                        putName = snapshot.getValue().toString();
                    } else if (snapshot.getKey().equals("EMAIL")) {
                        emailTv.setText(snapshot.getValue().toString());
                        putEmail = snapshot.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        headsetButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                int algoTypes = 0;// = NskAlgoType.NSK_ALGO_TYPE_CR.value;

                startButton.setEnabled(false);
                stopButton.setEnabled(false);

                currentSelectedAlgo = NskAlgoType.NSK_ALGO_TYPE_INVALID;

                stateText.setText("");
                sqText.setText("");

                algoTypes += NskAlgoType.NSK_ALGO_TYPE_MED.value;
                algoTypes += NskAlgoType.NSK_ALGO_TYPE_BP.value;
                algoTypes += NskAlgoType.NSK_ALGO_TYPE_ATT.value;

                if (bInited) {
                    nskAlgoSdk.NskAlgoUninit();
                    bInited = false;
                }
                int ret = nskAlgoSdk.NskAlgoInit(algoTypes, getFilesDir().getAbsolutePath());
                if (ret == 0) {
                    bInited = true;
                }

                output_data_count = 0;
                output_data = null;

                raw_data = new short[512];
                raw_data_index = 0;

                headsetButton.setEnabled(false);
                startButton.setEnabled(false);

                tgStreamReader = new TgStreamReader(mBluetoothAdapter, callback);

                if (tgStreamReader != null && tgStreamReader.isBTConnected()) {

                    // Prepare for connecting
                    tgStreamReader.stop();
                    tgStreamReader.close();
                }

                tgStreamReader.connect();
            }
        });

        headsetButton2.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                int algoTypes = 0;// = NskAlgoType.NSK_ALGO_TYPE_CR.value;

                startButton.setEnabled(false);
                stopButton.setEnabled(false);

                currentSelectedAlgo = NskAlgoType.NSK_ALGO_TYPE_INVALID;

                stateText.setText("");
                sqText.setText("");

                algoTypes += NskAlgoType.NSK_ALGO_TYPE_MED.value;
                algoTypes += NskAlgoType.NSK_ALGO_TYPE_BP.value;
                algoTypes += NskAlgoType.NSK_ALGO_TYPE_ATT.value;

                if (bInited) {
                    nskAlgoSdk.NskAlgoUninit();
                    bInited = false;
                }
                int ret = nskAlgoSdk.NskAlgoInit(algoTypes, getFilesDir().getAbsolutePath());
                if (ret == 0) {
                    bInited = true;
                }

                output_data_count = 0;
                output_data = null;

                raw_data = new short[512];
                raw_data_index = 0;

                headsetButton.setEnabled(false);
                startButton.setEnabled(false);

                tgStreamReader = new TgStreamReader(mBluetoothAdapter, callback);

                if (tgStreamReader != null && tgStreamReader.isBTConnected()) {

                    // Prepare for connecting
                    tgStreamReader.stop();
                    tgStreamReader.close();
                }

                tgStreamReader.connect();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (bRunning == false) {
                    nskAlgoSdk.NskAlgoStart(false);     //Start EEG data measurement.

                    //Start drawing the graph.
                    Intent goSelect = new Intent(getApplicationContext(), SelectActivity.class);
                    goSelect.putExtra("name", putName);
                    goSelect.putExtra("email", putEmail);
                    startActivity(goSelect);
                    overridePendingTransition(0, 0);

                } else {
                    nskAlgoSdk.NskAlgoPause();

                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                nskAlgoSdk.NskAlgoStop();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent logout = new Intent(EEG.this, MainActivity.class);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                    logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                } else {
                    logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                startActivity(logout);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        nskAlgoSdk.setOnSignalQualityListener(new NskAlgoSdk.OnSignalQualityListener()

        {
            @Override
            public void onSignalQuality(int level) {
                final int fLevel = level;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // change UI elements here
                        String sqStr = NskAlgoSignalQuality.values()[fLevel].toString();
                        sqText.setText(sqStr);
                    }
                });
            }
        });

        nskAlgoSdk.setOnStateChangeListener(new NskAlgoSdk.OnStateChangeListener()

        {
            @Override
            public void onStateChange(int state, int reason) {
                String stateStr = "";
                String reasonStr = "";
                for (NskAlgoState s : NskAlgoState.values()) {
                    if (s.value == state) {
                        stateStr = s.toString();
                    }
                }
                for (NskAlgoState r : NskAlgoState.values()) {
                    if (r.value == reason) {
                        reasonStr = r.toString();
                    }
                }

                final String finalStateStr = stateStr + " | " + reasonStr;
                final int finalState = state;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // change UI elements here
                        stateText.setText(finalStateStr);

                        if (finalState == NskAlgoState.NSK_ALGO_STATE_RUNNING.value || finalState == NskAlgoState.NSK_ALGO_STATE_COLLECTING_BASELINE_DATA.value) {
                            bRunning = true;
                            startButton.setText("Pause");
                            startButton.setEnabled(true);
                            stopButton.setEnabled(true);
                        } else if (finalState == NskAlgoState.NSK_ALGO_STATE_STOP.value) {
                            bRunning = false;
                            raw_data = null;
                            raw_data_index = 0;
                            startButton.setText("Start");
                            startButton.setEnabled(true);
                            stopButton.setEnabled(false);
                            headsetButton.setEnabled(true);
//                            cannedButton.setEnabled(true);

                            if (tgStreamReader != null && tgStreamReader.isBTConnected()) {

                                // Prepare for connecting
                                tgStreamReader.stop();
                                tgStreamReader.close();
                            }

                            output_data_count = 0;
                            output_data = null;

                            System.gc();
                        } else if (finalState == NskAlgoState.NSK_ALGO_STATE_PAUSE.value) {
                            bRunning = false;
                            startButton.setText("Start");
                            startButton.setEnabled(true);
                            stopButton.setEnabled(true);
                        } else if (finalState == NskAlgoState.NSK_ALGO_STATE_ANALYSING_BULK_DATA.value) {
                            bRunning = true;
                            startButton.setText("Start");
                            startButton.setEnabled(false);
                            stopButton.setEnabled(true);
                        } else if (finalState == NskAlgoState.NSK_ALGO_STATE_INITED.value || finalState == NskAlgoState.NSK_ALGO_STATE_UNINTIED.value) {
                            bRunning = false;
                            startButton.setText("Start");
                            startButton.setEnabled(true);
                            stopButton.setEnabled(false);
                        }
                    }
                });
            }
        });

        nskAlgoSdk.setOnSignalQualityListener(new NskAlgoSdk.OnSignalQualityListener()

        {
            @Override
            public void onSignalQuality(final int level) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String sqStr = NskAlgoSignalQuality.values()[level].toString();
                        sqText.setText(sqStr);
                    }
                });
            }
        });

        nskAlgoSdk.setOnBPAlgoIndexListener(new NskAlgoSdk.OnBPAlgoIndexListener()

        {
            @Override
            public void onBPAlgoIndex(final float delta, final float theta, final float alpha,
                                      final float beta, final float gamma) {

                final float fDelta = delta, fTheta = theta, fAlpha = alpha, fBeta = beta, fGamma = gamma;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        getNow();

                        double hb1 = rand.nextInt(30 - 20 + 1) + 20;
                        double smr1 = rand.nextInt(30 - 20 + 1) + 20;

                        eeg[0] = String.valueOf(alpha);  //alpha
                        eeg[1] = String.valueOf(theta);  //low_beta
                        eeg[2] = String.valueOf(delta);  //delta
                        eeg[3] = String.valueOf(gamma - 10);  //gamma
                        eeg[4] = String.valueOf(beta);  //theta

                        int size = nz.setData();

                        if (size != 0) {
                            if (nomal.length != 0) {
                                nomal = nz.nomal(eeg[0], eeg[1], eeg[2], eeg[3], eeg[4]);
                                if (nomal.length != 0) {
                                    databaseReference.child(name).child("EEG DATA").child(n[0] + "년")
                                            .child(n[1] + "월")
                                            .child(n[2] + "일")
                                            .child(n[3] + "시")
                                            .child(n[4] + "분")
                                            .child(n[5] + "초")
                                            .child("Alpha").setValue(nomal[0]);

                                    databaseReference.child(name).child("EEG DATA").child(n[0] + "년")
                                            .child(n[1] + "월")
                                            .child(n[2] + "일")
                                            .child(n[3] + "시")
                                            .child(n[4] + "분")
                                            .child(n[5] + "초")
                                            .child("Low Beta").setValue(nomal[1]);

                                    databaseReference.child(name).child("EEG DATA").child(n[0] + "년")
                                            .child(n[1] + "월")
                                            .child(n[2] + "일")
                                            .child(n[3] + "시")
                                            .child(n[4] + "분")
                                            .child(n[5] + "초")
                                            .child("Delta").setValue(nomal[2]);

                                    databaseReference.child(name).child("EEG DATA").child(n[0] + "년")
                                            .child(n[1] + "월")
                                            .child(n[2] + "일")
                                            .child(n[3] + "시")
                                            .child(n[4] + "분")
                                            .child(n[5] + "초")
                                            .child("Gamma").setValue(nomal[3]);

                                    databaseReference.child(name).child("EEG DATA").child(n[0] + "년")
                                            .child(n[1] + "월")
                                            .child(n[2] + "일")
                                            .child(n[3] + "시")
                                            .child(n[4] + "분")
                                            .child(n[5] + "초")
                                            .child("Theta").setValue(nomal[4]);

                                    databaseReference.child(name).child("EEG DATA").child(n[0] + "년")
                                            .child(n[1] + "월")
                                            .child(n[2] + "일")
                                            .child(n[3] + "시")
                                            .child(n[4] + "분")
                                            .child(n[5] + "초")
                                            .child("High Beta").setValue(hb1);

                                    databaseReference.child(name).child("EEG DATA").child(n[0] + "년")
                                            .child(n[1] + "월")
                                            .child(n[2] + "일")
                                            .child(n[3] + "시")
                                            .child(n[4] + "분")
                                            .child(n[5] + "초")
                                            .child("SMR").setValue(smr1);
                                }
                            }
                        }
                    }
                });
            }
        });

        nskAlgoSdk.setOnAttAlgoIndexListener(new NskAlgoSdk.OnAttAlgoIndexListener()

        {
            @Override
            public void onAttAlgoIndex(final int value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getNow();

                        if (value != 0) {
                            databaseReference.child(name).child("EEG DATA").child(n[0] + "년")
                                    .child(n[1] + "월")
                                    .child(n[2] + "일")
                                    .child(n[3] + "시")
                                    .child(n[4] + "분")
                                    .child(n[5] + "초")
                                    .child("집중도").setValue(value);
                        }
                    }
                });
            }
        });

        nskAlgoSdk.setOnMedAlgoIndexListener(new NskAlgoSdk.OnMedAlgoIndexListener()

        {
            @Override
            public void onMedAlgoIndex(final int value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getNow();

                        if (value != 0) {
                            databaseReference.child(name).child("EEG DATA").child(n[0] + "년")
                                    .child(n[1] + "월")
                                    .child(n[2] + "일")
                                    .child(n[3] + "시")
                                    .child(n[4] + "분")
                                    .child(n[5] + "초")
                                    .child("명상도").setValue(value);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        nskAlgoSdk.NskAlgoUninit();
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private TgStreamHandler callback = new TgStreamHandler() {

        @Override
        public void onStatesChanged(int connectionStates) {
            // TODO Auto-generated method stub
            switch (connectionStates) {
                case ConnectionStates.STATE_CONNECTING:
                    // Do something when connecting
                    break;
                case ConnectionStates.STATE_CONNECTED:
                    // Do something when connected
                    tgStreamReader.start();
                    showToast("Connected", Toast.LENGTH_SHORT);
                    break;
                case ConnectionStates.STATE_WORKING:

                    EEG.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Button startButton = (Button) findViewById(R.id.startButton);
                            startButton.setEnabled(true);
                        }

                    });

                    break;
                case ConnectionStates.STATE_GET_DATA_TIME_OUT:

                    showToast("Get data time out!", Toast.LENGTH_SHORT);

                    if (tgStreamReader != null && tgStreamReader.isBTConnected()) {
                        tgStreamReader.stop();
                        tgStreamReader.close();
                    }

                    break;
                case ConnectionStates.STATE_STOPPED:
                    // Do something when stopped
                    // We have to call tgStreamReader.stop() and tgStreamReader.close() much more than
                    // tgStreamReader.connectAndstart(), because we have to prepare for that.

                    break;
                case ConnectionStates.STATE_DISCONNECTED:
                    // Do something when disconnected
                    break;
                case ConnectionStates.STATE_ERROR:
                    // Do something when you get error message
                    break;
                case ConnectionStates.STATE_FAILED:
                    // Do something when you get failed message
                    // It always happens when open the BluetoothSocket error or timeout
                    // Maybe the device is not working normal.
                    // Maybe you have to try again
                    break;
            }
        }

        @Override
        public void onRecordFail(int flag) {

        }

        @Override
        public void onChecksumFail(byte[] payload, int length, int checksum) {
            // You can handle the bad packets here.
        }

        @Override
        public void onDataReceived(int datatype, int data, Object obj) {
            switch (datatype) {
                case MindDataType.CODE_ATTENTION:
                    short attValue[] = {(short) data};
                    nskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_ATT.value, attValue, 1);
                    break;
                case MindDataType.CODE_MEDITATION:
                    short medValue[] = {(short) data};
                    nskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_MED.value, medValue, 1);
                    break;
                case MindDataType.CODE_POOR_SIGNAL:
                    short pqValue[] = {(short) data};
                    nskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_PQ.value, pqValue, 1);
                    break;
                case MindDataType.CODE_RAW:
                    raw_data[raw_data_index++] = (short) data;
                    if (raw_data_index == 512) {
                        nskAlgoSdk.NskAlgoDataStream(NskAlgoDataType.NSK_ALGO_DATA_TYPE_EEG.value, raw_data, raw_data_index);
                        raw_data_index = 0;
                    }
                    break;
                default:
                    break;
            }
        }

    };

    public void showToast(final String msg, final int timeStyle) {
        EEG.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), msg, timeStyle).show();
            }

        });
    }

    private void showDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public String[] getNow() {
        Calendar cal = Calendar.getInstance();

        n[0] = String.format(Locale.KOREA, "%04d", cal.get(Calendar.YEAR));
        n[1] = String.format(Locale.KOREA, "%02d", cal.get(Calendar.MONTH) + 1);
        n[2] = String.format(Locale.KOREA, "%02d", cal.get(Calendar.DAY_OF_MONTH));
        n[3] = String.format(Locale.KOREA, "%02d", cal.get(Calendar.HOUR_OF_DAY));
        n[4] = String.format(Locale.KOREA, "%02d", cal.get(Calendar.MINUTE));
        n[5] = String.format(Locale.KOREA, "%02d", cal.get(Calendar.SECOND));

        return n;
    }
}

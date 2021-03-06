package com.neurosky.algo_sdk_sample;

/**
 * 사진첩 권한 수락여부와 파이어베이스를 이용한 로그인 구현.
 **/

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private final static String[] requestWritePermission =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    EditText idText, pwText;
    Button signup, login, findPw;
    TextView tv;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.loginTv);
        tv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/MILKYWAY.TTF"));

        idText = (EditText) findViewById(R.id.emailInput);
        pwText = (EditText) findViewById(R.id.passwordInput);

        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);
        findPw = (Button) findViewById(R.id.findPassword);
        login.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/MILKYWAY.TTF"));
        signup.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/MILKYWAY.TTF"));
        findPw.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/MILKYWAY.TTF"));

        getPermission();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin(idText.getText().toString().trim(), pwText.getText().toString().trim());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idText.setText("");
                pwText.setText("");
                Intent toSignup = new Intent(getApplicationContext(), SignUp.class);
                startActivity(toSignup);
                overridePendingTransition(0,0);
            }
        });

        findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idText.setText("");
                pwText.setText("");
                Intent toFind = new Intent(MainActivity.this, FindPw.class);
                startActivity(toFind);
                overridePendingTransition(0,0);
            }
        });
    }

    private void getPermission() {
        final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWritePermission) {

        } else {
            RuntimePermissionUtil.requestPermission(MainActivity.this, requestWritePermission, 100);
        }

    }

    private void userLogin(final String email, String password) {
        Intent intent = new Intent(MainActivity.this, CustomDialog.class);

        if (idText.getText().toString().equals("")) {
            intent.putExtra("activity", "enterEmail");
            startActivity(intent);
        } else if (pwText.getText().toString().equals("")) {
            intent.putExtra("activity", "enterPassword");
            startActivity(intent);
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                                idText.setText("");
                                pwText.setText("");
                            } else {
                                currentUser = mAuth.getCurrentUser();

                                Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                Intent goEEG = new Intent(MainActivity.this, EEG.class);
                                overridePendingTransition(0,0);
                                finish();
                                startActivity(goEEG);
                            }
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(MainActivity.this, EEG.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        switch (requestCode) {
            case 100: {

                RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MainActivity.this, "Permission Success!!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(MainActivity.this, "Permission Denied! You cannot save image!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }
}
package com.neurosky.algo_sdk_sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import processing.android.CompatUtils;
import processing.android.PFragment;
import processing.core.PApplet;

public class GraphActivity extends AppCompatActivity {

    private PApplet sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        //setContentView(R.layout.activity_main);
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        int width = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        int height = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        sketch = new com.neurosky.algo_sdk_sample.Sketch(width, height);
        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                int count=0;
                while(count<10){
                    count++;
                    Log.i("Thread",""+count);
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                Intent meditation = new Intent(getApplicationContext(), Meditation.class);
                startActivity(meditation);

            }
        });
        th.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResult) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(requestCode, permissions, grantResult);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }

}


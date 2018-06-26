package com.neurosky.algo_sdk_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class Meditation extends AppCompatActivity{

    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        b1 =(Button)findViewById(R.id.button1);

    }

}

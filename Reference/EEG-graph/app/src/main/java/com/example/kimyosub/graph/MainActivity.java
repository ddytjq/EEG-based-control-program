package com.example.kimyosub.graph;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import java.util.Objects;
import processing.android.CompatUtils;
import processing.android.PFragment;
import processing.core.PApplet;


public class MainActivity extends AppCompatActivity {

    private PApplet sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        //setContentView(R.layout.activity_main);
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        int width;
        width = ((WindowManager) Objects.requireNonNull(getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getWidth();
        int height;
        height = ((WindowManager) Objects.requireNonNull(getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getHeight();

        sketch = new Sketch(width, height);
        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);

//        Toast.makeText(this,String.valueOf(height),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResult) {
        if (sketch != null)
            sketch.onRequestPermissionsResult(requestCode, permissions, grantResult);
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }

}



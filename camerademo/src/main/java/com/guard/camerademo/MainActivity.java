package com.guard.camerademo;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                CameraManager.getInstance(getApplicationContext()).openCamera(CameraManager.CAMERA_FACING_FRONT);
            }
        };
        thread.start();;
    }

    @Override
    protected void onStop() {
        super.onStop();
        CameraManager.getInstance(getApplicationContext()).releaseCamera();
    }
}

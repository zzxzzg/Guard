package com.guard.mediademo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.TextureView;
import android.view.View;
import android.widget.BaseAdapter;

/**
 * Created by yxwang on 16-2-19.
 */
public class HardDecoderActivity extends BaseActivity {
    private TextureView mPreView;
    private TextureView mDecoderView;

    private DecoderController mDecoderController;
    private EncoderController mEncoderController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        mPreView=(TextureView) findViewById(R.id.surfaceview1);
        mDecoderView=(TextureView) findViewById(R.id.surfaceview2);



        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestCameraPerssion();
        }else{
            execute();
        }
    }

    private void execute(){
        mDecoderController = new DecoderController(this,
                DecoderController.DECODER_TYPE_H264);

        mDecoderController.addDecoder(mDecoderView);
        mEncoderController = new EncoderController(this, mPreView);


        mEncoderController.setH264DateListener(new EncoderController.H264DateListener() {
            @Override
            public void h264DateCallback(byte[] data, int width, int height, long timestemp) {
                mDecoderController.h264DateCallback(data, data.length, width, height);
            }
        });


        mPreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEncoderController.changeCamear();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }

    @TargetApi(23)
    private void requestCameraPerssion(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,

                Manifest.permission.CAMERA)                     //询问是否已经有权限
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,         //是否应该向用户解释下为什么我们需要权限
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        0);                        //申请权限

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setOrientationEnable();
    }

    @Override
    protected void onStop() {
        super.onStop();
        setOrientationDisable();
    }

    private int DEFAULT_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    @Override
    public void onOrientationChange(int requestedOrientation) {
        super.onOrientationChange(requestedOrientation);
        if(requestedOrientation == DEFAULT_ORIENTATION){
            return;
        }
        DEFAULT_ORIENTATION=requestedOrientation;
        super.onOrientationChange(requestedOrientation);
        mDecoderController.setScreenOrientation(requestedOrientation);
        if(mEncoderController!=null){
            mEncoderController.setScreenOrientation(requestedOrientation);
        }
    }
}

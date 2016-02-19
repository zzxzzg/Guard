package com.guard.harddecoderdemo;

import java.io.IOException;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.media.MediaCodec.BufferInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.TextureView.SurfaceTextureListener;

@SuppressLint("NewApi")
public class Activity3 extends BaseActivity {
	
	
	private TextureView mPreView;
	private TextureView mDecoderView;
	
	
	private Surface mRemoteSurface;
	private DecoderManager mDecoderManager;
	
	private EncoderController mEncoderController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_3);
		mPreView=(TextureView) findViewById(R.id.surfaceview1);
		mDecoderView=(TextureView) findViewById(R.id.surfaceview2);
		requestCameraPerssion();
		
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
		calculateOrientation(mDecoderView,requestedOrientation);

		if(mEncoderController!=null){
			mEncoderController.setScreenOrientation(requestedOrientation);
		}
	}


	private int getDeviceDefaultOrientation() {

		WindowManager windowManager = (WindowManager)getSystemService(Activity.WINDOW_SERVICE);

		Configuration config = getResources().getConfiguration();

		int rotation = windowManager.getDefaultDisplay().getRotation();

		if (((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && config.orientation == Configuration.ORIENTATION_LANDSCAPE)
				|| ((rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && config.orientation == Configuration.ORIENTATION_PORTRAIT)) {
			return Configuration.ORIENTATION_LANDSCAPE;
		} else {
			return Configuration.ORIENTATION_PORTRAIT;
		}
	}

	private void calculateOrientation(TextureView mTextureMain,int requestedOrientation){
		if (getDeviceDefaultOrientation() == Configuration.ORIENTATION_PORTRAIT) {
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
				mTextureMain.setRotation(0);
			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				mTextureMain.setRotation(90);
			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
				mTextureMain.setRotation(180);
			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
				mTextureMain.setRotation(270);
			}
		} else {
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
				mTextureMain.setRotation(90);
			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				mTextureMain.setRotation(180);

			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
				mTextureMain.setRotation(270);

			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
				mTextureMain.setRotation(0);

			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults.length > 0
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED){

		}
	}

	private void execute(){
		mEncoderController=new EncoderController(this, mPreView);
		mEncoderController.setH264DateListener(new EncoderController.H264DateListener() {

			@Override
			public void h264DateCallback(byte[] data, int width, int height) {
				mDecoderManager.putData(data);
			}
		});
		mDecoderView.setSurfaceTextureListener(mDecoderListener);

		mPreView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mEncoderController.changeCamear();
			}
		});
	}

	private void requestCameraPerssion(){
		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.CAMERA)                     //询问是否已经有权限
				!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE)                     //询问是否已经有权限
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
						new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
						0);                        //申请权限

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		}else{
			execute();
		}
	}
	
	private SurfaceTextureListener mDecoderListener=new SurfaceTextureListener() {
		
		@Override
		public void onSurfaceTextureUpdated(SurfaceTexture surface) {
			// TODO Auto-generated method stub
			
		};
		
		@Override
		public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
			mDecoderManager.stopDecode();
			return true;
		}
		
		@Override
		public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
				int height) {
			mRemoteSurface = new Surface(surface);
			mDecoderManager = new DecoderManager();
			mDecoderManager.init(mRemoteSurface);
			mDecoderManager.startDecoder();
		}
	};
	
	
	

}
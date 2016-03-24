package com.guard.openh264demo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.media.MediaCodec;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

public class EncoderController {
	private TextureView mEncoderTextureView;
	private Context mContext;

	private Camera mCamera;
	private int mCameraOrientation;
	private int mScreenOrientation=-1;

	private int mWidth;
	private int mHeight;
	private int mFramerate = 15;
	private byte[] mPreviewPuffer;
	int mBitrate=512*1024;
	//private long mInstance=-1;

	private int mCameraDefaultOrientation=270;

	int mCurrentCameraFace=CameraInfo.CAMERA_FACING_FRONT;

	public int getScreenOrientation() {
		return mScreenOrientation;
	}

	public void setScreenOrientation(int mScreenOrientation) {
		this.mScreenOrientation = mScreenOrientation;
		calculateOrientation(mScreenOrientation);
		resetCamera();
	}

	private H264DateListener mH264DateListener;

	public interface H264DateListener{
		void h264DateCallback(byte[] data, int width, int height);
	}

	public H264DateListener getH264DateListener() {
		return mH264DateListener;
	}

	public void setH264DateListener(H264DateListener mH264DateListener) {
		this.mH264DateListener = mH264DateListener;
	}


	public EncoderController(Context context, TextureView textureView){
		if(context==null || textureView==null){
			throw new RuntimeException("context and textureview can't be null");
		}
		mContext=context;
		mEncoderTextureView=textureView;
		mEncoderTextureView.setSurfaceTextureListener(mPreViewListener);
		OpenH264Model.INSTAICE.nativeInitOpenH264Encoder();
	}


//	public void changeCamear() {
//		if (mCamera == null) {
//			return;
//		}
//		mCamera.stopPreview();
//		mCamera.setPreviewCallback(null);
//		mCamera.release();
//		mCamera = null;
//		if (mCurrentCameraFace == CameraInfo.CAMERA_FACING_BACK) {
//			mCurrentCameraFace = CameraInfo.CAMERA_FACING_FRONT;
//		} else {
//			mCurrentCameraFace = CameraInfo.CAMERA_FACING_BACK;
//		}
//
//		openCamera();
//		setupCamear(mEncoderTextureView.getSurfaceTexture());
//
//	}

	public void resetCamera() {
		if (mCamera != null) {
			try{
				mCamera.stopPreview();
				mCamera.setPreviewCallback(null);
			}catch(RuntimeException e){
				e.printStackTrace();
				mCamera = null;
			}
		}
		setupCamear(mEncoderTextureView.getSurfaceTexture());
	}


	private SurfaceTextureListener mPreViewListener = new SurfaceTextureListener() {

		@Override
		public void onSurfaceTextureUpdated(SurfaceTexture surface) {

		}

		@Override
		public void onSurfaceTextureSizeChanged(SurfaceTexture surface,
												int width, int height) {
			//setupCamear(surface);
		}

		@Override
		public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
			if (mCamera != null) {
				try{
					mCamera.stopPreview();
					mCamera.setPreviewCallback(null);
					mCamera.release();
					mCamera = null;
				}catch(RuntimeException e){
					e.printStackTrace();
					mCamera = null;
				}
			}

			OpenH264Model.INSTAICE.nativeStopOpenH264Encoder();
//			if(mInstance!=-1){
//			    NewVideoController.getInstance().nativeStopEncoder(mInstance);
//			    mInstance=-1;
//			}
			return true;
		}

		@Override
		public void onSurfaceTextureAvailable(SurfaceTexture surface,
											  int width, int height) {
//			if(mInstance==-1){
//			    mInstance=NewVideoController.getInstance().nativeStartEncoder(mCurrentLine.mLineId);
//			    Log.d("sss","mInstance="+mInstance);
//			}
			openCamera();
			setupCamear(surface);
			OpenH264Model.INSTAICE.nativeStartOpenH264Encoder();
		}
	};


	private void openCamera() {


		int cameraCount = 0;
		CameraInfo cameraInfo = new CameraInfo();
		cameraCount = Camera.getNumberOfCameras(); // get cameras number

		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
			if (cameraInfo.facing == mCurrentCameraFace) {
				try {
					mCamera = Camera.open(camIdx);
					Log.d("sss",cameraInfo.orientation+"    "+mCameraOrientation);
					mCameraOrientation = cameraInfo.orientation;
					mCameraDefaultOrientation=cameraInfo.orientation;
					if(mScreenOrientation!=-1){
						calculateOrientation(mScreenOrientation);
					}else{
						if(getDeviceDefaultOrientation() == Configuration.ORIENTATION_LANDSCAPE){
							if(mCurrentCameraFace == CameraInfo.CAMERA_FACING_FRONT){
								mCameraOrientation+=180;
								mCameraOrientation=mCameraOrientation%360;
							}
						}
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
					mCamera=null;
				}
			}
		}
	}

	private void setupCamear(SurfaceTexture surface) {
		if (mCamera == null) {
			return;
		}
		int previewFormat;
		Camera.Parameters para=null;
		try{
			para = mCamera.getParameters();
		}catch(RuntimeException e){
			e.printStackTrace();
			mCamera=null;

			return;
		}
		List<Integer> formats = para.getSupportedPreviewFormats();
		for(int i=0;i<formats.size();i++){
			Log.d("sss","support format"+formats.get(i));
		}
		if (formats.contains(ImageFormat.NV21)) {
			para.setPreviewFormat(ImageFormat.NV21);
			previewFormat = ImageFormat.NV21;
			if ("ZTE N909".equals(android.os.Build.MODEL)) {
				previewFormat = -1;
			}
		} else if (formats.contains(ImageFormat.YV12)) {
			para.setPreviewFormat(ImageFormat.YV12);
			previewFormat = ImageFormat.YV12;
		} else {
			para.setPreviewFormat(formats.get(0));
			previewFormat = formats.get(0);
		}

		mCamera.setParameters(para);
		if(mCurrentCameraFace == CameraInfo.CAMERA_FACING_FRONT){
			if(mCameraDefaultOrientation ==270){
				mCamera.setDisplayOrientation(90);
			}else if (mCameraDefaultOrientation ==90){
				mCamera.setDisplayOrientation(270);
			}
		}else{
			mCamera.setDisplayOrientation(mCameraDefaultOrientation);
		}
		mCamera.setPreviewCallbackWithBuffer(new PreviewCallback() {

			@Override
			public void onPreviewFrame(byte[] data, Camera camera) {
				// mController.setLocalData();
				// mEncoderManager.putData(data);
				OpenH264Model.INSTAICE.nativePutYUVDate(data,mWidth,mHeight);
				mCamera.addCallbackBuffer(mPreviewPuffer);
			}
		});


		List<Camera.Size> sizes = para
				.getSupportedPreviewSizes();
		for (int i = 0; i < sizes.size(); i++) {
			Log.d("sss","support " + sizes.get(i).width+"   *   "+sizes.get(i).height);
		}

		ImageSize size = getMaxSize(mContext,
				640, 480, sizes);
		if (size == null) {
			Camera.Size s = sizes.get(sizes.size() - 1);
			para.setPreviewSize(s.width, s.height);
			// para.setPreviewSize(640, 480);
		} else {
			Log.d("sss","the size is "+ size.width+" * "+size.height);
			para.setPreviewSize(size.width, size.height);
		}

		mWidth = para.getPreviewSize().width;
		mHeight = para.getPreviewSize().height;

		OpenH264Model.INSTAICE.nativeSetOpenH264Param(mWidth, mHeight, mFramerate, mBitrate);

		List<int[]> fpsRange = para.getSupportedPreviewFpsRange();
		int[] fps = null;
		for (int i = 0; i < fpsRange.size(); i++) {
			if (fpsRange.get(i)[1] == mFramerate) {
				if (fps == null) {
					fps = fpsRange.get(i);
				} else {
					if (fps[0] < fpsRange.get(i)[0]) {
						fps = fpsRange.get(i);
					}
				}
			}

		}
		if (fps != null) {
			para.setPreviewFpsRange(fps[0], fps[1]);
		}

		// para.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);//1连续对焦
		mCamera.setParameters(para);

		mPreviewPuffer = new byte[mWidth * mHeight * 3 / 2];

		mCamera.addCallbackBuffer(mPreviewPuffer);
		try {
			mCamera.setPreviewTexture(surface);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCamera.startPreview();
		// mCamera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
	}

	public static class ImageSize {
		public int width;
		public int height;
	}


	public static ImageSize getMaxSize(Context context, int width,int height ,
									   List<Camera.Size> sizes) {
		if (context == null || sizes == null || sizes.size() == 0) {
			return null;
		}

		ImageSize size=new ImageSize();
		ImageSize imageSize = new ImageSize();
		imageSize.width=width;
		imageSize.height=height;
		for (int i = 0; i < sizes.size(); i++) {
			Camera.Size supportSize = sizes.get(i);
			if (supportSize.width == imageSize.width&& supportSize.height == imageSize.height) {
				size.height = imageSize.height;
				size.width = imageSize.width;
				return size;
			}
		}
		for (int i = 0; i < sizes.size(); i++) {
			Camera.Size supportSize = sizes.get(i);
			if(supportSize.width*supportSize.height<=width*height){
				size.height = supportSize.height;
				size.width = supportSize.width;
				return size;
			}
		}
		return null;
	}


	public void calculateOrientation(int requestedOrientation){
		if (getDeviceDefaultOrientation() == Configuration.ORIENTATION_PORTRAIT) {
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
				if(mCurrentCameraFace==CameraInfo.CAMERA_FACING_BACK){
					mCameraOrientation = 90;
				}else{
					if(mCameraDefaultOrientation ==270){
						mCameraOrientation=270;
					}else if (mCameraDefaultOrientation ==90){
						mCameraOrientation=90;
					}
				}

			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				if(mCurrentCameraFace==CameraInfo.CAMERA_FACING_BACK) {
					mCameraOrientation = 0;
				}else{
					if(mCameraDefaultOrientation ==270){
						mCameraOrientation=0;
					}else if (mCameraDefaultOrientation ==90){
						mCameraOrientation=180;
					}
				}
			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
				if(mCurrentCameraFace==CameraInfo.CAMERA_FACING_BACK){
					mCameraOrientation = 270;
				}else{
					if(mCameraDefaultOrientation ==270){
						mCameraOrientation=90;
					}else if (mCameraDefaultOrientation ==90){
						mCameraOrientation=270;
					}
				}
			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
				if(mCurrentCameraFace==CameraInfo.CAMERA_FACING_BACK) {
					mCameraOrientation = 180;
				}else{
					if(mCameraDefaultOrientation ==270){
						mCameraOrientation=180;
					}else if (mCameraDefaultOrientation ==90){
						mCameraOrientation=0;
					}
				}
			}
		} else {
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
				mCameraOrientation = 0;
			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				if(mCurrentCameraFace==CameraInfo.CAMERA_FACING_BACK){
					mCameraOrientation = 270;
				}else{
					mCameraOrientation = 90;
				}
			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
				mCameraOrientation = 180;
			}
			if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
				if(mCurrentCameraFace==CameraInfo.CAMERA_FACING_BACK){
					mCameraOrientation = 90;
				}else{
					mCameraOrientation = 270;
				}
			}
		}
	}

	private int getDeviceDefaultOrientation() {

		WindowManager windowManager = (WindowManager) mContext
				.getSystemService(Activity.WINDOW_SERVICE);

		Configuration config = mContext.getResources().getConfiguration();

		int rotation = windowManager.getDefaultDisplay().getRotation();

		if (((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && config.orientation == Configuration.ORIENTATION_LANDSCAPE)
				|| ((rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && config.orientation == Configuration.ORIENTATION_PORTRAIT)) {
			return Configuration.ORIENTATION_LANDSCAPE;
		} else {
			return Configuration.ORIENTATION_PORTRAIT;
		}
	}

}


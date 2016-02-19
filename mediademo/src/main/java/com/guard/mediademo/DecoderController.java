package com.guard.mediademo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.TextureView.SurfaceTextureListener;


public class DecoderController {
	public static final int DECODER_TYPE_H264=0;
	public static final int DECODER_TYPE_YUV=1;
	public static final int DECODER_TYPE_RGB=2;
	
	private Context mContext;
	private int mScreenOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
	
	private int mDecoderType;
	private List<DecoderHolder> mDecoderHolders;
	
	public int getScreenOrientation() {
		return mScreenOrientation;
	}

	public void setScreenOrientation(int mScreenOrientation) {
		this.mScreenOrientation = mScreenOrientation;
		if (mDecoderType == DECODER_TYPE_H264) {
			for (DecoderHolder holder : mDecoderHolders) {
				//holder.mTextureView.setCustomeLayout(false, 0, 0, 0, 0);
				calculateOrientation(holder.mTextureView, mScreenOrientation);
			}
		}
	}
	
	public DecoderController(Context context){
		this(context,DECODER_TYPE_H264);
	}
	
	public DecoderController(Context context,int decoderType){
		if(context==null){
			throw new RuntimeException("context and textureview can't be null");
		}
		mContext=context;
		mDecoderType=decoderType;
		mDecoderHolders=new ArrayList<DecoderHolder>();

	}

	public void h264DateCallback(byte[] bytes, int len, int width, int height) {
		for(final DecoderHolder holder : mDecoderHolders){
			if(mScreenOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || mScreenOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE){
				int temp=width;
				width=height;
				height=temp;
			}
			if(width!=holder.oldWidth && height!=holder.oldHeight){
				//holder.setSurfaceSize(width,height);

				//add this for bug  57733
				if(Build.VERSION.SDK_INT<= Build.VERSION_CODES.JELLY_BEAN_MR2 && holder.oldWidth!=0){
					holder.mDecoderManager.restart();
				}

				holder.oldWidth=width;
				holder.oldHeight=height;

			}
			if(holder.mDecoderManager!=null){
				holder.mDecoderManager.putData(bytes);
			}
		}
	}

	
	public void addDecoder(TextureView texture){
		if(texture==null){
			throw new RuntimeException("context and textureview can't be null");
		}
		DecoderHolder holder=new DecoderHolder();
		holder.mTextureView=texture;
		if(mDecoderType==DECODER_TYPE_H264){
		     new H264SurfaceListener(holder);
		}
		mDecoderHolders.add(holder);
	}
	
	public void startDecoder(DecoderHolder holder){
		boolean hasStart=false;
		holder.isStart=true;
		for(DecoderHolder temp:mDecoderHolders){
			if(temp==holder){
				continue;
			}
			if(temp.isStart){
				hasStart=true;
			}
		}
	}
	
	public void stopDecoder(DecoderHolder holder){
		boolean needEnd=true;
		holder.isStart=false;
		for(DecoderHolder temp:mDecoderHolders){
			if(temp==holder){
				continue;
			}
			if(temp.isStart){
				needEnd=false;
			}
		}
	}
	
	class DecoderHolder{
		public TextureView mTextureView;
		public boolean isStart=false;
		public DecoderManager mDecoderManager;
		public int oldWidth=0;
		public int oldHeight=0;
		public long mInstance;
		
//		public void setSurfaceSize(int width,int height){
//			mTextureView.changeSurfaceSize(mScreenOrientation, width, height);
//		}
//		public void setSurfaceSize(int orientation,int width,int height){
//			mTextureView.changeSurfaceSize(orientation, width, height);
//		}
		
	}

	
	class H264SurfaceListener implements SurfaceTextureListener{
		private DecoderHolder mHolder;
		
		public H264SurfaceListener(DecoderHolder holder){
			mHolder=holder;
			mHolder.mTextureView.setSurfaceTextureListener(this);
		}
		

		@Override
		public void onSurfaceTextureAvailable(SurfaceTexture surface,
				int width, int height) {
			mHandler.postDelayed(mStartRemote, 300);
		}

		@Override
		public void onSurfaceTextureSizeChanged(SurfaceTexture surface,
				int width, int height) {
			
		}

		@Override
		public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
			Log.d("sss","onSurfaceTextureDestroyed");
			mHandler.removeCallbacks(mStartRemote);
			if(isRemoteStart){
				stopDecoder(mHolder);
				mHolder.mDecoderManager.stopDecode();
			}
			isRemoteStart=false;
			
			return true;
		}

		@Override
		public void onSurfaceTextureUpdated(SurfaceTexture surface) {
			
		}
		private Handler mHandler=new Handler();
		private boolean isRemoteStart=false;
		private Surface mSurface;
		private Runnable mStartRemote=new Runnable() {
			
			@Override
			public void run() {
				isRemoteStart=true;
				startDecoder(mHolder);
				mSurface=new Surface(mHolder.mTextureView.getSurfaceTexture());
				if(mHolder.mDecoderManager==null){
					mHolder.mDecoderManager = new DecoderManager();
				}
				Log.d("sss","startDecoder");
				mHolder.mDecoderManager.init(mSurface);
				mHolder.mDecoderManager.startDecoder();
			}
		};
		
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

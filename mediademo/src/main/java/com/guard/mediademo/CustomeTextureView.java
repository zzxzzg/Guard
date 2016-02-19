package com.guard.mediademo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class CustomeTextureView extends TextureView{
	
	private boolean isCustomeLayout;
	private int mCustomeLeft;
	private int mCustomeRight;
	private int mCustomeTop;
	private int mCustomeBottom;

	public CustomeTextureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomeTextureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomeTextureView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void changeSurfaceSize(final int screenOrientation,final int width,final int height){
		post(new Runnable() {
			
			@Override
			public void run() {
				setSurfaceSize(screenOrientation,width,height);
			}
		});
	}
	
	public void setSurfaceSize( int screenOrientation ,int width, int height) {
			ViewGroup parent = (ViewGroup)getParent();
			float parentHeight = parent.getHeight() + 100;
			float parentWidth = parent.getWidth();
			float tempWidth;
			float tempHeight;
			if (width > height) {
				tempWidth = (int) parentWidth;
				tempHeight = (int) (parentWidth / width * height);
			} else {
				tempWidth = (int) parentWidth;
				tempHeight = (int) parentHeight;
			}
			if (screenOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
					|| screenOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
				if (width < height) {
					float left = tempWidth / 2 - tempHeight / 2;
					float top = tempHeight / 2 - tempWidth / 2;
					float right = tempWidth / 2 + tempHeight / 2;
					float bottom = tempHeight / 2 + tempWidth / 2;
					setCustomeLayout(true, (int) left, (int) top,
							(int) right, (int) bottom);
				} else {
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)getLayoutParams();
					params.width = (int) tempHeight;
					params.height = (int) tempWidth;
					params.addRule(RelativeLayout.CENTER_IN_PARENT);
					setLayoutParams(params);
				}
			} else {
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)getLayoutParams();
				params.width = (int) tempWidth;
				params.height = (int) tempHeight;
				params.addRule(RelativeLayout.CENTER_IN_PARENT);
				setLayoutParams(params);
				
			}
	}
	
	
	public void setCustomeLayout(boolean customeLayout,int left,int top,int right,int bottom){
		isCustomeLayout=customeLayout;
		if(isCustomeLayout){
			mCustomeLeft=left;
			mCustomeTop=top;
			mCustomeRight=right;
			mCustomeBottom=bottom;
			requestLayout();
		}
	}
	
	@Override
	public void layout(int l, int t, int r, int b) {
		if(isCustomeLayout){
			super.layout(mCustomeLeft, mCustomeTop, mCustomeRight, mCustomeBottom);
		}else{
			super.layout(l, t, r, b);
		}

	}

}

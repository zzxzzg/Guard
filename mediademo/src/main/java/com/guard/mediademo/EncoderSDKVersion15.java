package com.guard.mediademo;

public class EncoderSDKVersion15 extends AbstractEncoder{

	@Override
	public boolean canEncoder() {
		return false;
	}

	@Override
	public void initEncoder(int width, int height, int framerate, int bitrate,int previewFormat,int orientation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeEncoder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void offerEncoder(byte[] input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCallBack(EncodeManager.DateCallBack callback) {
		// TODO Auto-generated method stub

	}


}

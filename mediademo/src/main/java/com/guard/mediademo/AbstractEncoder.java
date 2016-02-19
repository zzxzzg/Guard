package com.guard.mediademo;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;

public abstract class  AbstractEncoder {
	
	public abstract boolean canEncoder();
	public abstract void initEncoder(int width, int height, int framerate, int bitrate,int previewFormat,int orientation);
	public abstract void closeEncoder();
	public abstract void offerEncoder(byte[] input);
	public abstract void setCallBack(EncodeManager.DateCallBack callback);
	
	public static MediaCodecInfo selectCodec(String 
			  mimeType) {
			     int numCodecs = MediaCodecList.getCodecCount();
			     for (int i = 0; i < numCodecs; i++) {
			         MediaCodecInfo codecInfo = 
			          MediaCodecList.getCodecInfoAt(i);

			         if (!codecInfo.isEncoder()) {
			             continue;
			         }

			         String[] types = codecInfo.getSupportedTypes();
			         for (int j = 0; j < types.length; j++) {
			             if (types[j].equalsIgnoreCase(mimeType)) {
			                 return codecInfo;
			             }
			         }
			     }
			     return null;
			 }
	
	
}

package com.guard.mediademo;

import android.os.Build;

public class EncoderFactory {
	public static AbstractEncoder createEncoder(){
//		if(Build.VERSION.SDK_INT>=21){
//			return new EncoderSDKVersion21();
//		}else 
			if(Build.VERSION.SDK_INT>=16){
			return new EncoderSDKVersion16();
		}else{
			return new EncoderSDKVersion15();
		}
	}
}
 
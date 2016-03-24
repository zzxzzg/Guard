package com.guard.openh264demo;

/**
 * Created by yxwang on 16-3-21.
 */
public enum OpenH264Model {
    INSTAICE;
    static{
        System.loadLibrary("openh264model");
        System.loadLibrary("openh264");
    }

    public native void nativeInitOpenH264Encoder();
    public native void nativePutYUVDate(byte[] bytes,int width,int height);
    public native void nativeStopOpenH264Encoder();
    public native void nativeStartOpenH264Encoder();
    public native void nativeSetOpenH264Param(int width,int height,int frameRate,int bitrate);
}

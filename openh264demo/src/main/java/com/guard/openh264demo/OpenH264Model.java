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

    public native void init(int width,int height);
    public native void nativePutLocalDate(byte[] bytes,int width,int height);
}

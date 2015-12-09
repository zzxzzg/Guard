package com.guard.camerademo;

import android.content.Context;
import android.hardware.camera2.*;

/**
 * Created by yxwang on 15-12-9.
 */
public class CameraManager2 {
    public static CameraManager2 getInstance(Context context){
        if(mCameraManager==null){
            mCameraManager2=new CameraManager2(context);
        }
        return mCameraManager2;
    }

    private Context mContext;
    private static CameraManager2 mCameraManager2;

    private static android.hardware.camera2.CameraManager mCameraManager;

    private int mCurrentCameraInfo;

    private CameraManager2(Context context){
        mContext=context;
        mCameraManager= (android.hardware.camera2.CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }


}

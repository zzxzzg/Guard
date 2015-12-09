package com.guard.camerademo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import java.io.IOException;

/**
 * Created by yxwang on 15-12-9.
 */
public class CameraManager {

    public static int CAMERA_FACING_FRONT=Camera.CameraInfo.CAMERA_FACING_FRONT;

    public static int CAMERA_FACING_BACK= Camera.CameraInfo.CAMERA_FACING_BACK;

    public static CameraManager getInstance(Context context){
        if(mCameraManager==null){
            mCameraManager=new CameraManager(context);
        }
        return mCameraManager;
    }

    private Context mContext;
    private static CameraManager mCameraManager;
    private Camera mCamera;
    private int mCurrentCameraInfo= Camera.CameraInfo.CAMERA_FACING_BACK;

    private CameraManager(Context context){
        mContext=context;
    }

    private boolean checkCameraHardware() {
        if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

//    public void openCameraSync(final int camera){
//        if(isOpeningCamera){
//            throw new RuntimeException("the camera is opening!");
//        }
//        isOpeningCamera=true;
//        mOpenThread=new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                openCamera(camera);
//                isOpeningCamera=false;
//            }
//        };
//        mOpenThread.start();
//    }

    public void openCamera(int camera){
        try {
            if (!checkCameraHardware()) {
                return;
            }
            releaseCamera();
            if (camera != Camera.CameraInfo.CAMERA_FACING_BACK && camera != Camera.CameraInfo.CAMERA_FACING_FRONT) {
                camera = Camera.CameraInfo.CAMERA_FACING_BACK;
            }

            int count = Camera.getNumberOfCameras();
            if (count <= 0) {
                return;
            }
            Camera.CameraInfo info = new Camera.CameraInfo();
            for (int i = 0; i < count; i++) {
                Camera.getCameraInfo(i, info);
                if (info.facing == camera) {
                    mCamera = Camera.open(i);
                    mCurrentCameraInfo = camera;
                    return;
                }
            }

            mCamera = Camera.open(0);
            Camera.getCameraInfo(0, info);
            mCurrentCameraInfo = info.facing;
        }catch(Exception e){
            mCamera=null;
        }
    }

    public void setupCamera(){
        if(mCamera!=null){
            Camera.Parameters parameters=mCamera.getParameters();
        }
    }


    public void releaseCamera(){
        if(mCamera!=null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera=null;
        }
    }


}

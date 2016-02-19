package com.guard.mediademo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;

/**
 * Created by yxwang on 16-2-19.
 */
public class BaseActivity extends Activity {
    private WindowOrientationListener mWindowOrientationListener;

    private Handler mDelayHandler = new Handler() {
        public void handleMessage(Message msg) {
            judgeOrientation(msg.arg1);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWindowOrientationListener = new WindowOrientationListener(this) {

            @Override
            public void onProposedRotationChanged(int rotation) {
                mDelayHandler.removeMessages(0);
//				int isCanOrientation=Settings.System.getInt(
//						getContentResolver(),
//						Settings.System.ACCELEROMETER_ROTATION, 0);
//				Log.d(YXWANG_TAG,"isCanOrientation="+isCanOrientation);
                Message msg = Message.obtain();
                msg.what = 0;
                msg.arg1 = rotation;
                mDelayHandler.sendMessageDelayed(msg, 1000);
            }
        };
        mWindowOrientationListener.disable();

    }

    public void setOrientationEnable(){
        mWindowOrientationListener.enable();
    }

    public void setOrientationDisable(){
        mWindowOrientationListener.disable();
    }

    private void judgeOrientation(int rotation) {
        if (rotation == Surface.ROTATION_0) {
            onOrientationChange(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (rotation == Surface.ROTATION_90
                && getResources().getConfiguration().orientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            onOrientationChange(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (rotation == Surface.ROTATION_180) {
            onOrientationChange(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        } else if (rotation == Surface.ROTATION_270
                && getResources().getConfiguration().orientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
            onOrientationChange(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }
    }

    public void onOrientationChange(int requestedOrientation){

    }
}

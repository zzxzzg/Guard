package com.guard.gof.command;

import android.util.Log;

/**
 * Created by yxwang on 16-2-18.
 */
public class TV {

    public void turnOn(){
        Log.d("sss", "打开电视");
    }

    public void turnOff(){
        Log.d("sss","关闭电视");
    }

    public void changeChannel(){
        Log.d("sss","切换频道");
    }
}

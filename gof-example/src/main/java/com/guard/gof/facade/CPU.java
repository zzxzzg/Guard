package com.guard.gof.facade;

import android.util.Log;

/**
 * Created by yxwang on 16-2-17.
 */
public class CPU  {
    public void freeze(){
        Log.d("sss", "通电");
    }

    public void jump(){
        Log.d("sss", "跳转到启动命令块");
    }

    public void execute(){
        Log.d("sss", "运行");
    }
}

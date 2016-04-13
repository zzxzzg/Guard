package com.guard.dagger2demo.demo1;

import android.os.Build;

import javax.inject.Inject;

/**
 * Created by yxwang on 16-4-13.
 */
public class OSHelp {
    @Inject
    public OSHelp(){

    }

    public String getDeviceBrand(){
        return Build.BRAND;
    }
}

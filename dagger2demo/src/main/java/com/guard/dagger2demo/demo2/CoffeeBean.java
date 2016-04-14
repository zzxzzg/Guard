package com.guard.dagger2demo.demo2;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by yxwang on 16-4-14.
 */
public class CoffeeBean {
    @Inject
    public CoffeeBean(){

    }

    public void hot(){
        Log.d("sss","hot the coffee bean");
    }
}

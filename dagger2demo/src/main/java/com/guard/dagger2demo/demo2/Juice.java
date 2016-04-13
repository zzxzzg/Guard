package com.guard.dagger2demo.demo2;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by apple on 16/4/13.
 */
public class Juice {
    @Inject
    public Juice(){

    }

    public void drink(){
        Log.d("sss","drink juice");
    }
}

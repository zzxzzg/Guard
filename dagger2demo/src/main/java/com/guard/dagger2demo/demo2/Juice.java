package com.guard.dagger2demo.demo2;

import android.util.Log;

import javax.inject.Inject;

import dagger.Provides;

public class Juice {
    @Inject
    public Juice(){

    }

    public void drink(){
        Log.d("sss","drink juice");
    }
}

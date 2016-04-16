package com.guard.dagger2demo.demo2;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by yxwang on 16-4-13.
 */
public class Coffee {
    @Inject
    public Coffee(CoffeeBean bean){
        bean.hot();
    }

    public void drink(){
        Log.d("sss","drink");
    }
}

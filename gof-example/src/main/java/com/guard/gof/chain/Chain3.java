package com.guard.gof.chain;

import android.util.Log;

/**
 * Created by yxwang on 16-2-18.
 */
public class Chain3 extends Handler {
    @Override
    public void handleRequest(int request) {
        if(request<30){
            Log.d("sss", Chain3.class.getName() + " handle it!");
        }else{
            if(getNextHandler()!=null) {
                getNextHandler().handleRequest(request);
            }
        }

    }
}

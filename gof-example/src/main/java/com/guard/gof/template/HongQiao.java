package com.guard.gof.template;

import android.util.Log;

/**
 * Created by yxwang on 16-2-17.
 */
public class HongQiao extends Restaurant {
    @Override
    protected void pickSeat() {
        Log.d("sss", "随便坐");
    }

    @Override
    protected void order() {
        Log.d("sss","选个面，直接报给老板");
    }

    @Override
    protected void eat() {
        Log.d("sss","吃饭");
    }

    @Override
    protected void pay() {
        Log.d("sss","现金付钱");
    }
}

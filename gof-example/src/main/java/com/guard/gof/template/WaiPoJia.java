package com.guard.gof.template;

import android.util.Log;

/**
 * Created by yxwang on 16-2-17.
 */
public class WaiPoJia extends Restaurant {
    @Override
    protected void pickSeat() {
        Log.d("sss", "排队");
    }

    @Override
    protected void order() {
        Log.d("sss", "电子菜单点菜");
    }

    @Override
    protected void eat() {
        Log.d("sss", "上菜吃");
    }

    @Override
    protected void pay() {
        Log.d("sss", "银行卡买单");
    }
}

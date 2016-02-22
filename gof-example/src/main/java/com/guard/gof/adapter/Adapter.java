package com.guard.gof.adapter;

import android.util.Log;

/**
 * Created by yxwang on 16-2-22.
 */
public class Adapter extends CurrentPerson implements TargetInterface {


    @Override
    public void doThing1() {
        super.dothing1();
    }

    @Override
    public void doThing2() {
        super.dothing2();
    }

    @Override
    public void doThing3() {
        Log.d("sss", "do thing 3");
    }
}

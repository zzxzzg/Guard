package com.guard.gof.observer;

import android.util.Log;

/**
 * Created by yxwang on 16-2-17.
 */
public class ObserverB implements Observer {
    @Override
    public void update() {
        Log.d("sss", "observer B");
    }
}

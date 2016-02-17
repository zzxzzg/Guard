package com.guard.gof.state;

import android.util.Log;

/**
 * Created by yxwang on 16-2-17.
 */
public class StateB implements State{

    @Override
    public void handle(Context context) {
        Log.d("sss", "current state is B");
        context.setState(new StateA());
    }
}

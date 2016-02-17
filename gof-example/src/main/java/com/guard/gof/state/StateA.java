package com.guard.gof.state;

import android.util.Log;

/**
 * Created by yxwang on 16-2-17.
 */
public class StateA implements State {
    @Override
    public void handle(Context context) {
        Log.d("sss", "current state is A");
        context.setState(new StateB());
    }
}

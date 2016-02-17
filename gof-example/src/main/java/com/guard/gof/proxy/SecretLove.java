package com.guard.gof.proxy;

import android.util.Log;

/**
 * Created by yxwang on 16-2-17.
 */
public class SecretLove implements ISecret {
    @Override
    public void message() {
        Log.d("sss", "I LOVE SOMEONE");
    }
}

package com.guard.httpstest;

import android.content.Context;

/**
 * Created by yxwang on 15-12-3.
 */
public abstract class BaseHttpsTest {
    public abstract void connect();

    public abstract void connectAllTrust();

    public abstract void connectSelfSign(Context context);
}

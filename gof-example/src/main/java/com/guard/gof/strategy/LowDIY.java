package com.guard.gof.strategy;

import android.util.Log;

/**
 * Created by yxwang on 16-2-17.
 */
public class LowDIY implements DIYStrategy {
    @Override
    public PC DIYPC() {
        Log.d("sss", "配置一台低端PC");
        return new PC();
    }
}

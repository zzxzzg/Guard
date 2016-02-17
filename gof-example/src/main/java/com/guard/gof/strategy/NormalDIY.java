package com.guard.gof.strategy;

import android.util.Log;

/**
 * Created by yxwang on 16-2-17.
 */
public class NormalDIY implements DIYStrategy {
    @Override
    public PC DIYPC() {
        Log.d("sss", "配置一台中端PC");
        return new PC();
    }
}

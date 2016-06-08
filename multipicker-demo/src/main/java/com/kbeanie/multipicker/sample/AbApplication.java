package com.kbeanie.multipicker.sample;

import android.app.Application;


/**
 * Created by kbibek on 2/18/16.
 */
public class AbApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG) {
        }
    }
}

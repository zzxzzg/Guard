package com.guard.dagger2demo.demo1;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yxwang on 16-4-12.
 */
@Module
public class AppModule {
    private MyApplication mApplication;

    public AppModule(MyApplication application){
        mApplication=application;
    }

    @Provides
    @Singleton
    public Application providesApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    public Context providesContext(){
        return mApplication;
    }
}

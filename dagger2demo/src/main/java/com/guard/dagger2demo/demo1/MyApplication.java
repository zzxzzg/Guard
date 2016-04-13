package com.guard.dagger2demo.demo1;

import android.app.Application;

import javax.inject.Inject;

/**
 * Created by yxwang on 16-4-12.
 */
public class MyApplication extends Application {
    private AppComponent mComponent=null;

    @Inject
    FakeManager mFakeManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent=DaggerAppComponent.builder().appModule(new AppModule(this)).domainModule(new DomainModule()).build();
        mComponent.inject(this);

        mFakeManager.register();

    }

    public AppComponent getAppComponent() {
        return mComponent;
    }
}

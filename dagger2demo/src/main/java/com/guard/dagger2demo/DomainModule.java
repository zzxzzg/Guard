package com.guard.dagger2demo;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yxwang on 16-4-12.
 */
@Module
public class DomainModule {
    @Provides
    @Singleton
    public FakeManager provideAnalyticsManager(Context context) {
        return new FakeManager(context);
    }
}

package com.guard.dagger2demo.demo1;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yxwang on 16-4-12.
 */

@Component(
        modules = {
                AppModule.class,
                DomainModule.class
        }
)
@Singleton
public interface AppComponent {
    MyApplication inject(MyApplication app);
}

package com.guard.dagger2demo.demo5;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yxwang on 16-4-15.
 */
@Module
public class AModule {
    @Provides
    SomeClassA provideSomeClassA(){
        return new SomeClassA();
    }
}

package com.guard.dagger2demo.demo4;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yxwang on 16-4-15.
 */
@Module
public class BeanModule {
    @Provides
    BeanTypeA providerBeanTypeA(){
        return new BeanTypeA();
    }

    @Provides
    BeanTypeB providerBeanTypeB(){
        return new BeanTypeB();
    }
}

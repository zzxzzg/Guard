package com.guard.dagger2demo.demo3;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yxwang on 16-4-14.
 */
@Module
public class NameModule {
    @Provides
    @Country("china")
    Name getChinaName(){
        return new Name("wang","yxwang");
    }

    @Provides
    @Country("foreigner")
    Name getForeignerName(){
        return new Name("yxwang","wang","D");
    }
}

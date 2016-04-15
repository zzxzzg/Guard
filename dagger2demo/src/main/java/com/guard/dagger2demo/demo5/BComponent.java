package com.guard.dagger2demo.demo5;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by yxwang on 16-4-15.
 */
@Subcomponent(modules = BModule.class)
public interface BComponent {
    SomeClassB getSomeClassB();
    void inject(User user);
}

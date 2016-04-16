package com.guard.dagger2demo.demo3;

import dagger.Component;

/**
 * Created by yxwang on 16-4-14.
 */
@Component(modules = NameModule.class)
public interface NameComponent {
    void inject(Person person);
}

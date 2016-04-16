package com.guard.dagger2demo.demo4;

import dagger.Component;

/**
 * Created by yxwang on 16-4-15.
 */
@Component(modules = BeanModule.class)
public interface BeanComponent {
    BeanTypeA getBeanTypeA();
    BeanTypeB getBeanTypeB();
}

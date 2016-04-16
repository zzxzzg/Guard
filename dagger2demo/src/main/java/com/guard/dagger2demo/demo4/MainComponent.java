package com.guard.dagger2demo.demo4;

import dagger.Component;

/**
 * Created by yxwang on 16-4-15.
 */
@Component(dependencies = BeanComponent.class)
public interface MainComponent {
    void inject(User user);
    RealObj getRealObj();
}

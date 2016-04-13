package com.guard.dagger2demo.demo1;

import dagger.Component;

/**
 * Created by yxwang on 16-4-13.
 */
@Component(dependencies = AppComponent.class)
@ComponentScope
public interface ActivityComponent {
    void inject(MainActivity activity);
    OSHelp getOSHelp();
}

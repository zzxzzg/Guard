package com.guard.dagger2demo.demo5;

import dagger.Component;
import dagger.Module;

/**
 * Created by yxwang on 16-4-15.
 */
@Component(modules = AModule.class)
public interface AComponent {
    BComponent getBComponent(BModule bModule);
}

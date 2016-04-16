package com.guard.dagger2demo.demo6;

import dagger.Component;

/**
 * Created by apple on 16/4/16.
 */
@Component(modules = ParentModule.class)
public interface ParentComponent {
    void Inject(User user);
}

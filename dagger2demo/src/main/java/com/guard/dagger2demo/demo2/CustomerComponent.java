package com.guard.dagger2demo.demo2;

import dagger.Component;

/**
 * Created by yxwang on 16-4-13.
 */
@Component()
public interface CustomerComponent {
    public void inject(Customer customer);
}

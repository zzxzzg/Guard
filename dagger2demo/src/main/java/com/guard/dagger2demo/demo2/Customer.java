package com.guard.dagger2demo.demo2;

import javax.inject.Inject;

/**
 * Created by yxwang on 16-4-13.
 */
public class Customer {
    @Inject
    Coffee mCoffee;

    public Customer(){
        CustomerComponent component=DaggerCustomerComponent.builder().build();
        component.inject(this);
        drink();
    }

    public void drink(){
        mCoffee.drink();
    }
}

package com.guard.dagger2demo.demo2;

import javax.inject.Inject;

/**
 * Created by yxwang on 16-4-13.
 */
public class Customer {
    @Inject
    Coffee mCoffee;

    Juice mJuice;

    public Customer(){
        CustomerComponent component=DaggerCustomerComponent.builder().build();
        component.inject(this);
        mJuice=component.getJuice();
        drink();
    }

    public void drink(){
        mCoffee.drink();
        mJuice.drink();
    }
}

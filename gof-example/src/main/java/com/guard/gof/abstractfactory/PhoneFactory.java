package com.guard.gof.abstractfactory;

/**
 * Created by yxwang on 16-2-16.
 */
public class PhoneFactory implements AbstractFactory {
    @Override
    public Product createProcessor() {
        return new ARM();
    }

    @Override
    public Product createMemory() {
        return new RAM();
    }
}

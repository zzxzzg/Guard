package com.guard.gof.abstractfactory;

/**
 * Created by yxwang on 16-2-16.
 */
public class PCFactory implements AbstractFactory {
    @Override
    public Product createProcessor() {
        return new CPU();
    }

    @Override
    public Product createMemory() {
        return new SSD();
    }
}

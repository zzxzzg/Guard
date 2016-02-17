package com.guard.gof.abstractfactory;

/**
 * Created by yxwang on 16-2-16.
 */
public interface AbstractFactory  {
    Product createProcessor();
    Product createMemory();
}

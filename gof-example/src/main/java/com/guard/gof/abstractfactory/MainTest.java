package com.guard.gof.abstractfactory;

/**
 * Created by yxwang on 16-2-16.
 */
// 抽象工厂
public class MainTest {
    public void execute(){
        AbstractFactory pcFactory=new PCFactory();
        pcFactory.createMemory();
        pcFactory.createProcessor();

        AbstractFactory phoneFactory=new PhoneFactory();
        phoneFactory.createMemory();
        phoneFactory.createProcessor();
    }
}

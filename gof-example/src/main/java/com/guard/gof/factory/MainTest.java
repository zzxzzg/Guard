package com.guard.gof.factory;

/**
 * Created by yxwang on 16-2-16.
 */
// 工厂模式
public class MainTest {
    public void execute(){
        Product product1=Factory.createProcessor(Factory.TYPE_PC);
        Product product2=Factory.createProcessor(Factory.TYPE_PHONE);
    }
}

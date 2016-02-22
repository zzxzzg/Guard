package com.guard.gof.builder;

/**
 * Created by yxwang on 16-2-22.
 */
public class Director {
    public Computer createComputer(Builder builder){
        builder.createBIOS().createCpu().createHardDisk().createIO().createMemory().createScreen();
        return builder.create();
    }
}

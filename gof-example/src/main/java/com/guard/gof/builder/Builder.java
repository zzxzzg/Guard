package com.guard.gof.builder;



/**
 * Created by yxwang on 16-2-22.
 */
public interface Builder {
    Builder createCpu();
    Builder createMemory();
    Builder createHardDisk();
    Builder createScreen();
    Builder createBIOS();
    Builder createIO();
    Computer create();
}

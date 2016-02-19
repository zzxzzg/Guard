package com.guard.gof.facade;

/**
 * Created by yxwang on 16-2-17.
 */
//外观模式
public class Main {
    public void execute(){
//        CPU cpu=new CPU();
//        Memory memory=new Memory();
//        HardDisk hardDisk=new HardDisk();
//
//        cpu.freeze();
//        hardDisk.read();
//        memory.load();
//        cpu.jump();
//        cpu.execute();
        Compute compute=new Compute();
        compute.boot();
    }
}

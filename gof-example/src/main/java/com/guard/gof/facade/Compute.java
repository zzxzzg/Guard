package com.guard.gof.facade;

/**
 * Created by yxwang on 16-2-17.
 */
public class Compute {
    CPU cpu=new CPU();
    Memory memory=new Memory();
    HardDisk hardDisk=new HardDisk();


    public Compute(){
         cpu=new CPU();
         memory=new Memory();
         hardDisk=new HardDisk();


    }

    public void boot(){
        cpu.freeze();
        hardDisk.read();
        memory.load();
        cpu.jump();
        cpu.execute();
    }
}

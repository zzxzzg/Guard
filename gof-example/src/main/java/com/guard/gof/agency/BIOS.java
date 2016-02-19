package com.guard.gof.agency;

/**
 * Created by yxwang on 16-2-17.
 */
public class BIOS {
    public void requestMemory(CPU cpu, Memory memory){
        //cpu request form memory
    }

    public void requestCalculate(CPU cpu, Memory memory){
        // memory request cpu to calculate something
    }

    public void writeFile(HardDisk hardDisk,CPU cpu,Memory memory){
        //cpu ask hard disk write the byte form memory
    }


}

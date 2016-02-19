package com.guard.gof.agency;

/**
 * Created by yxwang on 16-2-17.
 */
//中介者模式  the bios is the agency!
public class Main {
    public void execute(){
        CPU cpu=new CPU();
        HardDisk hardDisk=new HardDisk();
        Memory memory=new Memory();

        BIOS bios=new BIOS();
        bios.requestMemory(cpu,memory);
        bios.requestCalculate(cpu,memory);
        bios.writeFile(hardDisk,cpu,memory);

    }
}

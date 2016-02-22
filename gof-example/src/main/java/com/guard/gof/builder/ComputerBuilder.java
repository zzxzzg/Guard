package com.guard.gof.builder;

/**
 * Created by yxwang on 16-2-22.
 */
public class ComputerBuilder implements Builder{

    Computer mComputer;

    public ComputerBuilder(){
        mComputer=new Computer();
    }

    @Override
    public Builder createCpu() {
        mComputer.setCpu("computer cpu");
        return this;
    }

    @Override
    public Builder createMemory() {
        mComputer.setMemory("computer memory");
        return this;
    }

    @Override
    public Builder createHardDisk() {
        mComputer.setHardDisk("computer hard disk");
        return this;
    }

    @Override
    public Builder createScreen() {
        mComputer.setScreen("computer screen");
        return this;
    }

    @Override
    public Builder createBIOS() {
        mComputer.setBIOS("computer bios");
        return this;
    }

    @Override
    public Builder createIO() {
        mComputer.setIO("computer io");
        return this;
    }

    @Override
    public Computer create() {
        return mComputer;
    }
}

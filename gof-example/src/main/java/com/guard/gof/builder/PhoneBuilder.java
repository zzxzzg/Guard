package com.guard.gof.builder;

/**
 * Created by yxwang on 16-2-22.
 */
public class PhoneBuilder implements Builder{

    Phone mComputer;

    public PhoneBuilder(){
        mComputer=new Phone();
    }

    @Override
    public Builder createCpu() {
        mComputer.setCpu("Phone cpu");
        return this;
    }

    @Override
    public Builder createMemory() {
        mComputer.setMemory("Phone memory");
        return this;
    }

    @Override
    public Builder createHardDisk() {
        mComputer.setHardDisk("Phone hard disk");
        return this;
    }

    @Override
    public Builder createScreen() {
        mComputer.setScreen("Phone screen");
        return this;
    }

    @Override
    public Builder createBIOS() {
        mComputer.setBIOS("Phone bios");
        return this;
    }

    @Override
    public Builder createIO() {
        mComputer.setIO("Phone io");
        return this;
    }

    @Override
    public Computer create() {
        return mComputer;
    }
}

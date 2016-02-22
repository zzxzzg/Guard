package com.guard.gof.builder;


/**
 * Created by yxwang on 16-2-22.
 */
public class Computer {
    private String mCpu;
    private String mMemory;
    private String mHardDisk;
    private String mScreen;
    private String mBIOS;
    private String mIO;

    public Computer(String cpu,String memory,String hardDisk,String screen,String bios,String io){
        mCpu=cpu;
        mMemory=memory;
        mHardDisk=hardDisk;
        mScreen=screen;
        mBIOS=bios;
        mIO=io;
    }

    public Computer(){

    }

    public String getCpu() {
        return mCpu;
    }

    public void setCpu(String mCpu) {
        this.mCpu = mCpu;
    }

    public String getMemory() {
        return mMemory;
    }

    public void setMemory(String mMemory) {
        this.mMemory = mMemory;
    }

    public String getHardDisk() {
        return mHardDisk;
    }

    public void setHardDisk(String mHardDisk) {
        this.mHardDisk = mHardDisk;
    }

    public String getScreen() {
        return mScreen;
    }

    public void setScreen(String mScreen) {
        this.mScreen = mScreen;
    }

    public String getBIOS() {
        return mBIOS;
    }

    public void setBIOS(String mBIOS) {
        this.mBIOS = mBIOS;
    }

    public String getIO() {
        return mIO;
    }

    public void setIO(String mIO) {
        this.mIO = mIO;
    }
}

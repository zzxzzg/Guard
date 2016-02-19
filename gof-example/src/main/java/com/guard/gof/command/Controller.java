package com.guard.gof.command;

/**
 * Created by yxwang on 16-2-18.
 */
public class Controller {

    private Command mTurnOn;
    private Command mTurnOff;
    private Command mChangeChannel;

    public Controller(Command turnon,Command turnoff,Command changeChannel){
        mTurnOn=turnon;
        mTurnOff=turnoff;
        mChangeChannel=changeChannel;
    }

    public void turnOn(){
        mTurnOn.execute();
    }

    public void turnOff(){
        mTurnOff.execute();
    }

    public void changeChannel(){
        mChangeChannel.execute();
    }
}

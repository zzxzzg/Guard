package com.guard.gof.command;

/**
 * Created by yxwang on 16-2-18.
 */
public class TurnOn implements Command {

    private TV mTV;

    public TurnOn(TV tv){
        mTV=tv;
    }

    @Override
    public void execute() {
        mTV.turnOn();
    }
}

package com.guard.gof.command;

/**
 * Created by yxwang on 16-2-18.
 */
public class TurnOff implements Command {
    private TV mTV;

    public TurnOff(TV tv){
        mTV=tv;
    }

    @Override
    public void execute() {
        mTV.turnOff();
    }
}

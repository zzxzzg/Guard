package com.guard.gof.command;

/**
 * Created by yxwang on 16-2-18.
 */
public class ChangeChannel implements Command{
    private TV mTV;

    public ChangeChannel(TV tv){
        mTV=tv;
    }

    @Override
    public void execute() {
        mTV.changeChannel();
    }
}

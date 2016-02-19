package com.guard.gof.command;

/**
 * Created by yxwang on 16-2-18.
 */
//命令模式
public class Main {
    public void execute(){
        TV tv=new TV();
        Command changeChannel=new ChangeChannel(tv);
        Command turnOn=new TurnOn(tv);
        Command turnOff=new TurnOff(tv);

        Controller controller=new Controller(turnOn,turnOff,changeChannel);
        controller.turnOn();

        controller.changeChannel();

        controller.turnOff();
    }
}

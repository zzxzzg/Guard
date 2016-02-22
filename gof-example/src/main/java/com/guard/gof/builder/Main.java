package com.guard.gof.builder;

/**
 * Created by yxwang on 16-2-22.
 */
//建造者模式
public class Main {
    public void execute(){
        Director director=new Director();
        director.createComputer(new PhoneBuilder());
        director.createComputer(new ComputerBuilder());
    }
}

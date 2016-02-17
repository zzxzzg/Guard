package com.guard.gof.prototype;

/**
 * Created by yxwang on 16-2-17.
 */
//原型模式
public class Main {
    public void execute(){
        Prototype prototype1=new Prototype();
        // do something for  prototype1

        Prototype prototype2=prototype1.clone();

    }
}

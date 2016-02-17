package com.guard.gof.observer;

/**
 * Created by yxwang on 16-2-17.
 */
//原型模式
public class Main {
    public void execute(){
        DateBase database=new DateBase();
        //attach
        database.attach(new ObserverA());
        database.attach(new ObserverA());
        database.attach(new ObserverB());

        //change


        //notify
        database.notifyChange();
    }
}

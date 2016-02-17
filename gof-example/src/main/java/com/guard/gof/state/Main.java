package com.guard.gof.state;

/**
 * Created by yxwang on 16-2-17.
 */
public class Main {
    public void execute(){
        //init
        Context context=new Context(new StateA());

        context.request();
        context.request();
    }
}

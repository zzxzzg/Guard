package com.guard.gof.strategy;

/**
 * Created by yxwang on 16-2-17.
 */
//策略模式
public class Main {
    public void execute(){
        //配置一台高端PC
        PCContext context=new PCContext(new DIYHigh());
        context.DIY();

        //配置一台低端PC
        PCContext context1=new PCContext(new LowDIY());
        context.DIY();
    }
}

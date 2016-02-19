package com.guard.gof.chain;

/**
 * Created by yxwang on 16-2-18.
 */
//责任链模式 注意，责任链模式就和想象的一样，类和类之间会出现耦合，但是由于继承关系，可以说是松散耦合（个人理解）
public class Main {
    public void execute(){
        Handler chain1=new Chain1();
        Handler chain2=new Chain2();
        Handler chain3=new Chain3();
        chain1.setNextHandler(chain2);
        chain2.setNextHandler(chain3);

        chain1.handleRequest(15);
    }
}

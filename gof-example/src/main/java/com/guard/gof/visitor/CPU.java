package com.guard.gof.visitor;


/**
 * Created by yxwang on 16-2-16.
 */
public class CPU extends Product {
    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}

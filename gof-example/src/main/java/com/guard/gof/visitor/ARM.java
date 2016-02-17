package com.guard.gof.visitor;


/**
 * Created by yxwang on 16-2-16.
 */
public class ARM extends Product {
    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}

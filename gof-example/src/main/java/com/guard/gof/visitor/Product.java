package com.guard.gof.visitor;

/**
 * Created by yxwang on 16-2-16.
 */
public abstract class Product {
    public abstract void accept(Visitor visitor);
}

package com.guard.gof.visitor;

/**
 * Created by yxwang on 16-2-16.
 */
public interface Visitor {
    public void visit(CPU cpu);
    public void visit(ARM arm);
}

package com.guard.gof.tree;

/**
 * Created by yxwang on 16-2-18.
 */
//组合模式
public class Main {
    public void execute(){
        Component root=new Limb();

        root.addComponent(new Leaf());
        //...
    }
}

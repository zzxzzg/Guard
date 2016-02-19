package com.guard.gof.tree;

/**
 * Created by yxwang on 16-2-18.
 */
public abstract class Component {
    private String mName;
    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName=name;
    }

    public abstract void addComponent(Component component);
    public abstract Component removeComponent(Component component);
}

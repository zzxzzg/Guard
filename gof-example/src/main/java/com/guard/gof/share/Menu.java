package com.guard.gof.share;

/**
 * Created by yxwang on 16-2-17.
 */
public class Menu implements IShareCell{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

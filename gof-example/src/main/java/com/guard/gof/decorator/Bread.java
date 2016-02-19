package com.guard.gof.decorator;

/**
 * Created by yxwang on 16-2-18.
 */
public class Bread extends Ingredient {

    private String description ;

    public Bread(String desc){
        this.description=desc ;
    }

    @Override
    public String getDescription(){
        return description ;
    }

    @Override
    public double getCost(){
        return 2.48 ;
    }
}

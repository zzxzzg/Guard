package com.guard.gof.decorator;

/**
 * Created by yxwang on 16-2-18.
 */
public abstract class Ingredient {
    public abstract String getDescription();
    public abstract double getCost();
    public void printDescription(){
        System.out.println(" Name      "+ this.getDescription());
        System.out.println(" Price RMB "+ this.getCost());
    }
}

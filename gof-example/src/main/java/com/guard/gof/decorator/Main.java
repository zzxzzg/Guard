package com.guard.gof.decorator;

/**
 * Created by yxwang on 16-2-18.
 */
//装饰者模式
public class Main {
    public void execute(){
        Ingredient sandwich;
        Ingredient bread=new Bread("bread");
        Ingredient meet=new Meet(bread);
        Ingredient mutton=new Mutton(meet);
        sandwich = new GreenGrocery(mutton);

    }
}

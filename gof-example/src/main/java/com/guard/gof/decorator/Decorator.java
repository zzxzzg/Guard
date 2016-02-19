package com.guard.gof.decorator;

/**
 * Created by yxwang on 16-2-18.
 */
public abstract class Decorator extends Ingredient {
    private Ingredient mIngredient;
    public Decorator(Ingredient ingredient){
        mIngredient=ingredient;
    }

    public Ingredient getIngredient(){
        return mIngredient;
    }
}

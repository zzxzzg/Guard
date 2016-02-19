package com.guard.gof.decorator;

/**
 * Created by yxwang on 16-2-18.
 */
public class GreenGrocery extends Decorator{
    public GreenGrocery(Ingredient ingredient) {
        super(ingredient);
    }

    @Override
    public String getDescription() {
        String base = getIngredient().getDescription();
        return base +"\n"+"Decrocated with GreenGrocery !";
    }

    @Override
    public double getCost() {
        double basePrice = getIngredient().getCost();
        double greenGroceryPrice = 1.0;
        return        basePrice + greenGroceryPrice ;
    }
}

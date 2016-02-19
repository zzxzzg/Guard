package com.guard.gof.decorator;

/**
 * Created by yxwang on 16-2-18.
 */
public class Mutton extends Decorator {

    public Mutton(Ingredient ingredient) {
        super(ingredient);
    }

    @Override
    public String getDescription() {
        String base = getIngredient().getDescription();
        return base +"\n"+"Decrocated with Mutton !";
    }

    @Override
    public double getCost() {
        double basePrice = getIngredient().getCost();
        double muttonPrice = 2.0;
        return        basePrice + muttonPrice ;
    }
}

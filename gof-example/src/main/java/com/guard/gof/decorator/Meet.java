package com.guard.gof.decorator;

/**
 * Created by yxwang on 16-2-18.
 */
public class Meet extends Decorator {

    public Meet(Ingredient ingredient) {
        super(ingredient);
    }


    public String getDescription(){
        String base = getIngredient().getDescription();
        return base +"\n"+"Decrocated with Pork !";
    }


    public double getCost(){
        double basePrice = getIngredient().getCost();
        double porkPrice = 1.8;
        return        basePrice + porkPrice ;
    }
}

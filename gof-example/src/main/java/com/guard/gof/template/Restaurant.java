package com.guard.gof.template;

/**
 * Created by yxwang on 16-2-17.
 */
public abstract class Restaurant {
    public void haveDinner(){
        pickSeat();
        order();
        eat();
        pay();
    }

    protected abstract void pickSeat();

    protected abstract void order();

    protected abstract void eat();

    protected abstract void pay();

}

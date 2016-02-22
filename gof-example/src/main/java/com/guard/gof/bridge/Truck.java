package com.guard.gof.bridge;

/**
 * Created by yxwang on 16-2-22.
 */
public class Truck implements ICar{
    @Override
    public String getName() {
        return "Truck";
    }
}

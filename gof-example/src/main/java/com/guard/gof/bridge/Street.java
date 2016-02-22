package com.guard.gof.bridge;

/**
 * Created by yxwang on 16-2-22.
 */
public class Street implements IRoad {
    @Override
    public String getName() {
        return "Street";
    }
}

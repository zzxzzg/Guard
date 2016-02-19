package com.guard.gof.chain;

/**
 * Created by yxwang on 16-2-18.
 */
public abstract class Handler {
    private Handler mNextHandler;

    public void setNextHandler(Handler mNextHandler) {
        this.mNextHandler = mNextHandler;
    }

    public Handler getNextHandler(){
        return mNextHandler;
    }

    public abstract void handleRequest(int request);
}

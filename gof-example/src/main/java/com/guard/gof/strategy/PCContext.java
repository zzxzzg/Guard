package com.guard.gof.strategy;

/**
 * Created by yxwang on 16-2-17.
 */
public class PCContext {
    private DIYStrategy mDIYStrategy;

    public PCContext(DIYStrategy strategy){
        mDIYStrategy=strategy;
    }

    public PC DIY(){
        return mDIYStrategy.DIYPC();
    }
}

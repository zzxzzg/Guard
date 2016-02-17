package com.guard.gof.memento;

/**
 * Created by yxwang on 16-2-17.
 */
public class Memento {
    private int mHp;
    private int mMp;
    public void setHp(int hp){
        mHp=hp;
    }

    public int getHp(){
        return mHp;
    }

    public void setMp(int mp){
        mMp=mp;
    }

    public int getMp(){
        return mMp;
    }
}

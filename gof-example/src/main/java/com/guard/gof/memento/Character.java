package com.guard.gof.memento;

/**
 * Created by yxwang on 16-2-17.
 */
public class Character {
    private int mHp;
    private int mMp;
    private int mAttack;

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

    public Memento createMemento(){
        Memento memento=new Memento();
        memento.setHp(mHp);
        memento.setMp(mMp);
        return memento;
    }

    public void reset(Memento memento){
        mHp=memento.getHp();
        mMp=memento.getMp();
    }

}

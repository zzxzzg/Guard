package com.guard.gof.adapter;

import android.util.Log;

/**
 * Created by yxwang on 16-2-22.
 */
public class Adapter2 implements TargetInterface {

    private CurrentPerson mCurrentPerson;

    public Adapter2(CurrentPerson currentPerson){
        mCurrentPerson=currentPerson;
    }

    @Override
    public void doThing1() {
        if(mCurrentPerson!=null){
            mCurrentPerson.dothing1();
        }
    }

    @Override
    public void doThing2() {
        if(mCurrentPerson!=null){
            mCurrentPerson.dothing2();
        }
    }

    @Override
    public void doThing3() {
        Log.d("sss", "do thing 3");
    }

    public CurrentPerson getCurrentPerson() {
        return mCurrentPerson;
    }

    public void setCurrentPerson(CurrentPerson mCurrentPerson) {
        this.mCurrentPerson = mCurrentPerson;
    }
}

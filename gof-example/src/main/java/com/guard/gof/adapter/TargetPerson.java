package com.guard.gof.adapter;

/**
 * Created by yxwang on 16-2-22.
 */
public class TargetPerson {
    private TargetInterface mTarget;

    public TargetInterface getTarget() {
        return mTarget;
    }

    public void setTarget(TargetInterface mTarget) {
        this.mTarget = mTarget;
    }

    public void dothing1(){
        mTarget.doThing1();
    }

    public void dothing2(){
        mTarget.doThing2();
    }

    public void dothing3(){
        mTarget.doThing3();
    }
}

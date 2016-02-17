package com.guard.gof.state;

/**
 * Created by yxwang on 16-2-17.
 */
public class Context {
    private State mState;

    public Context(State state){
        mState=state;
    }

    public void setState(State state){
        mState=state;
    }

    public State getState(){
        return mState;
    }

    public void request(){
        mState.handle(this);
    }

}

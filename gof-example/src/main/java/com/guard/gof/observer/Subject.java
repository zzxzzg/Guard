package com.guard.gof.observer;

/**
 * Created by yxwang on 16-2-17.
 */
//被观察者
public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyChange();
}

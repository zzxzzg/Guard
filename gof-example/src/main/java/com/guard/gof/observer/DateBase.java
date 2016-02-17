package com.guard.gof.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16-2-17.
 */
public class DateBase implements Subject {
    List<Observer> mObservers=new ArrayList<>();
    @Override
    public void attach(Observer observer) {
        mObservers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        mObservers.remove(observer);
    }

    @Override
    public void notifyChange() {
        for(Observer observer:mObservers){
            observer.update();
        }
    }
}

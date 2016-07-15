package com.guard.rxjava.demo;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by yxwang on 16/7/11.
 */
public class Test4 {
    public void test(){
        Observable<String> observable=Observable.just("Hello","world","I","am","yxwang")
                .doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                Log.d("sss","doOnUnsubscribe");
            }
        });

        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d("sss","onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("sss","s");
            }
        };

        observable.subscribe(subscriber);


    }
}

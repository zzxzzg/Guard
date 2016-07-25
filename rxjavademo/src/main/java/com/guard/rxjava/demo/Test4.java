package com.guard.rxjava.demo;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

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

    public void schedulePeriodically(){
        //schedulePeriodically  直接在选中的进程中创建一个循环任务(当然也可以创建不循环任务),通过unsubscribe结束
        Subscription subscriber=Schedulers.newThread().createWorker().schedulePeriodically(new Action0() {
            @Override
            public void call() {

            }
        },1000,2000, TimeUnit.MILLISECONDS);
        subscriber.unsubscribe();

    }
}

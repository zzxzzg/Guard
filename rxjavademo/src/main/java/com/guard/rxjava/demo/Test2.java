package com.guard.rxjava.demo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by yxwang on 16/7/6.
 */
public class Test2 {

    public void test() {
        final Observable<String> observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String name=Thread.currentThread().getName();
                Log.d("sss","call thread is "+name);
                subscriber.onNext("hello");
                subscriber.onNext("  haha ");
                subscriber.onNext("I am yxwang!");
                subscriber.onCompleted();
            }
        });

        final Subscriber<String> subscriber1 = new Subscriber<String>() {

            //this method is called in current thread!!
            @Override
            public void onStart() {
                super.onStart();
                Log.d("sss", "onStart");
            }

            @Override
            public void onCompleted() {
                Log.d("sss", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("sss", "onError");
            }

            @Override
            public void onNext(String s) {
                String name=Thread.currentThread().getName();
                Log.d("sss","do data thread is "+name);
                Log.d("sss", s);

//                String t="123a3";
//                Log.d("sss",Integer.parseInt(t)+"");
            }
        };
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                Thread.currentThread().setName("subscribe_thread");
                observable1.subscribe(subscriber1);
            }
        };

        thread.start();

    }

    public void test2() {

        //Schedulers.immediate()  当前线程,并且立即执行
        //Schedulers.trampoline() 当前线程,queue队列执行
        //Schedulers.computation()  计算型线程
        //Schedulers.io() 输入输出操作线程
        //Schedulers.newThread() 新建线程
        //Schedulers.from(executor) 自己指定线程
        //AndroidSchedulers.mainThread() 主线程

        //observeOn 控制该方法之后的方法发生的线程,比如map, subscribe等,可以多次调用切换线程
        //subscribeOn 用于制定observable1生成原始数据时调用的线程,只调用一次.

        final Observable<String> observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String name=Thread.currentThread().getName();
                Log.d("sss","call thread is "+name);
                subscriber.onNext("hello");
                subscriber.onNext("  haha ");
                subscriber.onNext("I am yxwang!");
                subscriber.onCompleted();
            }
        }).observeOn(Schedulers.newThread()).subscribeOn(Schedulers.immediate());

        final Subscriber<String> subscriber1 = new Subscriber<String>() {

            //this method is called in current thread!!
            @Override
            public void onStart() {
                super.onStart();
                Log.d("sss", "onStart");
            }

            @Override
            public void onCompleted() {
                Log.d("sss", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("sss", "onError");
            }

            @Override
            public void onNext(String s) {
                String name=Thread.currentThread().getName();
                Log.d("sss","do data thread is "+name);
                Log.d("sss", s);

//                String t="123a3";
//                Log.d("sss",Integer.parseInt(t)+"");
            }
        };
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                Thread.currentThread().setName("subscribe_thread");
                observable1.subscribe(subscriber1);
            }
        };

        thread.start();

    }

    public void test3() {

        //subscribeOn doOnSubscribe 可以替代onStart方法初始化observable  但是他会在下方最近的subscribeOn定义的线程中运行.

        final Observable<String> observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String name=Thread.currentThread().getName();
                Log.d("sss","call thread is "+name);
                subscriber.onNext("hello");
                subscriber.onNext("  haha ");
                subscriber.onNext("I am yxwang!");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                String name=Thread.currentThread().getName();
                Log.d("sss","doOnSubscribe thread is "+name);
            }
        }).subscribeOn(AndroidSchedulers.mainThread());

        final Subscriber<String> subscriber1 = new Subscriber<String>() {

            //this method is called in current thread!!
            @Override
            public void onStart() {
                super.onStart();
                Log.d("sss", "onStart");
            }

            @Override
            public void onCompleted() {
                Log.d("sss", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("sss", "onError");
            }

            @Override
            public void onNext(String s) {
                String name=Thread.currentThread().getName();
                Log.d("sss","do data thread is "+name);
                Log.d("sss", s);

//                String t="123a3";
//                Log.d("sss",Integer.parseInt(t)+"");
            }
        };
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                Thread.currentThread().setName("subscribe_thread");
                observable1.subscribe(subscriber1);
            }
        };

        thread.start();

    }

    public void test4(){
        HandlerThread handlerThread=new HandlerThread("test");
        handlerThread.start();
        AndroidSchedulers.from(handlerThread.getLooper());
    }


}

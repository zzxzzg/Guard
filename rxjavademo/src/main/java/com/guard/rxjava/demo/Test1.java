package com.guard.rxjava.demo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by yxwang on 16/7/1.
 */
public class Test1 {
    public void test(){

        Observable<String> observable1= Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("  haha ");
                subscriber.onNext("I am yxwang!");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber1=new Subscriber<String>() {

            //this method is called in current thread!!
            @Override
            public void onStart() {
                super.onStart();
                Log.d("sss","onStart");
            }

            @Override
            public void onCompleted() {
                Log.d("sss","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("sss","onError");
            }

            @Override
            public void onNext(String s) {
                Log.d("sss",s);
//                String t="123a3";
//                Log.d("sss",Integer.parseInt(t)+"");
            }
        };

        observable1.subscribe(subscriber1);

        Log.d("sss",subscriber1.isUnsubscribed()+"");//true
    }

    public void test1(){

        Observable<String> observable1=Observable.just("hello","  haha ","I am yxwang!");

        Subscriber<String> subscriber1=new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                Log.d("sss",s);
            }
        };

        observable1.subscribe(subscriber1);

    }

    public void test2(){

//        List<String> strs=new ArrayList<>();
//        strs.add("hello");
//        strs.add("  haha ");
//        strs.add("I am yxwang!");

        String [] strs=new String[]{"hello","  haha ","I am yxwang!"};

        Observable<String> observable1=Observable.from(strs);

        Subscriber<String> subscriber1=new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                Log.d("sss",s);
            }
        };

        observable1.subscribe(subscriber1);

    }

    public void test3(){
        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                int i=0;
                while (i<10){
                    Log.d("sss",""+i);
                    i++;
                    Thread.sleep(1000);
                }
                return "hello world";
            }
        };
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future=executor.submit(callable);
        Observable<String> observable1=Observable.from(future);
        Subscriber<String> subscriber1=new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                Log.d("sss",s);
            }
        };
        observable1.subscribe(subscriber1);

    }

    public void test4(){
        // 0 params
        Action0 completeAction=new Action0() {
            @Override
            public void call() {

            }
        };

        Action1<Throwable> errorAction=new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };

        Action1<String> nextAction=new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("sss",s);
            }
        };

        String [] strs=new String[]{"hello","  haha ","I am yxwang!"};

        Observable<String> observable1=Observable.from(strs);

        observable1.subscribe(nextAction,errorAction,completeAction);
    }

    public void test5(){

        Observable<String> observable1= Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("  haha ");
                subscriber.onNext("I am yxwang!");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber1=new Subscriber<String>() {

            //this method is called in current thread!!
            @Override
            public void onStart() {
                super.onStart();
                Log.d("sss","onStart");
            }

            @Override
            public void onCompleted() {
                Log.d("sss","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("sss","onError");
            }

            @Override
            public void onNext(String s) {
                Log.d("sss",s);
//                String t="123a3";
//                Log.d("sss",Integer.parseInt(t)+"");
            }
        };

        observable1.filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                if(s.contains("haha")){
                    return false;
                }
                return true;
            }
        }).subscribe(subscriber1);

        Log.d("sss",subscriber1.isUnsubscribed()+"");//true
    }
}

package com.guard.rxjava.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Observable;
import rx.Subscriber;
import rx.observers.Observers;
import rx.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Test1 test=new Test1();
//        //test.test();
//
//        //test.test3();
//
//        test.test4();

//        Test2 test2=new Test2();
//        test2.test3();

//        Test3 test3=new Test3();
//        test3.test2();

        Test4 test4=new Test4();
        test4.test();
    }
}

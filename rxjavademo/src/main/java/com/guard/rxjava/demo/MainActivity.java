package com.guard.rxjava.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.observers.Observers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

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

        Test2 test2=new Test2();
        test2.test2();

//        Test3 test3=new Test3();
//        test3.test2();

    }
}

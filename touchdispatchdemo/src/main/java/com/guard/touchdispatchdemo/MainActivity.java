package com.guard.touchdispatchdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MyRelativeLayout mRelativeLayout;
    MyLinearLayout1 mLinearLayout1;
    MyLinearLayout2 mLinearlayout2;
    MyView1 mView1;
    MyView2 mView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout= (MyRelativeLayout) findViewById(R.id.relativelayout);
        mLinearLayout1= (MyLinearLayout1) findViewById(R.id.linearlayout1);
        //mLinearlayout2= (MyLinearLayout2) findViewById(R.id.linearlayout2);
        mView1= (MyView1) findViewById(R.id.myview1);
        //mView2= (MyView2) findViewById(R.id.myview2);
    }
}

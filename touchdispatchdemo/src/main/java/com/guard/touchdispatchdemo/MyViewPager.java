package com.guard.touchdispatchdemo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by yxwang on 16/10/28.
 */

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b = super.onTouchEvent(ev);
        Log.d("sss", "MyViewPager   --onTouch ----   " + Util.actionToString(ev) +"  "+b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //使用该句之后,父(所有上层)控件将不再调用onInterceptTouchEvent方法
        //getParent().requestDisallowInterceptTouchEvent(true);
        boolean b = super.dispatchTouchEvent(ev);
        Log.d("sss", "MyViewPager   --dispatchTouchEvent ----   " + Util.actionToString(ev) +"  "+b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        Log.d("sss", "MyViewPager   --onInterceptTouchEvent ----   " + Util.actionToString(ev) +"  "+b);
        return b;
    }
}

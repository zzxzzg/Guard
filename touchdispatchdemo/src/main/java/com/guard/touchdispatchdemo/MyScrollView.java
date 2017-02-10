package com.guard.touchdispatchdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by yxwang on 16/10/28.
 */

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b = super.onTouchEvent(ev);
        Log.d("sss", "MyScrollView onTouch ----   " + Util.actionToString(ev) +"  "+b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        Log.d("sss", "MyScrollView dispatchTouchEvent ----   " + Util.actionToString(ev) +"  "+b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        Log.d("sss", "MyScrollView onInterceptTouchEvent ----   " + Util.actionToString(ev) +"  "+b);
        return b;
    }
}

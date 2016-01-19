package com.guard.touchdispatchdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by yxwang on 16-1-19.
 */
public class MyLinearLayout1 extends LinearLayout {
    public MyLinearLayout1(Context context) {
        super(context);
        init();
    }

    public MyLinearLayout1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyLinearLayout1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        setTag("MyLinearLayout1");
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String name= (String) getTag();
                Log.d("sss", name + " onTouchListener----->onTouch --------------   "+Util.actionToString(event));
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String name= (String) getTag();
        Log.d("sss", name +"-----> onTouchEvent --------------   "+Util.actionToString(event));
        super.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String name= (String) getTag();
        Log.d("sss", name +"-----> dispatchTouchEvent --------------   "+Util.actionToString(ev));
        super.dispatchTouchEvent(ev);
        return  false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        String name= (String) getTag();
        Log.d("sss", name +"-----> onInterceptTouchEvent --------------   "+Util.actionToString(ev));
        super.onInterceptTouchEvent(ev);
        return false;
    }
}

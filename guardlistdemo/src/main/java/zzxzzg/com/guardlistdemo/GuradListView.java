package zzxzzg.com.guardlistdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by yxwang on 16-1-6.
 */
public class GuradListView extends ListView {

    public boolean mHideMenu=true;

    private int mTouchPosition;
    private GuardListItem mTouchView;

    public GuradListView(Context context) {
        super(context);
    }

    public GuradListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuradListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GuradListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(mHideMenu) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN && mTouchView != null) {
                int touchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
                if (mTouchPosition != touchPosition) {
                    mTouchView.smoothCloseMenu();
                }
            }
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
            View view = getChildAt(mTouchPosition - getFirstVisiblePosition());
            if (view instanceof GuardListItem) {
                mTouchView = (GuardListItem) view;
            }
        }

        if(mTouchView!=null && mTouchView.isSwipe()){
            return false;
        }

        boolean b= super.onInterceptTouchEvent(ev);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("sss", "GuardListView onTouchEvent" + ev.getAction());

        return super.onTouchEvent(ev);
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (ev.getAction() != MotionEvent.ACTION_DOWN && mTouchView == null)
//            return super.onTouchEvent(ev);
//
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
//                View view = getChildAt(mTouchPosition - getFirstVisiblePosition());
//                if (view instanceof GuardListItem) {
//                    mTouchView = (GuardListItem) view;
//                    mTouchView.onGuardTouchEvent(ev);
//                }
//                break;
////            case MotionEvent.ACTION_MOVE:
////                if(mTouchView.onGuardTouchEvent(ev)){
////                    return true;
////                }
////                break;
////            case MotionEvent.ACTION_UP:
////
////                break;
//        }
//
//        if(mTouchView.onGuardTouchEvent(ev)){
//            return true;
//        }
//        super.onTouchEvent(ev);
//
//        return false;
//    }

}

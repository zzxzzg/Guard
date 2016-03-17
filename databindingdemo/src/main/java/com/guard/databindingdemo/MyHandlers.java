package com.guard.databindingdemo;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by yxwang on 16-3-14.
 */
public class MyHandlers {
    private User mUser;

    public MyHandlers(User user){
        mUser=user;
    }

    public void onClickFriend(View view) {
        Toast.makeText(view.getContext(),"onClickFriend",Toast.LENGTH_SHORT).show();
    }
    public void onClickEnemy(View view) {
        Toast.makeText(view.getContext(),"onClickEnemy",Toast.LENGTH_SHORT).show();
    }

    public boolean onLongFriend(View view) {
        Toast.makeText(view.getContext(),"onLongFriend",Toast.LENGTH_SHORT).show();
        return true;
    }
    public boolean onLongEnemy(View view) {
        Toast.makeText(view.getContext(),"onLongClickEnemy",Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onTouchFriend(View v, MotionEvent event) {
        Toast.makeText(v.getContext(),"onTouchFriend",Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean onTouchEnemy(View v, MotionEvent event) {
        Toast.makeText(v.getContext(),"onTouchEnemy",Toast.LENGTH_SHORT).show();
        return false;
    }

}

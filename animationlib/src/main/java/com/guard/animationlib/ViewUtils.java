package com.guard.animationlib;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.guard.animationlib.ripple.Android;
import com.guard.animationlib.ripple.DrawableHotspotTouch;
import com.guard.animationlib.ripple.LollipopDrawable;
import com.guard.animationlib.ripple.LollipopDrawablesCompat;

/**
 * Created by yxwang on 15-12-16.
 */
public final class ViewUtils {
    public static void setRippleBackground(Resources res, Resources.Theme theme,View view,int id){
        if(Android.isLollipop()){
            view.setBackgroundResource(id);
        }else{
            view.setBackgroundDrawable(getDrawable2(res, id,theme));
        }
    }

    public static void setDrawableHotspotTouch(View view){
        view.setOnTouchListener(
                new DrawableHotspotTouch((LollipopDrawable) view.getBackground()));
    }

    public static Drawable getDrawable2(Resources res,int id,Resources.Theme theme){

        return LollipopDrawablesCompat.getDrawable(res, id, theme);
    }


}

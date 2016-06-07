package com.yalantis.phoenix.refresh_view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;

import com.yalantis.phoenix.PullToRefreshView;

/**
 * Created by yxwang on 16/5/24.
 */
public class BaseFrameView extends AnimationDrawable {

    private PullToRefreshView mRefreshView;
    private float mPercent;
    public BaseFrameView(AnimationDrawable aniDrawable, PullToRefreshView refreshView) {
        /* Add each frame to our animation drawable */
        for (int i = 0; i < aniDrawable.getNumberOfFrames(); i++) {
            this.addFrame(aniDrawable.getFrame(i), aniDrawable.getDuration(i));
        }
        mRefreshView=refreshView;
    }

    public void setPercent(float percent){
        mPercent=percent;
        if(mPercent>0f){
            start();
        }else{
            stop();
        }
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {

        float dragPercent = mPercent;
        if (dragPercent > 1.0f) { // Slow down if pulling over set height
            dragPercent = (dragPercent + 9.0f) / 10;
        }
        Rect rect=canvas.getClipBounds();
        int height=canvas.getHeight();
        int bmpHeight=height + rect.top*2;
        int width=canvas.getWidth();
        int dragDistance=mRefreshView.getTotalDragDistance();
        float totalHeight=mRefreshView.getTotalDragDistance()*dragPercent;

        final int saveCount = canvas.save();
        canvas.translate(0,rect.top+totalHeight-bmpHeight-50);
        canvas.clipRect(0,0,width,dragDistance);
        super.draw(canvas);
        canvas.restoreToCount(saveCount);
    }
}

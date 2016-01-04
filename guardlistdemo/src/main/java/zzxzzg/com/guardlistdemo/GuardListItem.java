package zzxzzg.com.guardlistdemo;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by apple on 16/1/2.
 */
public class GuardListItem extends RelativeLayout {

    private LinearLayout mContentLayout;
    private LinearLayout mExpandLayout;

    private int mMaxHeight=-1;
    private int mAnimY=0;
    public GuardListItem(Context context) {
        super(context);
        init();
    }

    public GuardListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuardListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GuardListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        inflate(getContext(),R.layout.gurad_list_item,this);
        mContentLayout= (LinearLayout) findViewById(R.id.content);
        mExpandLayout= (LinearLayout) findViewById(R.id.expand);
        mContentLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                expand();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //mContentLayout.measure(widthMeasureSpec, heightMeasureSpec);
        Log.d("sss", "onMeasure");
        measureChildWithMargins(mContentLayout, widthMeasureSpec, 0, heightMeasureSpec, 0);
        int contentHeight=mContentLayout.getMeasuredHeight();
        measureChildWithMargins(mExpandLayout, widthMeasureSpec, 0, heightMeasureSpec, contentHeight);
        int expandHeight=mExpandLayout.getMeasuredHeight();
        mMaxHeight=expandHeight;
        int height=mAnimY+contentHeight;

        ViewGroup.MarginLayoutParams lp = (MarginLayoutParams) mExpandLayout.getLayoutParams();
        lp.topMargin = -(mExpandLayout.getMeasuredHeight()-mAnimY);
        mExpandLayout.setLayoutParams(lp);

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));


    }

    public void expand(){
        ValueAnimator expandAnimator=ValueAnimator.ofInt(0,mMaxHeight);
        expandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int a=(Integer)animation.getAnimatedValue();
                mAnimY=a;
                ViewGroup.LayoutParams params=getLayoutParams();
                params.height=mContentLayout.getMeasuredHeight()+mAnimY;
                setLayoutParams(params);
                Log.d("sss","mAnimY="+mAnimY);
                //invalidate();
            }
        });
        expandAnimator.start();
    }

}

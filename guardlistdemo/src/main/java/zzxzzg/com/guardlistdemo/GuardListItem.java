package zzxzzg.com.guardlistdemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import zzxzzg.com.guardlistdemo.swipemenulistview.SwipeMenuLayout;

/**
 * Created by apple on 16/1/2.
 */
public class GuardListItem extends RelativeLayout {

    public static final int STATE_EXPANDED=0;
    public static final int STATE_COLLAPSED=1;
    public static final int STATE_EXPANDING=2;
    public static final int STATE_COLLAPSING=3;

    private int mCurrentState=STATE_COLLAPSED;

    private SwipeMenuLayout mContentLayout;
    private ViewGroup mExpandLayout;

    private int mMaxHeight=-1;
    private int mAnimY=0;

    private ValueAnimator mExpandAnimation;
    private ValueAnimator mCollapseAnimation;

    private OnModeChangeListener mOnModeChangeListener;

    private GestureDetectorCompat mGestureDetector;

    private boolean mIsSwipe =false;
    private boolean isFling=false;
    private boolean isScroll=false;
    GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("sss","onScroll");
            if(!isScroll){
                if(Math.abs(distanceX)*2<Math.abs(distanceY)*3){
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }
            }
            isScroll=true;
            int dis=mContentLayout.getCurrentDis();
            dis+=distanceX;
            mContentLayout.swipe(dis);
            mIsSwipe =true;
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("sss","onFling");
            isFling=true;
            if(velocityX>0){
                mContentLayout.smoothClose(velocityX);
            }else{
                mContentLayout.smoothOpen(-velocityX);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            isFling=false;
            isScroll=false;
            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("sss","onSingleTapUp");
            contentClick();
            return super.onSingleTapUp(e);
        }
    };

    public void smoothCloseMenu(){
        mContentLayout.smoothClose();
    }

//    public boolean onGuardTouchEvent(MotionEvent e){
//        Rect rect=new Rect();
//        mContentLayout.getHitRect(rect);
//        int action=e.getAction();
//        if(/*action==MotionEvent.ACTION_DOWN ==*/ rect.contains((int)e.getX(),(int)e.getY())){
//            mGestureDetector.onTouchEvent(e);
//            return true;
//        }
//        return false;
//    }

    public boolean isSwipe(){
        return mIsSwipe;
    }

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

    public ViewGroup getContentLayout(){
        return mContentLayout;
    }

    public ViewGroup getExpandLayout(){
        return mExpandLayout;
    }

    public void setState(int state){
        if(state!=STATE_EXPANDED && state != STATE_COLLAPSED){
            return;
        }
        if(mCollapseAnimation!=null && mCollapseAnimation.isRunning()){
            mCollapseAnimation.cancel();
        }
        if(mExpandAnimation!=null && mExpandAnimation.isRunning()){
            mExpandAnimation.cancel();
        }
        if(state == STATE_EXPANDED){
            mAnimY=-1;
        }else{
            mAnimY=0;
        }
        innerSetState(state);
        requestLayout();
    }

    private void innerSetState(int state){
        mCurrentState=state;
        if(mOnModeChangeListener!=null){
            mOnModeChangeListener.onModeChange(state);
        }
    }

    private void init(){
        inflate(getContext(),R.layout.gurad_list_item,this);
        mContentLayout= (SwipeMenuLayout) findViewById(R.id.item_content);
        mExpandLayout= (ViewGroup) findViewById(R.id.expand);
        mGestureDetector = new GestureDetectorCompat(getContext(),
                mGestureListener);

        mContentLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.d("sss","onTouch ACTION_UP");
                    if(!isFling) {
                        mContentLayout.onFling();
                    }
                    mIsSwipe=false;
                }
                return true;
            }
        });

        mExpandAnimation=new ValueAnimator();
        mExpandAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int a=(Integer)animation.getAnimatedValue();
                mAnimY=a;
//                ViewGroup.LayoutParams params=getLayoutParams();
//                params.height=mContentLayout.getMeasuredHeight()+mAnimY;
//                setLayoutParams(params);
                requestLayout();
            }
        });
        mExpandAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                innerSetState(STATE_EXPANDING);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                innerSetState(STATE_EXPANDED);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        mCollapseAnimation=new ValueAnimator();
        mCollapseAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int a = (Integer) animation.getAnimatedValue();
                mAnimY = a;
//                ViewGroup.LayoutParams params = getLayoutParams();
//                params.height = mContentLayout.getMeasuredHeight() + mAnimY;
//                setLayoutParams(params);
                requestLayout();
            }
        });
        mCollapseAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                innerSetState(STATE_COLLAPSING);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                innerSetState(STATE_COLLAPSED);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void contentClick(){
        if(mCurrentState==STATE_EXPANDING){
            mExpandAnimation.cancel();
            collapse();
        }else if(mCurrentState==STATE_COLLAPSING){
            mCollapseAnimation.cancel();
            expand();
        }else if(mCurrentState==STATE_COLLAPSED){
            expand();
        }else if(mCurrentState==STATE_EXPANDED){
            collapse();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildWithMargins(mContentLayout, widthMeasureSpec, 0, heightMeasureSpec, 0);
        int contentHeight=mContentLayout.getMeasuredHeight();
        measureChildWithMargins(mExpandLayout, widthMeasureSpec, 0, heightMeasureSpec, contentHeight);
        int expandHeight=mExpandLayout.getMeasuredHeight();
        mMaxHeight=expandHeight;
        if(mAnimY ==-1){
            mAnimY=mMaxHeight;
        }
        int height=mAnimY+contentHeight;

        ViewGroup.MarginLayoutParams lp = (MarginLayoutParams) mExpandLayout.getLayoutParams();
        lp.topMargin = -(mExpandLayout.getMeasuredHeight()-mAnimY);
        mExpandLayout.setLayoutParams(lp);

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

    }

    public void expand(){
        mExpandAnimation.setIntValues(mAnimY, mMaxHeight);
        mExpandAnimation.start();
    }

    public void collapse(){
        mCollapseAnimation.setIntValues(mAnimY,0);
        mCollapseAnimation.start();
    }

    public OnModeChangeListener getOnModeChangeListener() {
        return mOnModeChangeListener;
    }

    public void setOnModeChangeListener(OnModeChangeListener mOnModeChangeListener) {
        this.mOnModeChangeListener = mOnModeChangeListener;
    }

    public interface OnModeChangeListener{
        public void onModeChange(int mode);
    }

}

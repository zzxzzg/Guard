package zzxzzg.com.guardlistdemo.swipemenulistview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import zzxzzg.com.guardlistdemo.R;

/**
 * 
 * @author baoyz
 * @date 2014-8-23
 * 
 */
public class SwipeMenuLayout extends FrameLayout {

	private static final int CONTENT_VIEW_ID = 1;
	private static final int MENU_VIEW_ID = 2;

	private static final int STATE_CLOSE = 0;
	private static final int STATE_CLOSING=2;
	private static final int STATE_OPEN = 1;
	private static final int STATE_OPENING=3;

	private View mContentView;
	private SwipeMenuView mMenuView;
	private int state = STATE_CLOSE;

	private ValueAnimator mOpenAnimator;
	private ValueAnimator mCloseAnimator;

	private int MAX_ANIMATION_TIME=300;


	private int mCurrentDis=0;

	public int getCurrentDis(){
		return mCurrentDis;
	}

	public SwipeMenuLayout(Context context) {
		super(context);
		init();
	}

	public SwipeMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {

		ViewGroup v= (ViewGroup) inflate(getContext(), R.layout.swipe_layout, null);
		mContentView=v.findViewById(R.id.swipe_content);
		mMenuView= (SwipeMenuView) v.findViewById(R.id.swipe_menu);
		v.removeAllViews();
		addView(mMenuView);
		addView(mContentView);

		SwipeMenu menu = new SwipeMenu(getContext());
//		menu.setViewType(mAdapter.getItemViewType(position));
		SwipeMenuItem item = new SwipeMenuItem(getContext());
		item.setTitle("Item 1");
		item.setBackground(new ColorDrawable(Color.RED));
		item.setWidth(300);
		menu.addMenuItem(item);

		item = new SwipeMenuItem(getContext());
		item.setTitle("Item 2");
		item.setBackground(new ColorDrawable(Color.GREEN));
		item.setWidth(300);
		menu.addMenuItem(item);
		mMenuView.setMenu(menu);
		mMenuView.setOnSwipeItemClickListener(null);

	}

	public void onFling(){
		if(mCurrentDis==0 || mCurrentDis==mMenuView.getMeasuredWidth()){
			return;
		}
		if(mCurrentDis<mMenuView.getMeasuredWidth()/2){
			// close
			smoothClose();
		}else{
			// open
			smoothOpen();
		}
	}

	public void smoothOpen(float velocityX){
		if(mCloseAnimator!=null && mCloseAnimator.isRunning()){
			mCloseAnimator.cancel();
		}

		if(mOpenAnimator==null){
			mOpenAnimator=new ValueAnimator();
			mOpenAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					swipe((Integer) animation.getAnimatedValue());
				}
			});
		}

		mOpenAnimator.setIntValues(mCurrentDis,mMenuView.getMeasuredWidth());
		int time = (int) ((mMenuView.getMeasuredWidth()-mCurrentDis)*1.0f/velocityX*1000);
		mOpenAnimator.setDuration(time);
		mOpenAnimator.start();
	}

	public void smoothOpen(){
		float velocityX=mMenuView.getMeasuredWidth()*1.0f/MAX_ANIMATION_TIME*1000;
		smoothOpen(velocityX);
	}

	public void smoothClose(float velocityX){
		if(mOpenAnimator!=null && mOpenAnimator.isRunning()){
			mOpenAnimator.cancel();
		}

		if(mCloseAnimator==null){
			mCloseAnimator=new ValueAnimator();
			mCloseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					swipe((Integer) animation.getAnimatedValue());
				}
			});
		}

		mCloseAnimator.setIntValues(mCurrentDis, 0);
		int time= (int) (mCurrentDis*1.0f/velocityX*1000);
		mCloseAnimator.setDuration(time);
		mCloseAnimator.start();
	}

	public void smoothClose(){
		float velocityX=mMenuView.getMeasuredWidth()*1.0f/MAX_ANIMATION_TIME*1000;
		smoothClose(velocityX);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}


	public boolean isOpen() {
		return state == STATE_OPEN || state ==  STATE_OPENING;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void swipe(int dis) {
		if (dis > mMenuView.getMeasuredWidth()) {
			dis = mMenuView.getMeasuredWidth();
		}
		if (dis < 0) {
			dis = 0;
		}
		mCurrentDis=dis;
		requestLayout();
	}

	public View getContentView() {
		return mContentView;
	}

	public SwipeMenuView getMenuView() {
		return mMenuView;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mContentView.layout(-mCurrentDis, 0,
				mContentView.getMeasuredWidth()-mCurrentDis , mContentView.getMeasuredHeight());
		mMenuView.layout(mContentView.getWidth() - mCurrentDis, 0,
				mContentView.getMeasuredWidth() + mMenuView.getMeasuredWidth() - mCurrentDis,
				mMenuView.getMeasuredHeight());
	}

}

package com.timehop.stickyheadersrecyclerview.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

/**
 * Created by yuzekun on 16/4/15.
 */
public class StickyDivider extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerColor;

    private int mOrientation;
    private StickyRecyclerHeadersAdapter mStickyRecyclerHeadersAdapter;

    public StickyDivider(Context context, StickyRecyclerHeadersAdapter adapter, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mStickyRecyclerHeadersAdapter=adapter;
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public StickyDivider(Context context, StickyRecyclerHeadersAdapter adapter, int orientation, int res) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = context.getResources().getDrawable(res);
        mStickyRecyclerHeadersAdapter=adapter;
        a.recycle();
        setOrientation(orientation);
    }

    public StickyDivider(Context context, StickyRecyclerHeadersAdapter adapter, int orientation, int height, int color) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mStickyRecyclerHeadersAdapter=adapter;
        mDivider = null;
        mDividerHeight=height;
        mDividerColor=color;
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        Paint paint=new Paint();
        paint.setColor(mDividerColor);
        paint.setStrokeWidth(mDividerHeight);
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            int pos=parent.getChildAdapterPosition(child);
            if(pos==mStickyRecyclerHeadersAdapter.getItemCount()-1){
                continue;
            }
            long cHeadId=mStickyRecyclerHeadersAdapter.getHeaderId(pos);
            long nextHeadId=mStickyRecyclerHeadersAdapter.getHeaderId(pos+1);
            if(cHeadId!=nextHeadId){
                continue;
            }


            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            if(mDivider!=null) {
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }else{
                final int bottom = top + mDividerHeight;
                c.drawLine(left,top,right,bottom,paint);
            }
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        Paint paint=new Paint();
        paint.setColor(mDividerColor);
        paint.setStrokeWidth(mDividerHeight);
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            int pos=parent.getChildAdapterPosition(child);
            if(pos==mStickyRecyclerHeadersAdapter.getItemCount()-1){
                continue;
            }
            long cHeadId=mStickyRecyclerHeadersAdapter.getHeaderId(pos);
            long nextHeadId=mStickyRecyclerHeadersAdapter.getHeaderId(pos+1);
            if(cHeadId!=nextHeadId){
                continue;
            }

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            if(mDivider!=null) {
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }else{
                final int right = left + mDividerHeight;
                c.drawLine(left,top,right,bottom,paint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {

        if(itemPosition==mStickyRecyclerHeadersAdapter.getItemCount()-1){
            outRect.set(0, 0, 0, 0);
            return;
        }
        long cHeadId=mStickyRecyclerHeadersAdapter.getHeaderId(itemPosition);
        long nextHeadId=mStickyRecyclerHeadersAdapter.getHeaderId(itemPosition+1);
        if(cHeadId!=nextHeadId){
            outRect.set(0, 0, 0, 0);
            return;
        }

        if (mOrientation == VERTICAL_LIST) {
            if(mDivider!=null) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }else{
                outRect.set(0, 0, 0, mDividerHeight);
            }
        } else {
            if(mDivider!=null) {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }else{
                outRect.set(0, 0, mDividerHeight, 0);
            }
        }
    }
}

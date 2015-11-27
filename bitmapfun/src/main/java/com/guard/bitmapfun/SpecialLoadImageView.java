package com.guard.bitmapfun;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by apple on 15/8/26.
 */
public class SpecialLoadImageView extends ImageView {

    private Drawable mDrawable;

    private OnLoadFinshListener mOnLoadFinshListener;

    public SpecialLoadImageView(Context context) {
        super(context);
    }

    public SpecialLoadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpecialLoadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    public void setImageDrawable(Drawable drawable) {
//        super.setImageDrawable(drawable);
//        if(mDrawable!=null&& mDrawable instanceof BitmapDrawable){
//            ((BitmapDrawable) mDrawable).getBitmap().recycle();
//        }
//        mDrawable=drawable;
//
//    }

    public OnLoadFinshListener getOnLoadFinshListener() {
        return mOnLoadFinshListener;
    }

    public void setOnLoadFinshListener(OnLoadFinshListener onLoadFinshListener) {
        mOnLoadFinshListener = onLoadFinshListener;
    }

    public interface OnLoadFinshListener{
        public void onLoadFinish();
    }

}

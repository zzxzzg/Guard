package com.guard.touchdispatchdemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by qianf on 16/7/28.
 */
public class HomeGalleryPagerAdapter extends PagerAdapter {
    private int  mCount;
    private Context mContext;

    int []colors = {0xaaff00af,0xaa12fe34,0xaa556568,0xaa9fae28,0xaae5e5e5};

    public HomeGalleryPagerAdapter(Context context) {
        mContext = context;
    }

    public void setData(int count) {
        mCount = count;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mCount;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setLayoutParams(new ViewPager.LayoutParams());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        container.addView(imageView);
        int  a =position%colors.length;
        imageView.setBackgroundColor(colors[a]);

        return imageView;
    }
}

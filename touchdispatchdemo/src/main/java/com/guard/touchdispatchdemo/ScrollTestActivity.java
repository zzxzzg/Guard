package com.guard.touchdispatchdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by yxwang on 16/10/28.
 */

public class ScrollTestActivity extends Activity {

    private MyViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolltest);
        mViewPager = (MyViewPager) findViewById(R.id.viewpager);
        HomeGalleryPagerAdapter adapter = new HomeGalleryPagerAdapter(this);
        adapter.setData(10);
        mViewPager.setAdapter(adapter);
    }

}

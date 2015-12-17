package com.guard.ainimationdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.guard.animationlib.TransitionUtil;

/**
 * Created by yxwang on 15-12-11.
 */
public class CrossfadingActivity extends Activity {
    private View mContentView;
    private View mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crossfading_activity);

        mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading_spinner);

        // Initially hide the content view.
        mContentView.setVisibility(View.GONE);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionUtil.CrossFade(CrossfadingActivity.this,mLoadingView,mContentView);
            }
        });

        mLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionUtil.CrossFade(CrossfadingActivity.this, mContentView,mLoadingView);
            }
        });

    }

}

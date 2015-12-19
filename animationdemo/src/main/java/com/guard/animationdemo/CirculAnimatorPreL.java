package com.guard.animationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.guard.animationdemo.R;
import com.guard.animationlib.circul.SupportAnimator;
import com.guard.animationlib.circul.ViewAnimationUtils;

/**
 * Created by yxwang on 15-12-15.
 */
public class CirculAnimatorPreL extends Activity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circul_pre_l_activity);
        imageView= (ImageView) findViewById(R.id.imageview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View myView) {
                int cx = (myView.getLeft() + myView.getRight()) / 2;
                int cy = (myView.getTop() + myView.getBottom()) / 2;

                // get the final radius for the clipping circle
                int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

                SupportAnimator animator =
                        ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(1500);
                animator.start();
            }
        });
    }
}

package com.guard.animationlib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;

/**
 * Created by yxwang on 15-12-14.
 */
public final class TransitionUtil {
    /**
     *
     * @param frontView  the view will be shown
     * @param backView  the view will be hide
     */
    public static void CrossFade(Context context,View frontView,final View backView){
        int shortAnimationDuration = context.getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        frontView.setAlpha(0f);
        frontView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        frontView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        backView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        backView.setVisibility(View.GONE);
                    }
                });
    }
}

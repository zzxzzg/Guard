package com.guard.animationlib.list.effects;

import android.view.View;
import android.view.ViewPropertyAnimator;

import com.guard.animationlib.list.JazzyEffect;

public class SlideHorizontal  implements JazzyEffect {

    @Override
    public void initView(View item, int position, int scrollDirection) {
        item.setTranslationX(item.getWidth()  * scrollDirection);
    }

    @Override
    public void setupAnimation(View item, int position, int scrollDirection, ViewPropertyAnimator animator) {
        animator.translationXBy(-item.getWidth()  * scrollDirection);
    }
}
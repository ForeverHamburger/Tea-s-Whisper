package com.xuptggg.forum.publish.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class AnimationUtils {

    public static ObjectAnimator fadeIn(View view, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animator.setDuration(duration);
        return animator;
    }

    public static ObjectAnimator fadeOut(View view, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animator.setDuration(duration);
        return animator;
    }

    public static AnimatorSet scaleIn(View view, long duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.setDuration(duration);
        return set;
    }

    public static AnimatorSet scaleOut(View view, long duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.setDuration(duration);
        return set;
    }
}
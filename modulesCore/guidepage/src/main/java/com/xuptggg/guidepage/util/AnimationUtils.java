package com.xuptggg.guidepage.util;

import android.animation.ValueAnimator;
import android.view.View;

public class AnimationUtils {
    public static void FadeOutAnimation (View view){
        ValueAnimator fadeOutAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        fadeOutAnimator.setDuration(1000); // 持续时间1000毫秒
        fadeOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                view.setAlpha(alpha);
            }
        });
        fadeOutAnimator.start();
    }

    public static void FadeInAnimation (View view){
        ValueAnimator fadeInAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        fadeInAnimator.setDuration(1000); // 持续时间1000毫秒
        fadeInAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                view.setAlpha(alpha);
            }
        });
        fadeInAnimator.start();
    }
}

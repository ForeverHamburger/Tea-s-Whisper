package com.xuptggg.detail.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.animation.LinearInterpolator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xuptggg.detail.R;

public class AnimationUtils {

    public static void performLikeAnimation(Context context, FloatingActionButton fab) {
        // 缩放动画
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(fab, "scaleX", 1f, 0.8f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(fab, "scaleY", 1f, 0.8f);
        scaleDownX.setDuration(80);
        scaleDownY.setDuration(80);
        scaleDownX.setInterpolator(new LinearInterpolator());
        scaleDownY.setInterpolator(new LinearInterpolator());

        // 颜色动画
        ValueAnimator colorAnimator = ValueAnimator.ofArgb(
                context.getResources().getColor(android.R.color.white),
                context.getResources().getColor(R.color.dark_green)
        );
        colorAnimator.setDuration(80);
        colorAnimator.addUpdateListener(animator -> {
            int color = (int) animator.getAnimatedValue();
        });

        // 弹性放大动画
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(fab, "scaleX", 0.8f, 1.2f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(fab, "scaleY", 0.8f, 1.2f);
        scaleUpX.setDuration(150);
        scaleUpY.setDuration(150);
        scaleUpX.setInterpolator(new LinearInterpolator());
        scaleUpY.setInterpolator(new LinearInterpolator());

        // 恢复正常大小动画
        ObjectAnimator backToNormalX = ObjectAnimator.ofFloat(fab, "scaleX", 1.2f, 1f);
        ObjectAnimator backToNormalY = ObjectAnimator.ofFloat(fab, "scaleY", 1.2f, 1f);
        backToNormalX.setDuration(80);
        backToNormalY.setDuration(80);
        backToNormalX.setInterpolator(new LinearInterpolator());
        backToNormalY.setInterpolator(new LinearInterpolator());

        // 组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(scaleDownX, scaleDownY, colorAnimator, scaleUpX, scaleUpY, backToNormalX, backToNormalY);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                fab.setImageResource(R.drawable.ic_like);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束后的操作
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // 动画取消后的操作
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // 动画重复后的操作
            }
        });
        animatorSet.start();
    }

    public static void performUnlikeAnimation(Context context, FloatingActionButton fab) {
        // 缩放动画
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(fab, "scaleX", 1f, 0.8f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(fab, "scaleY", 1f, 0.8f);
        scaleDownX.setDuration(80);
        scaleDownY.setDuration(80);
        scaleDownX.setInterpolator(new LinearInterpolator());
        scaleDownY.setInterpolator(new LinearInterpolator());

        // 颜色动画
        ValueAnimator colorAnimator = ValueAnimator.ofArgb(
                context.getResources().getColor(R.color.dark_green),
                context.getResources().getColor(android.R.color.white)
        );
        colorAnimator.setDuration(80);
        colorAnimator.addUpdateListener(animator -> {
            int color = (int) animator.getAnimatedValue();
        });

        // 弹性放大动画
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(fab, "scaleX", 0.8f, 1.2f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(fab, "scaleY", 0.8f, 1.2f);
        scaleUpX.setDuration(150);
        scaleUpY.setDuration(150);
        scaleUpX.setInterpolator(new LinearInterpolator());
        scaleUpY.setInterpolator(new LinearInterpolator());

        // 恢复正常大小动画
        ObjectAnimator backToNormalX = ObjectAnimator.ofFloat(fab, "scaleX", 1.2f, 1f);
        ObjectAnimator backToNormalY = ObjectAnimator.ofFloat(fab, "scaleY", 1.2f, 1f);
        backToNormalX.setDuration(80);
        backToNormalY.setDuration(80);
        backToNormalX.setInterpolator(new LinearInterpolator());
        backToNormalY.setInterpolator(new LinearInterpolator());

        // 组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(scaleDownX, scaleDownY, colorAnimator, scaleUpX, scaleUpY, backToNormalX, backToNormalY);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                fab.setImageResource(R.drawable.ic_like_select);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束后的操作
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // 动画取消后的操作
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // 动画重复后的操作
            }
        });
        animatorSet.start();
    }
}
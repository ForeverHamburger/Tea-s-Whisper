package com.example.module.chat.communicate.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
// AnimUtil.java
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;

/**
 * 动画工具类，提供 ImageView 的展开和收回动画。
 * 支持 Android 5.0 (LOLLIPOP) 及以上版本的圆形揭示动画，
 * 以及旧版本的缩放和透明度组合动画。
 */
public class AnimUtil {
    // 动画默认持续时间（300毫秒）
    private static final int ANIM_DURATION = 300;

    /**
     * 动画结束监听接口，用于在动画完成后执行回调逻辑。
     */
    public interface AnimationEndListener {
        void onAnimationEnd();
    }

    /**
     * 启动展开动画：从触发视图的中心点展开显示目标 ImageView。
     *
     * @param targetView   要执行动画的目标 ImageView
     * @param triggerView  触发动画的视图（用于确定动画起点）
     * @param newImageRes  目标 ImageView 要设置的图片资源 ID
     */
    public static void startImageTransition(ImageView targetView,
                                            View triggerView,
                                            @DrawableRes int newImageRes) {
        // 获取触发视图在屏幕上的中心坐标
        int[] location = new int[2];
        triggerView.getLocationOnScreen(location);
        final int centerX = location[0] + triggerView.getWidth() / 2;
        final int centerY = location[1] + triggerView.getHeight() / 2;

        // 设置目标视图的新图片
        targetView.setImageResource(newImageRes);

        // 根据系统版本选择动画方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Lollipop+ 使用圆形揭示动画
            createRevealAnimator(targetView, centerX, centerY, true).start();
        } else {
            // 旧版本使用组合缩放动画
            createLegacyShowAnim(targetView, centerX, centerY).start();
        }
    }

    /**
     * 启动收回动画：从触发视图的中心点收缩隐藏目标 ImageView。
     *
     * @param targetView   要执行动画的目标 ImageView
     * @param triggerView  触发动画的视图（用于确定动画起点）
     * @param listener     动画结束监听器（可为 null）
     */
    public static void reverseImageTransition(ImageView targetView,
                                              View triggerView,
                                              AnimationEndListener listener) {
        // 获取触发视图在屏幕上的中心坐标
        int[] location = new int[2];
        triggerView.getLocationOnScreen(location);
        final int centerX = location[0] + triggerView.getWidth() / 2;
        final int centerY = location[1] + triggerView.getHeight() / 2;

        // 根据系统版本选择动画方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            handleLollipopReverse(targetView, centerX, centerY, listener);
        } else {
            handleLegacyReverse(targetView, centerX, centerY, listener);
        }
    }

    /**
     * 处理 Lollipop 及以上版本的收回动画（反向圆形揭示）
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void handleLollipopReverse(ImageView targetView,
                                              int centerX, int centerY,
                                              AnimationEndListener listener) {
        // 计算初始半径（视图对角线长度）
        float initialRadius = (float) Math.hypot(targetView.getWidth(), targetView.getHeight());

        // 创建圆形揭示动画（从满屏收缩到 0）
        Animator anim = ViewAnimationUtils.createCircularReveal(
                targetView, centerX, centerY, initialRadius, 0
        );
        anim.setDuration(ANIM_DURATION);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());

        // 动画结束监听
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                targetView.setVisibility(View.INVISIBLE);
                if (listener != null) listener.onAnimationEnd();
            }
        });
        anim.start();
    }

    /**
     * 处理旧版本收回动画（缩放 + 透明度组合动画）
     */
    private static void handleLegacyReverse(ImageView targetView,
                                            int centerX, int centerY,
                                            AnimationEndListener listener) {
        // 确保视图已完成布局
        targetView.post(() -> {
            if (targetView.getWidth() == 0 || targetView.getHeight() == 0) return;

            // 将屏幕坐标转换为目标视图的相对坐标
            int[] targetLocation = new int[2];
            targetView.getLocationOnScreen(targetLocation);
            float pivotX = (centerX - targetLocation[0]) / (float) targetView.getWidth();
            float pivotY = (centerY - targetLocation[1]) / (float) targetView.getHeight();

            // 创建缩放动画（从 1 缩小到 0，围绕计算出的中心点）
            ScaleAnimation scaleAnim = new ScaleAnimation(
                    1, 0, 1, 0,
                    Animation.RELATIVE_TO_SELF, pivotX,
                    Animation.RELATIVE_TO_SELF, pivotY
            );
            scaleAnim.setDuration(ANIM_DURATION);

            // 创建透明度动画（从 1 到 0）
            AlphaAnimation alphaAnim = new AlphaAnimation(1, 0);
            alphaAnim.setDuration(ANIM_DURATION);

            // 组合动画
            AnimationSet set = new AnimationSet(true);
            set.addAnimation(scaleAnim);
            set.addAnimation(alphaAnim);
            set.setFillEnabled(true);

            // 动画监听
            set.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    targetView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    targetView.setVisibility(View.INVISIBLE);
                    if (listener != null) listener.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            targetView.clearAnimation();
            targetView.startAnimation(set);
        });
    }

    /**
     * 创建圆形揭示动画（Lollipop+）
     *
     * @param isOpen true: 展开动画，false: 收回动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Animator createRevealAnimator(ImageView targetView,
                                                 int centerX, int centerY,
                                                 boolean isOpen) {
        float finalRadius = (float) Math.hypot(targetView.getWidth(), targetView.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(
                targetView,
                centerX,
                centerY,
                isOpen ? 0 : finalRadius,
                isOpen ? finalRadius : 0
        );
        anim.setDuration(ANIM_DURATION);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        targetView.setVisibility(isOpen ? View.VISIBLE : View.INVISIBLE);
        return anim;
    }

    /**
     * 创建旧版本展开动画（缩放 + 透明度）
     */
    private static Animation createLegacyShowAnim(ImageView targetView,
                                                  int centerX, int centerY) {
        targetView.post(() -> {
            // 计算相对中心点
            int[] targetLocation = new int[2];
            targetView.getLocationOnScreen(targetLocation);
            float pivotX = (centerX - targetLocation[0]) / (float) targetView.getWidth();
            float pivotY = (centerY - targetLocation[1]) / (float) targetView.getHeight();

            // 组合动画：从 0 放大到 1，透明度从 0 到 1
            AnimationSet set = new AnimationSet(true);
            set.addAnimation(new ScaleAnimation(0, 1, 0, 1,
                    Animation.RELATIVE_TO_SELF, pivotX,
                    Animation.RELATIVE_TO_SELF, pivotY));
            set.addAnimation(new AlphaAnimation(0, 1));
            set.setDuration(ANIM_DURATION);
            set.setFillAfter(true);
            targetView.startAnimation(set);
        });
        targetView.setVisibility(View.VISIBLE);
        return targetView.getAnimation();
    }

    /**
     * 创建旧版本隐藏动画（已弃用，功能整合至 handleLegacyReverse）
     */
    @Deprecated
    private static Animation createLegacyHideAnim(ImageView targetView,
                                                  int centerX, int centerY) {
        targetView.post(() -> {
            int[] targetLocation = new int[2];
            targetView.getLocationOnScreen(targetLocation);
            float pivotX = (centerX - targetLocation[0]) / (float) targetView.getWidth();
            float pivotY = (centerY - targetLocation[1]) / (float) targetView.getHeight();

            AnimationSet set = new AnimationSet(true);
            set.addAnimation(new ScaleAnimation(1, 0, 1, 0,
                    Animation.RELATIVE_TO_SELF, pivotX,
                    Animation.RELATIVE_TO_SELF, pivotY));
            set.addAnimation(new AlphaAnimation(1, 0));
            set.setDuration(ANIM_DURATION);
            set.setFillAfter(true);
            targetView.startAnimation(set);
        });
        return targetView.getAnimation();
    }
}
//public class AnimUtil {
//    private static final int ANIM_DURATION = 600;
//
//    /**
//     * 启动图片切换动画
//     * @param targetView 目标ImageView
//     * @param triggerView 触发按钮
//     * @param newImageRes 新图片资源
//     */
//    public static void startImageTransition(ImageView targetView,
//                                            View triggerView,
//                                            @DrawableRes int newImageRes) {
//        // 计算触发点坐标
//        int[] location = new int[2];
//        triggerView.getLocationOnScreen(location);
//        final int centerX = location[0] + triggerView.getWidth()/2;
//        final int centerY = location[1] + triggerView.getHeight()/2;
//
//        // 设置新图片
//        targetView.setImageResource(newImageRes);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            // 圆形揭露动画
//            float finalRadius = (float) Math.hypot(
//                    targetView.getWidth(),
//                    targetView.getHeight()
//            );
//
//            Animator anim = ViewAnimationUtils.createCircularReveal(
//                    targetView, centerX, centerY, 0, finalRadius
//            );
//            anim.setDuration(ANIM_DURATION);
//            anim.setInterpolator(new FastOutSlowInInterpolator());
//            targetView.setVisibility(View.VISIBLE);
//            anim.start();
//        } else {
//            targetView.setVisibility(View.VISIBLE);
//            targetView.post(() -> {
//                int[] targetLocation = new int[2];
//                targetView.getLocationOnScreen(targetLocation);
//
//                float pivotX = (centerX - targetLocation[0]) / (float) targetView.getWidth();
//                float pivotY = (centerY - targetLocation[1]) / (float) targetView.getHeight();
//
//                ScaleAnimation scaleAnim = new ScaleAnimation(
//                        0, 1, 0, 1,
//                        Animation.RELATIVE_TO_SELF, pivotX,
//                        Animation.RELATIVE_TO_SELF, pivotY
//                );
//            // 缩放渐变动画
////            ScaleAnimation scaleAnim = new ScaleAnimation(
////                    0, 1, 0, 1,
////                    Animation.RELATIVE_TO_SELF, 0.5f,
////                    Animation.RELATIVE_TO_SELF, 0.5f
////            );
//            scaleAnim.setDuration(ANIM_DURATION);
//
//            AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
//            alphaAnim.setDuration(ANIM_DURATION);
//
//            AnimationSet set = new AnimationSet(true);
//            set.addAnimation(scaleAnim);
//            set.addAnimation(alphaAnim);
//            targetView.startAnimation(set);
//            targetView.setVisibility(View.VISIBLE);
//            });
//        }
//    }
//    /**
//     * 启动图片收回动画
//     * @param targetView 目标ImageView
//     * @param triggerView 触发按钮
//     */
//    public static void reverseImageTransition(ImageView targetView, View triggerView) {
//        int[] location = new int[2];
//        triggerView.getLocationOnScreen(location);
//        final int centerX = location[0] + triggerView.getWidth() / 2;
//        final int centerY = location[1] + triggerView.getHeight() / 2;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            // 逆向圆形揭露动画
//            float initialRadius = (float) Math.hypot(
//                    targetView.getWidth(),
//                    targetView.getHeight()
//            );
//
//            Animator anim = ViewAnimationUtils.createCircularReveal(
//                    targetView, centerX, centerY, initialRadius, 0
//            );
//            anim.setDuration(ANIM_DURATION);
//            anim.setInterpolator(new FastOutSlowInInterpolator());
//            anim.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    targetView.setVisibility(View.INVISIBLE);
//                }
//            });
//            anim.start();
//        } else {
//            // 逆向缩放渐变动画
//            targetView.post(() -> {
//                int[] targetLocation = new int[2];
//                targetView.getLocationOnScreen(targetLocation);
//
//                float pivotX = (centerX - targetLocation[0]) / (float) targetView.getWidth();
//                float pivotY = (centerY - targetLocation[1]) / (float) targetView.getHeight();
//
//                ScaleAnimation scaleAnim = new ScaleAnimation(
//                        1, 0, 1, 0,
//                        Animation.RELATIVE_TO_SELF, pivotX,
//                        Animation.RELATIVE_TO_SELF, pivotY
//                );
//                scaleAnim.setDuration(ANIM_DURATION);
//
//                AlphaAnimation alphaAnim = new AlphaAnimation(1, 0);
//                alphaAnim.setDuration(ANIM_DURATION);
//
//                AnimationSet set = new AnimationSet(true);
//                set.addAnimation(scaleAnim);
//                set.addAnimation(alphaAnim);
//                set.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {}
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        targetView.setVisibility(View.INVISIBLE);
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {}
//                });
//                targetView.startAnimation(set);
//            });
//        }
//    }
//}
package com.xuptggg.detection.card;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.xuptggg.detection.R;

public class FlippableDialog extends Dialog {
    private ConstraintLayout dialogLayout;
    private ObjectAnimator currentAnimator;
    private boolean isAnimating = false;

    public FlippableDialog(Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_flippable);

        dialogLayout = findViewById(R.id.dialog_container);

        // 对话框显示时开始旋转三圈
        setOnShowListener(dialog -> {
            rotateThreeTimes();
        });

        // 设置点击监听器
        dialogLayout.setOnClickListener(v -> {
            if (isAnimating) {
                stopRotationSmoothly();
            }
        });

        // 设置长按监听器
        dialogLayout.setOnLongClickListener(v -> {
            rotateHundredTimes();
            return true;
        });
    }

    private void rotateThreeTimes() {
        currentAnimator = ObjectAnimator.ofFloat(dialogLayout, "rotationY", 0f, 1080f);
        setupAnimator(currentAnimator, 1500);
        currentAnimator.start();
    }

    private void rotateHundredTimes() {
        currentAnimator = ObjectAnimator.ofFloat(dialogLayout, "rotationY", 0f, 36000f);
        setupAnimator(currentAnimator, 15000);
        currentAnimator.start();
    }

    private void setupAnimator(ObjectAnimator animator, long duration) {
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });
    }

    private void stopRotationSmoothly() {
        if (currentAnimator != null && currentAnimator.isRunning()) {
            float currentRotation = dialogLayout.getRotationY();
            currentAnimator.cancel();
            ObjectAnimator stopAnimator = ObjectAnimator.ofFloat(dialogLayout, "rotationY", currentRotation, (float) (Math.round(currentRotation / 360) * 360));
            stopAnimator.setDuration(1000);
            stopAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            stopAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimating = false;
                }
            });
            stopAnimator.start();
        }
    }
}    
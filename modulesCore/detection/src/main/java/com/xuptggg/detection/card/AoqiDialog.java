package com.xuptggg.detection.card;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.xuptggg.detection.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AoqiDialog extends Dialog {
    private ConstraintLayout dialogLayout;
    private ImageView aoqi;
    private ObjectAnimator currentAnimator;
    private boolean isAnimating = false;

    public AoqiDialog(Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_aoqi);
        aoqi = findViewById(R.id.iv_aoqi);
        dialogLayout = findViewById(R.id.dialog_container_aoqi);

        List<Integer> imagePaths = new ArrayList<>();
        imagePaths.add(R.drawable.img_1);
        imagePaths.add(R.drawable.pic_aoqi1);

        Integer image = loadRandomImage(imagePaths);
        aoqi.setImageResource(image);

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

    public static Integer loadRandomImage(List<Integer> imageList) {
        // 创建随机数生成器
        Random random = new Random();
        // 随机选择一个图片索引
        int randomIndex = random.nextInt(imageList.size());
        Integer randomImagePath = imageList.get(randomIndex);
        return randomImagePath;
    }
}    
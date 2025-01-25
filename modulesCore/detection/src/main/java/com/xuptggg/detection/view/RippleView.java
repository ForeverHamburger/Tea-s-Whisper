package com.xuptggg.detection.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RippleView extends View {

    private static final int NUM_RIPPLES = 3; // 三个圆形
    private static final int DURATION = 2000; // 动画持续时间
    private static final float MAX_SIZE = 700f; // 最大尺寸
    private static final float MIN_SIZE = 250f; // 最小尺寸
    private static final float CENTER_MIN_SIZE = 250f; // 中心圆形最小尺寸
    private static final float CENTER_MAX_SIZE = 300f; // 中心圆形最大尺寸
    private static final int CENTER_ANIMATION_DURATION = 1000; // 中心圆形动画持续时间
    private Paint[] paints;
    private float[] sizes;
    private float[] alphas;
    private ValueAnimator[] animators;
    private ValueAnimator centerAnimator; // 中心圆形的弹性动画
    private boolean isAnimating = false; // 标记动画是否正在运行

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paints = new Paint[NUM_RIPPLES];
        sizes = new float[NUM_RIPPLES];
        alphas = new float[NUM_RIPPLES];
        animators = new ValueAnimator[NUM_RIPPLES];

        // 初始化每个圆形的Paint对象
        for (int i = 0; i < NUM_RIPPLES; i++) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(0xA5CEBD); // 设置颜色
            paints[i] = paint;
            sizes[i] = MIN_SIZE; // 初始大小
            alphas[i] = 1.0f; // 初始透明度
        }

        // 初始化中心圆形的弹性动画
        centerAnimator = ValueAnimator.ofFloat(CENTER_MIN_SIZE, CENTER_MAX_SIZE, CENTER_MIN_SIZE);
        centerAnimator.setDuration(CENTER_ANIMATION_DURATION);
        centerAnimator.setRepeatCount(ValueAnimator.INFINITE);
        centerAnimator.addUpdateListener(valueAnimator -> {
            sizes[0] = (float) valueAnimator.getAnimatedValue(); // 更新中心圆形的大小
            invalidate(); // 重绘视图
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取中心点
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        // 绘制每个圆形
        for (int i = 0; i < NUM_RIPPLES; i++) {
            Paint paint = paints[i];
            paint.setAlpha((int) (255 * alphas[i])); // 设置透明度
            canvas.drawCircle(centerX, centerY, sizes[i], paint); // 绘制圆形
        }
    }

    public void toggleAnimations() {
        if (isAnimating) {
            // 如果动画正在运行，则停止动画并重置状态
            for (ValueAnimator animator : animators) {
                if (animator != null) {
                    animator.cancel(); // 停止动画
                }
            }
            centerAnimator.cancel(); // 停止中心圆形的弹性动画
            // 重置圆形的大小和透明度为初始状态
            for (int i = 1; i < NUM_RIPPLES; i++) {
                sizes[i] = MIN_SIZE;
                alphas[i] = 1.0f;
            }
            invalidate(); // 重绘视图
            isAnimating = false; // 标记动画停止
        } else {
            // 如果动画没有运行，则启动动画
            for (int i = 1; i < NUM_RIPPLES; i++) {
                final int index = i;
                ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
                animator.setStartDelay(i * 1000);
                animator.setDuration(DURATION);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.addUpdateListener(valueAnimator -> {
                    float fraction = (float) valueAnimator.getAnimatedValue();
                    sizes[index] = MIN_SIZE + fraction * (MAX_SIZE - MIN_SIZE); // 动态调整大小
                    alphas[index] = 1 - fraction; // 动态调整透明度
                    invalidate(); // 重绘视图
                });
                animators[i] = animator; // 保存动画对象
                animator.start();
            }
            centerAnimator.start(); // 启动中心圆形的弹性动画
            isAnimating = true; // 标记动画正在运行
        }
    }
}
package com.xuptggg.module.login.base;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class SoftKeyboardVisibilityDetector {

    private View rootView;
    private OnSoftKeyboardVisibilityChangeListener listener;
    private int screenHeight;

    public SoftKeyboardVisibilityDetector(View rootView) {
        this.rootView = rootView;
        screenHeight = rootView.getResources().getDisplayMetrics().heightPixels;
        setupListener();
    }

    public void setOnSoftKeyboardVisibilityChangeListener(OnSoftKeyboardVisibilityChangeListener listener) {
        this.listener = listener;
    }

    private void setupListener() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.bottom - r.top;
                int heightDiff = screenHeight - visibleHeight;
                boolean isKeyboardVisible = heightDiff > screenHeight / 4;

                if (listener != null) {
                    listener.onSoftKeyboardVisibilityChange(isKeyboardVisible);
                }
            }
        });
    }

    public interface OnSoftKeyboardVisibilityChangeListener {
        void onSoftKeyboardVisibilityChange(boolean isVisible);
    }
}
package com.example.module.chat.base;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KeyboardObserver {

    public interface Callback {
        void onKeyboardHeightChanged(int height);
    }

    // 静态工厂方法替代伴生对象
    public static KeyboardObserver create(Activity activity, boolean showDebug) {
        return new KeyboardObserver(activity, showDebug);
    }

    private final float density;
    private final boolean showDebug;
    private final WeakReference<Activity> activityRef;
    private PopupWindow rulerPopWin;
    private PopupWindow cursorPopWin;
    private final Rect rulerRect = new Rect();
    private final Rect cursorRect = new Rect();
    private final List<Callback> callbacks = new LinkedList<>();
    private boolean isWatching = false;
    private boolean notifyOnceWatch = false;
    private int lastKeyboardHeight = 0;

    private KeyboardObserver(Activity activity, boolean showDebug) {
        this.showDebug = showDebug;
        this.activityRef = new WeakReference<>(activity);
        this.density = activity.getResources().getDisplayMetrics().density;

        // 延迟初始化弹窗
        this.rulerPopWin = makeRulerPopWin(activity);
        this.cursorPopWin = makeCursorPopWin(activity);
    }

    // DP 转换工具方法
    private int dpToPx(int dp) {
        return (int) (dp * density + 0.5f);
    }

    private View getDecorView() {
        Activity activity = activityRef.get();
        return (activity != null) ? activity.getWindow().getDecorView() : null;
    }

    private final View.OnLayoutChangeListener rulerLayoutChangeListener =
            new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {

                    if (rulerPopWin.getContentView() != null) {
                        rulerPopWin.getContentView().removeOnLayoutChangeListener(this);
                    }

                    boolean wasWatching = isWatching;
                    isWatching = true;

                    rulerPopWin.getContentView().getGlobalVisibleRect(rulerRect);
                    cursorPopWin.getContentView().getGlobalVisibleRect(cursorRect);

                    int keyboardHeight = rulerRect.bottom - cursorRect.bottom;

                    if (notifyOnceWatch) {
                        if (wasWatching) {
                            if (keyboardHeight != lastKeyboardHeight) {
                                notifyHeightChanged(keyboardHeight);
                            }
                        } else {
                            notifyHeightChanged(keyboardHeight);
                        }
                    } else {
                        if (wasWatching) {
                            if (keyboardHeight != lastKeyboardHeight) {
                                notifyHeightChanged(keyboardHeight);
                            }
                        } else {
                            lastKeyboardHeight = keyboardHeight;
                        }
                    }

                    if (keyboardHeight != lastKeyboardHeight) {
                        notifyHeightChanged(keyboardHeight);
                    }

                    if (showDebug && v instanceof TextView) {
                        TextView textView = (TextView) v;
                        textView.setText(String.valueOf(keyboardHeight));
                        int paddingBottom = Math.max(keyboardHeight - textView.getLineHeight(), 0);
                        textView.setPadding(0, 0, 0, paddingBottom);
                    }
                }
            };

    private final View.OnLayoutChangeListener cursorLayoutChangeListener =
            new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {

                    if (rulerPopWin.isShowing()) {
                        rulerPopWin.dismiss();
                    }
                    View decor = getDecorView();
                    if (decor != null) {
                        rulerPopWin.showAtLocation(decor, Gravity.BOTTOM | Gravity.END, 0, 0);
                    }
                    if (rulerPopWin.getContentView() != null) {
                        rulerPopWin.getContentView().addOnLayoutChangeListener(rulerLayoutChangeListener);
                    }
                }
            };

    private void notifyHeightChanged(int keyboardHeight) {
        List<Callback> copy = new ArrayList<>(callbacks);
        for (Callback cb : copy) {
            cb.onKeyboardHeightChanged(keyboardHeight);
        }
        lastKeyboardHeight = keyboardHeight;
    }

    public void watch(boolean notifyNow) {
        notifyOnceWatch = notifyNow;
        if (!cursorPopWin.isShowing()) {
            View decor = getDecorView();
            if (decor != null) {
                cursorPopWin.showAtLocation(decor, Gravity.BOTTOM | Gravity.END, 0, 0);
            }
            if (cursorPopWin.getContentView() != null) {
                cursorPopWin.getContentView().addOnLayoutChangeListener(cursorLayoutChangeListener);
            }
        }
    }

    public void unwatch() {
        if (cursorPopWin.getContentView() != null) {
            cursorPopWin.getContentView().removeOnLayoutChangeListener(cursorLayoutChangeListener);
        }
        if (cursorPopWin.isShowing()) {
            cursorPopWin.dismiss();
        }
        if (rulerPopWin.getContentView() != null) {
            rulerPopWin.getContentView().removeOnLayoutChangeListener(rulerLayoutChangeListener);
        }
        if (rulerPopWin.isShowing()) {
            rulerPopWin.dismiss();
        }
        notifyOnceWatch = false;
        isWatching = false;
    }

    public void addCallback(Callback callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
        }
    }

    public void removeCallback(Callback callback) {
        callbacks.remove(callback);
    }

    private PopupWindow makeRulerPopWin(Activity activity) {
        PopupWindow popup = new PopupWindow(activity);

        View contentView;
        if (showDebug) {
            TextView textView = new TextView(activity);
            GradientDrawable gd = new GradientDrawable();
            gd.setStroke(dpToPx(1), Color.LTGRAY);
            textView.setBackground(gd);
            textView.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            textView.setTextColor(Color.RED);
            contentView = textView;
        } else {
            contentView = new View(activity);
        }

        popup.setContentView(contentView);
        popup.setBackgroundDrawable(null);
        popup.setWidth(showDebug ? dpToPx(80) : 1);
        popup.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popup.setElevation(0f);
        popup.setFocusable(false);
        popup.setTouchable(false);
        popup.setOutsideTouchable(false);
        return popup;
    }

    private PopupWindow makeCursorPopWin(Activity activity) {
        PopupWindow popup = new PopupWindow(activity);

        View contentView;
        if (showDebug) {
            FrameLayout frame = new FrameLayout(activity);
            View line = new View(activity);
            line.setBackground(new ColorDrawable(Color.RED));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    dpToPx(1),
                    Gravity.BOTTOM
            );
            frame.addView(line, params);
            contentView = frame;
        } else {
            contentView = new View(activity);
        }

        popup.setContentView(contentView);
        popup.setBackgroundDrawable(null);
        popup.setWidth(showDebug ? dpToPx(80) : 1);
        popup.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popup.setElevation(0f);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setFocusable(false);
        popup.setTouchable(false);
        popup.setOutsideTouchable(false);
        return popup;
    }
}
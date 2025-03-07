package com.example.module.chat.base;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import java.util.Map;
import java.util.WeakHashMap;

public class ObserverManager implements Application.ActivityLifecycleCallbacks {

    // 使用 WeakHashMap 防止内存泄漏
    private final Map<Activity, KeyboardObserver> observersMap = new WeakHashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // 初始化键盘观察器
        KeyboardObserver observer = KeyboardObserver.create(
                activity,
                SoftKeyboardGlobal.isDebug
        );
        observer.addCallback(SoftKeyboardGlobal.getInstance());
        observersMap.put(activity, observer);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        // 延迟监听解决窗口令牌问题
        activity.getWindow().getDecorView().addOnLayoutChangeListener(
                new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                               int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        v.removeOnLayoutChangeListener(this);
                        KeyboardObserver observer = observersMap.get(activity);
                        if (observer != null) {
                            observer.watch(false);
                        }
                    }
                });
    }

    @Override
    public void onActivityPaused(Activity activity) {
        KeyboardObserver observer = observersMap.get(activity);
        if (observer != null) {
            observer.unwatch();
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        KeyboardObserver observer = observersMap.remove(activity);
        if (observer != null) {
            observer.removeCallback(SoftKeyboardGlobal.getInstance());
        }
    }

    // 以下空实现保持接口完整性
    @Override public void onActivityStarted(Activity activity) {}
    @Override public void onActivityStopped(Activity activity) {}
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
}
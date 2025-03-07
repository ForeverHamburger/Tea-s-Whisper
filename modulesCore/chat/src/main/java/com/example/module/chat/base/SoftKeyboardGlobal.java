package com.example.module.chat.base;
import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class SoftKeyboardGlobal implements KeyboardObserver.Callback {
    private static volatile SoftKeyboardGlobal INSTANCE;
    private boolean isInstalled = false;
    public static boolean isDebug = false;
    private int lastHeight = 0;
    private final List<SoftKeyboardCallback> callbacks = new LinkedList<>();
    private static final int HEIGHT_THRESHOLD = 50;

    private SoftKeyboardGlobal() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() to get the singleton instance");
        }
    }

    public static SoftKeyboardGlobal getInstance() {
        if (INSTANCE == null) {
            synchronized (SoftKeyboardGlobal.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SoftKeyboardGlobal();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void install(Application app, boolean debug) {
        if (isInstalled) return;
        this.isDebug = debug;
        app.registerActivityLifecycleCallbacks(new ObserverManager());
        isInstalled = true;
    }

    public synchronized void addSoftKeyboardCallback(SoftKeyboardCallback callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
        }
    }

    public synchronized void removeSoftKeyboardCallback(SoftKeyboardCallback callback) {
        callbacks.remove(callback);
    }

    @Override
    public void onKeyboardHeightChanged(int height) {
        List<SoftKeyboardCallback> copy;
        synchronized (this) {
            copy = new ArrayList<>(callbacks);
            copy.removeIf(cb -> !callbacks.contains(cb));
        }

        try {
            for (SoftKeyboardCallback cb : copy) {
                cb.onHeightChanged(height);
            }

            if (lastHeight <= HEIGHT_THRESHOLD && height > HEIGHT_THRESHOLD) {
                for (SoftKeyboardCallback cb : copy) {
                    cb.onOpen(height);
                }
            } else if (lastHeight > HEIGHT_THRESHOLD && height <= HEIGHT_THRESHOLD) {
                for (SoftKeyboardCallback cb : copy) {
                    cb.onClose();
                }
            }
        } catch (Exception e) {
            Log.e("KeyboardCallback", "Callback error", e);
        }

        lastHeight = height;
    }

    public interface SoftKeyboardCallback {
        void onOpen(int height);
        void onClose();
        default void onHeightChanged(int height) {}
    }
}
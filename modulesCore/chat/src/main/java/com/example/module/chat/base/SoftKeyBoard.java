package com.example.module.chat.base;

import android.app.Activity;
import android.graphics.Rect;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class SoftKeyBoard {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    //欲了解更多信息，请访问https://code.google.com/p/android/issues/detail? id=5497
    //要使用此类，只需对已经设置了内容视图的活动调用assistActive（）即可。
    public static void assistActivity (Activity activity) {
        new SoftKeyBoard(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private SoftKeyBoard(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                //visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                //hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);// 全屏模式下： return r.bottom
    }

}
//import android.content.Context;
//import android.util.DisplayMetrics;
//import android.view.ViewConfiguration;
//
///**
// * 修复Android软键盘遮挡输入框的问题（适配全面屏/异形屏）
// * 使用方式：在Activity的onCreate中调用assistActivity()
// */
//public class SoftKeyBoard {
//
//    private final View mChildOfContent;
//    private int usableHeightPrevious;
//    private final FrameLayout.LayoutParams frameLayoutParams;
//
//    // 私有构造函数
//    private SoftKeyBoard(Activity activity) {
//        // 获取Activity的根布局
//        FrameLayout content = activity.findViewById(android.R.id.content);
//        mChildOfContent = content.getChildAt(0);
//
//        // 设置全局布局监听
//        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        possiblyResizeChildOfContent();
//                    }
//                });
//
//        // 获取布局参数
//        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
//    }
//
//    /**
//     * 静态方法启动辅助功能
//     * @param activity 需要调整的Activity实例
//     */
//    public static void assistActivity(Activity activity) {
//        new SoftKeyBoard(activity);
//    }
//
//    /**
//     * 计算可用高度并调整布局
//     */
//    private void possiblyResizeChildOfContent() {
//        int usableHeightNow = computeUsableHeight();
//        if (usableHeightNow != usableHeightPrevious) {
//            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
//            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
//
//            // 当高度差超过1/4屏幕高度时，视为键盘弹出
//            if (heightDifference > (usableHeightSansKeyboard/4)) {
//                // 键盘可见时调整布局高度
//                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
//            } else {
//                // 键盘隐藏时恢复全屏高度（减去导航栏高度）
//                frameLayoutParams.height = usableHeightSansKeyboard - getNavigationBarHeight(mChildOfContent.getContext());
//            }
//
//            // 请求重新布局
//            mChildOfContent.requestLayout();
//            usableHeightPrevious = usableHeightNow;
//        }
//    }
//
//    /**
//     * 计算窗口可见区域高度
//     */
//    private int computeUsableHeight() {
//        Rect rect = new Rect();
//        mChildOfContent.getWindowVisibleDisplayFrame(rect);
//
//        // 适配全面屏设备（增加状态栏高度补偿）
//        int statusBarHeight = getStatusBarHeight(mChildOfContent.getContext());
//        return rect.bottom - statusBarHeight;
//    }
//
//    /**
//     * 获取导航栏高度（适配不同设备）
//     */
//    private int getNavigationBarHeight(Context context) {
//        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
//        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
//
//        // 当设备存在物理按键时返回0
//        if (hasMenuKey || hasBackKey) {
//            return 0;
//        }
//
//        // 获取导航栏资源ID
//        int resourceId = context.getResources().getIdentifier(
//                "navigation_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            return context.getResources().getDimensionPixelSize(resourceId);
//        }
//        return 0;
//    }
//
//    /**
//     * 获取状态栏高度
//     */
//    private int getStatusBarHeight(Context context) {
//        int result = 0;
//        int resourceId = context.getResources().getIdentifier(
//                "status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = context.getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }
//
//    /**
//     * 适配异形屏的屏幕高度计算
//     */
//    private int getRealScreenHeight(Context context) {
//        DisplayMetrics metrics = new DisplayMetrics();
//        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
//                .getDefaultDisplay().getRealMetrics(metrics);
//        return metrics.heightPixels;
//    }
//}

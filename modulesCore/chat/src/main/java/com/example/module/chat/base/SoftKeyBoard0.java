package com.example.module.chat.base;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class SoftKeyBoard0 {
    public static void assistActivity(Activity activity) {
        new SoftKeyBoard0(activity);
    }

    private final View mChildOfContent;
    private int usableHeightPrevious;
    private final FrameLayout.LayoutParams frameLayoutParams;

    private SoftKeyBoard0(Activity activity) {
        FrameLayout content = activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(() -> possiblyResizeChildOfContent(activity));
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent(Activity activity) {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
// 键盘可能刚刚变得可见
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + getNavigationBarHeight(activity);
            } else {
                // keyboard probably just became hidden
// 键盘可能刚刚被隐藏
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    private int getNavigationBarHeight(Activity mActivity) {
        boolean hasMenuKey = ViewConfiguration.get(mActivity).hasPermanentMenuKey();
        int resourceId = mActivity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && !hasMenuKey) {
            if (resourceId > 0 && !hasMenuKey) {
                return mActivity.getResources().getDimensionPixelSize(resourceId);
            }
            return 0;
        }
        return resourceId;
    }
}
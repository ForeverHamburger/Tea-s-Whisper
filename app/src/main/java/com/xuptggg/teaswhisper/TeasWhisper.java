package com.xuptggg.teaswhisper;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class TeasWhisper extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        onCreateARouter();
    }

    private void onCreateARouter() {
        if (isModule()) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    private boolean isModule() {
        return !BuildConfig.isModule;
    }
}

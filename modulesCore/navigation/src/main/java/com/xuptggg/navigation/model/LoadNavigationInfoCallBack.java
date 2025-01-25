package com.xuptggg.navigation.model;

public interface LoadNavigationInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

package com.xuptggg.guidepage.model;

public interface LoadGuideInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

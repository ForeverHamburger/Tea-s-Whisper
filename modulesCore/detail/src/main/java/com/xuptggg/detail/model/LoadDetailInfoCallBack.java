package com.xuptggg.detail.model;

public interface LoadDetailInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

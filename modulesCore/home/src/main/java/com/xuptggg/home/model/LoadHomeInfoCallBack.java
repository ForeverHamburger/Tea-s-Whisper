package com.xuptggg.home.model;

public interface LoadHomeInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

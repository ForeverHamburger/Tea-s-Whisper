package com.xuptggg.search.model;

public interface LoadSearchInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

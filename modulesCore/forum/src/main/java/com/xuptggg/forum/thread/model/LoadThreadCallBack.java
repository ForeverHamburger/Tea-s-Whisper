package com.xuptggg.forum.thread.model;

public interface LoadThreadCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

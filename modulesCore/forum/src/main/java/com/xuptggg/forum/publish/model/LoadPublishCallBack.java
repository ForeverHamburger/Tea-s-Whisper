package com.xuptggg.forum.publish.model;

public interface LoadPublishCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

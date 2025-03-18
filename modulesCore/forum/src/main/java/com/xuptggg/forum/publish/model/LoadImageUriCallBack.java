package com.xuptggg.forum.publish.model;

public interface LoadImageUriCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

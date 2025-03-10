package com.xuptggg.forum.square.model;

public interface LoadForumInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

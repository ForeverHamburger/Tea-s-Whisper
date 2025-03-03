package com.xuptggg.forum.model;

public interface LoadForumInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

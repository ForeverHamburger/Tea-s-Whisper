package com.xuptggg.libnetwork.aword;

public interface LoadTasksCallBack<T> {
    void onSuccess(T data);
    void onFailed(String error);
}

package com.xuptggg.module.login.base;

public interface LoadTasksCallBack<T> {
    void onSuccess(T data);
    void onFailed(String error);
}

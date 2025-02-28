package com.example.module.chat.base.other;

public interface LoadTasksCallBack<T> {
    void onSuccess(T data);
    void onFailed(String error);
}

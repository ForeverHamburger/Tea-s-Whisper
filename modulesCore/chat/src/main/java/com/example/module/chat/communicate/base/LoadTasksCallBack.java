package com.example.module.chat.communicate.base;

public interface LoadTasksCallBack<T> {
    void onSuccess(T data);
    void onFailed(String error);
}

package com.xuptggg.individual.edit.model;

public interface LoadEditInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

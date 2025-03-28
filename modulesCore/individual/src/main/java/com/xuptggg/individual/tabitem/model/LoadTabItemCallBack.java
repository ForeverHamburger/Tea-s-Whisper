package com.xuptggg.individual.tabitem.model;

public interface LoadTabItemCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

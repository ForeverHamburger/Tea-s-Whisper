package com.xuptggg.detection.function.model;

public interface LoadDetectionInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

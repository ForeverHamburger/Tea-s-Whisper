package com.xuptggg.individual.personal.model;

public interface LoadIndividualInfoCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}

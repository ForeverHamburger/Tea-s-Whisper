package com.xuptggg.individual.edit.model;

public interface LoadPostEditCallBack<T> {
    void onPostSuccess(T t);
    void onStartPost();
    void onFailedPost();
    void onFinishPost();
}

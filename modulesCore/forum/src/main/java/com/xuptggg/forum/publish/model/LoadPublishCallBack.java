package com.xuptggg.forum.publish.model;

public interface LoadPublishCallBack<T> {
    void onSuccessPublished(T t);
    void onStartPublish();
    void onFailedPublish();
    void onFinishPublish();
}

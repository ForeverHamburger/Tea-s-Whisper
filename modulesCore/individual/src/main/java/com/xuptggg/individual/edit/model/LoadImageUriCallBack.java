package com.xuptggg.individual.edit.model;

public interface LoadImageUriCallBack<T> {
    void onUploadSuccess(T t);
    void onUploadStart();
    void onUploadFailed();
    void onUploadFinish();
}

package com.xuptggg.forum.publish.presenter;

import android.util.Log;

import com.xuptggg.forum.publish.contract.IPublishContract;
import com.xuptggg.forum.publish.model.LoadImageUriCallBack;
import com.xuptggg.forum.publish.model.PublishInfo;

import java.io.File;
import java.util.List;

public class PublishPresenter implements IPublishContract.IPublishPresenter, LoadImageUriCallBack<List<String>> {
    private IPublishContract.IPublishModel model;
    private IPublishContract.IPublishView view;

    public PublishPresenter(IPublishContract.IPublishModel model, IPublishContract.IPublishView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getUri(List<File> files, String token) {
        model.getUriFromFile(files,token,this);
    }

    @Override
    public void publishThread(PublishInfo publishInfos, String token) {
        model.publishThread(publishInfos,token,this);
    }

    @Override
    public void onSuccess(List<String> strings) {
        view.showMessage(strings);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onFinish() {

    }
}

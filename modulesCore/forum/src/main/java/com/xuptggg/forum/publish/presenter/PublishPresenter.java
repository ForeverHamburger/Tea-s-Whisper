package com.xuptggg.forum.publish.presenter;

import android.graphics.drawable.Icon;
import android.net.Uri;

import com.xuptggg.forum.publish.contract.IPublishContract;
import com.xuptggg.forum.publish.model.LoadPublishCallBack;
import com.xuptggg.forum.publish.model.PublishInfo;

import java.util.List;

public class PublishPresenter implements IPublishContract.IPublishPresenter, LoadPublishCallBack<String> {
    private IPublishContract.IPublishModel model;
    private IPublishContract.IPublishView view;

    public PublishPresenter(IPublishContract.IPublishModel model, IPublishContract.IPublishView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getUri(List<Uri> uris) {
        model.getUriFromFile(uris);
    }

    @Override
    public void publishThread(PublishInfo publishInfos) {
        model.publishThread(publishInfos);
    }

    @Override
    public void onSuccess(String string) {

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

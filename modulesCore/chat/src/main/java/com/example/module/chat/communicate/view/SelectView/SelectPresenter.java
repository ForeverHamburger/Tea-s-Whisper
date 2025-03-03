package com.example.module.chat.communicate.view.SelectView;

import com.example.module.chat.base.other.LoadTasksCallBack;


public class SelectPresenter implements SelectContract.Presenter, LoadTasksCallBack<String> {
    private SelectContract.View mView;
    private SelectContract.Model mModel;

    public SelectPresenter(SelectContract.View mView, SelectContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }

    @Override
    public void getSelectInfo(String content, String sessionId) {
        mModel.getSelectInfo(content, sessionId, this);
    }

    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }

    @Override
    public void onSuccess(String data) {
        if (mView != null && mView.isACtive()) {
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error);
        if (mView != null && mView.isACtive()) {
        }
    }
}
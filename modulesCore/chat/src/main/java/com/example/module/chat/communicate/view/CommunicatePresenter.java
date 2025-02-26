package com.example.module.chat.communicate.view;

import com.example.module.chat.communicate.base.LoadTasksCallBack;

public class CommunicatePresenter implements CommunicateContract.Presenter, LoadTasksCallBack<String> {
    private CommunicateContract.View mView;
    private CommunicateContract.Model mModel;
    public CommunicatePresenter(CommunicateContract.View mView, CommunicateContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }
    @Override
    public void getCommunicateInfo(String username, String password) {
        mModel.getCommunicateInfo(username, password, this);
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

    }
}
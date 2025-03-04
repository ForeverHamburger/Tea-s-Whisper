package com.example.module.chat.communicate.view.CommunicateView;

import com.example.module.chat.base.database.communicate.Data;
import com.example.module.chat.base.other.LoadTasksCallBack;

public class CommunicatePresenter implements CommunicateContract.Presenter, LoadTasksCallBack<Data> {
    private CommunicateContract.View mView;
    private CommunicateContract.Model mModel;
    public CommunicatePresenter(CommunicateContract.View mView, CommunicateContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }
    @Override
    public void getCommunicateInfo(String content, String sessionId) {
        mModel.getCommunicateInfo(content,sessionId,this);
    }
    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }
    @Override
    public void onSuccess(Data data) {
        if (mView != null && mView.isACtive()) {
            mView.aiResponse(data.getContent());
            mView.setSessionId(data.getSessionID());
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error);
        if (mView != null && mView.isACtive()) {
            mView.aiResponse(error);
        }
    }
}
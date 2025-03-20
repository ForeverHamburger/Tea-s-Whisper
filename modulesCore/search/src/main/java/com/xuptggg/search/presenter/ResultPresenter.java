package com.xuptggg.search.presenter;

import android.util.Log;

import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.xuptggg.search.contract.ResultContract;

public class ResultPresenter implements ResultContract.Presenter, LoadTasksCallBack<String> {
    private ResultContract.View mView;
    private ResultContract.Model mModel;

    public ResultPresenter(ResultContract.View mView, ResultContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }


    @Override
    public void getResultInfo(String content, String tag, String size) {
        mModel.getResultInfo(content, tag, size, this);

    }

    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }

    @Override
    public void getToken(String token) {
        mModel.getToken(token);

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
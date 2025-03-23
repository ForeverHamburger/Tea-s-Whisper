package com.xuptggg.search.presenter;

import android.util.Log;
import android.widget.Toast;

import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.xuptggg.search.contract.ResultContract;

import java.util.List;

public class ResultPresenter<T> implements ResultContract.Presenter<T>, LoadTasksCallBack<List<T>> {
    private ResultContract.View<T> mView;
    private ResultContract.Model mModel;

    public ResultPresenter(ResultContract.View<T> mView, ResultContract.Model mModel) {
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
    public void onSuccess(List<T> data) {
        if (mView != null && mView.isACtive()) {
            Log.i("ResultPresenter", "onSuccess: " + data.get(0).toString());
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error);
        if (mView != null && mView.isACtive()) {

        }
    }
}
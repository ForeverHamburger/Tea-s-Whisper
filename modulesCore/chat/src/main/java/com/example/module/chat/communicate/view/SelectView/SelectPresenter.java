package com.example.module.chat.communicate.view.SelectView;

import android.util.Log;

import com.example.module.chat.base.database.select.DataItem;
import com.example.module.chat.base.other.LoadTasksCallBack;

import java.util.ArrayList;
import java.util.List;


public class SelectPresenter implements SelectContract.Presenter, LoadTasksCallBack<List<DataItem>> {
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
    public void getHistoryDataInfo() {
        mModel.getHistoryListInfo(this);
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
    public void onSuccess(List<DataItem> data) {
        if (mView != null && mView.isACtive()) {
            if (data == null || data.isEmpty()) {
//                mView.showEmptyHistory();
            } else {
                mView.displayHistoryData(data,null);
                for (DataItem mydata : data) {
                    Log.d("HistoryData", mydata.getTitle());
                }
            }
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error);
        if (mView != null && mView.isACtive()) {
            List<DataItem> err = new ArrayList<>();
            err.add(new DataItem());
            mView.displayHistoryData(err,error);
        }
    }
}
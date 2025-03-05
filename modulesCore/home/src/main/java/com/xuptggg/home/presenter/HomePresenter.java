package com.xuptggg.home.presenter;

import android.util.Log;

import com.xuptggg.home.contract.IHomeContract;
import com.xuptggg.home.model.LoadHomeInfoCallBack;
import com.xuptggg.home.model.infos.TeaInfo;

import java.util.List;

public class HomePresenter implements IHomeContract.IHomePresenter, LoadHomeInfoCallBack<List<TeaInfo>> {
    private final static String TAG = "HomePresenter";
    private  IHomeContract.IHomeModel model;
    private  IHomeContract.IHomeView view;

    public HomePresenter(IHomeContract.IHomeModel model, IHomeContract.IHomeView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getHomeInfo(String info) {
        Log.d(TAG, "getHomeInfo: " + "发送请求至Model");
        model.execute(info,this);
    }

    @Override
    public void onSuccess(List<TeaInfo> teaInfos) {
        view.showHomeInfomation(teaInfos);
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

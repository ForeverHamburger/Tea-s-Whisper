package com.xuptggg.navigation.presenter;

import com.xuptggg.navigation.contract.INavigationContract;
import com.xuptggg.navigation.model.LoadNavigationInfoCallBack;
import com.xuptggg.navigation.model.NavigationInfo;

import java.util.List;

public class NavigationPresenter implements INavigationContract.INavigationPresenter, LoadNavigationInfoCallBack<List<NavigationInfo>> {
    private INavigationContract.INavigationModel model;
    private INavigationContract.INavigationView view;

    public NavigationPresenter(INavigationContract.INavigationModel model, INavigationContract.INavigationView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getNavigationInfo(String info) {
        model.execute(info,this);
    }

    @Override
    public void onSuccess(List<NavigationInfo> navigationInfos) {
        view.showNavigationInfomation(navigationInfos);
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

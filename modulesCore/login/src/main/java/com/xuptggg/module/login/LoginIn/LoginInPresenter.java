package com.xuptggg.module.login.LoginIn;

import com.xuptggg.module.login.base.LoadTasksCallBack;

public class LoginInPresenter implements LoginInContract.Presenter, LoadTasksCallBack<String> {
    private LoginInContract.View mView;
    private LoginInContract.Model mModel;
    public LoginInPresenter(LoginInContract.View mView, LoginInContract.Model mModel)
    {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }
    @Override
    public void getLoginInInfo(String ip) {

        mModel.getLoginInInfo(ip, this);
    }

    @Override
    public void onstart() {
        getLoginInInfo("");
    }

    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }

    @Override
    public void onSuccess( String data) {
        if (mView!=null&&mView.isACtive()) {
//            mView.setStarData(data);
        }
    }

    @Override
    public void onFailed() {

    }
}
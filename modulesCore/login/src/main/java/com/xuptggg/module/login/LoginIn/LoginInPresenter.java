package com.xuptggg.module.login.LoginIn;

import static android.provider.Settings.System.getString;

import android.widget.Toast;

import com.xuptggg.module.login.R;
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
    public void getLoginInInfo(String phoneoremail, String password) {

        mModel.getLoginInInfo(phoneoremail, password, this);
    }

    @Override
    public void onstart() {
//        getLoginInInfo("lbd", "dsb");
    }

    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }
    @Override
    public void onLoginClick(String phoneoremail, String password) {
        getLoginInInfo(phoneoremail, password);

    }
    @Override
    public void onSuccess( String data) {
        System.out.println("LoginInPresenter onSuccess"+data);
        if (mView!=null&&mView.isACtive()) {
//            mView.setStarData(data);
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error);

    }
}
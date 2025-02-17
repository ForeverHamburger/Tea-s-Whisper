package com.xuptggg.module.login.VerifyLogin;

import com.xuptggg.module.login.Register.RegisterContract;
import com.xuptggg.module.login.base.LoadTasksCallBack;

public class VerifyLoginPresenter implements VerifyLoginContract.Presenter, LoadTasksCallBack<String> {
    private VerifyLoginContract.View mView;
    private VerifyLoginContract.Model mModel;
    public VerifyLoginPresenter(VerifyLoginContract.View mView, VerifyLoginContract.Model mModel)
    {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }


    @Override
    public void getVerifyLoginInfo(String email, String password, String phone, String verificationCode) {
        mModel.getVerifyLoginInfo(email, verificationCode, this);

    }

    @Override
    public void onstart() {
    }

    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }

    @Override
    public void onVerifyLoginClick(String email, String verificationCode) {
        mModel.getVerifyLoginInfo(email,  verificationCode, this);

    }

    @Override
    public void getVerificationCode(String email) {

        mModel.getVerificationCode(email, this);
    }

    @Override
    public void onSuccess(String data) {

        if (mView!=null&&mView.isACtive()) {
//            mView.setStarData(data);
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error);

    }
}

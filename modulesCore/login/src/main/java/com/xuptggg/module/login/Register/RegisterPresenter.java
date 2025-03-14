package com.xuptggg.module.login.Register;

import android.content.Context;
import android.widget.Toast;

import com.xuptggg.module.login.LoginIn.LoginInContract;
import com.xuptggg.module.login.base.LoadTasksCallBack;

public class RegisterPresenter implements RegisterContract.Presenter, LoadTasksCallBack<String> {
    private RegisterContract.View mView;
    private RegisterContract.Model mModel;
    public RegisterPresenter(RegisterContract.View mView, RegisterContract.Model mModel)
    {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }


    @Override
    public void getRegisterInfo(String email, String password, String phone, String verificationCode) {
        mModel.getRegisterInfo(email, password, phone, verificationCode, this);

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
    public void onRegisterClick(String email, String password, String phone, String verificationCode) {
        mModel.getRegisterInfo(email, password, phone, verificationCode, this);

    }

    @Override
    public void getVerificationCode(String email) {
        mModel.getVerificationCode(email, this);
    }

    @Override
    public void onSuccess(String data) {

        if (mView!=null&&mView.isACtive()) {
            mView.showSuccess(data);
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error);

    }
}
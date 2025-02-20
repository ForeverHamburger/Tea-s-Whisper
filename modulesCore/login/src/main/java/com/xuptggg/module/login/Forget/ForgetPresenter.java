package com.xuptggg.module.login.Forget;

import com.xuptggg.module.login.Register.RegisterContract;
import com.xuptggg.module.login.base.LoadTasksCallBack;

public class ForgetPresenter implements ForgetContract.Presenter, LoadTasksCallBack<String> {
    private ForgetContract.View mView;
    private ForgetContract.Model mModel;
    public ForgetPresenter(ForgetContract.View mView, ForgetContract.Model mModel)
    {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }


    @Override
    public void getForgetInfo(String email, String verificationCode, String password, String password1) {
        mModel.getForgetInfo(email,verificationCode,password,password1,this);
    }

    @Override
    public void getVerificationCode(String email) {
        mModel.getVerificationCode(email,this);
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
    public void onForgetClick(String email, String verificationCode, String password, String confirmPassword) {
        getForgetInfo(email, verificationCode, password, confirmPassword);
    }


    @Override
    public void onSuccess( String data) {

        if (mView!=null&&mView.isACtive()) {
//            mView.setStarData(data);
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error);

    }
}
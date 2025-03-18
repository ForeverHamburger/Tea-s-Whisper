package com.xuptggg.module.login.Forget;

import android.util.Log;

import com.xuptggg.module.libbase.eventbus.TokenManager;
import com.xuptggg.module.login.Register.RegisterContract;
import com.xuptggg.module.login.base.LoadTasksCallBack;
import com.xuptggg.module.login.base.VerificationRequestManager;

import org.greenrobot.eventbus.EventBus;

public class ForgetPresenter implements ForgetContract.Presenter, LoadTasksCallBack<String> {
    private ForgetContract.View mView;
    private ForgetContract.Model mModel;
    private String Email;

    public ForgetPresenter(ForgetContract.View mView, ForgetContract.Model mModel)
    {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }


    @Override
    public void getForgetInfo(String email, String verificationCode,String password, String confirmPassword) {
        mModel.getForgetInfo(email,verificationCode,password,confirmPassword,this);
    }

    @Override
    public void getVerificationCode(String email) {
        Email = email;
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
        if (!VerificationRequestManager.getInstance().isRequestValid(email)) {
            onFailed("请先获取验证码");
            return;
        }
        getForgetInfo(email, verificationCode, password, confirmPassword);
    }


    @Override
    public void onSuccess( String data) {
        if (mView != null && mView.isACtive()) {
            if (VerificationRequestManager.getInstance().addRequested(Email, data)) {
                Email = null;
            }
            mView.showSuccess(data);
        }
    }

    @Override
    public void onFailed(String error) {
        if (mView!=null&&mView.isACtive()) {
            mView.showError(error);
        }
        System.out.println(error);

    }
}
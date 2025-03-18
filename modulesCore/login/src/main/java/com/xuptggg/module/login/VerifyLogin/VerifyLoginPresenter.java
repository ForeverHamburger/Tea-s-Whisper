package com.xuptggg.module.login.VerifyLogin;

import android.util.Log;

import com.xuptggg.module.libbase.eventbus.TokenManager;
import com.xuptggg.module.login.Register.RegisterContract;
import com.xuptggg.module.login.base.LoadTasksCallBack;
import com.xuptggg.module.login.base.VerificationRequestManager;

import org.greenrobot.eventbus.EventBus;

public class VerifyLoginPresenter implements VerifyLoginContract.Presenter, LoadTasksCallBack<String> {
    private VerifyLoginContract.View mView;
    private VerifyLoginContract.Model mModel;
    private String Email;
    private static final String TAG = "VerifyLoginPresenter";

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
        if (!VerificationRequestManager.getInstance().isRequestValid(email)) {
            onFailed("请先获取验证码");
            return;
        }
        mModel.getVerifyLoginInfo(email,  verificationCode, this);

    }

    @Override
    public void getVerificationCode(String email) {
        Email = email;
        mModel.getVerificationCode(email, this);
    }

    @Override
    public void onSuccess(String data) {
        if (mView != null && mView.isACtive()) {
            if (VerificationRequestManager.getInstance().addRequested(Email, data)) {
                Email = null;
            }
            if (data != null) {
                mView.showSuccess("验证码登录成功！");
                System.out.println(data);
                TokenManager tokenManager = new TokenManager();
                tokenManager.setToken(data);
                Log.d(TAG, "onSuccess: " + tokenManager.getToken());
                EventBus.getDefault().postSticky(tokenManager);
            } else {
                mView.showSuccess(data);
                System.out.println(data);
                Log.d(TAG, "onSuccess: " + "token获取失败");
            }
        }
    }


    @Override
    public void onFailed(String error) {
        System.out.println(error);
        if (mView != null && mView.isACtive()) {
            mView.showError(error);
        }
    }
}

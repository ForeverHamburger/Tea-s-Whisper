package com.xuptggg.module.login.LoginIn;

import static android.provider.Settings.System.getString;

import android.util.Log;
import android.widget.Toast;

import com.xuptggg.module.libbase.eventbus.TokenManager;
import com.xuptggg.module.login.R;
import com.xuptggg.module.login.base.LoadTasksCallBack;

import org.greenrobot.eventbus.EventBus;

public class LoginInPresenter implements LoginInContract.Presenter, LoadTasksCallBack<String> {
        private final static String TAG = "LoginInPresenter";
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
    public void onSuccess(String data) {
        if (mView!=null&&mView.isACtive()) {
            Log.d(TAG, "onSuccess: " + 111);
            mView.loginSuccess();

            if (data != null) {
                TokenManager tokenManager = new TokenManager();
                tokenManager.setToken(data);
                Log.d(TAG, "onSuccess: " + tokenManager.getToken());
                EventBus.getDefault().postSticky(tokenManager);
            } else {
                Log.d(TAG, "onSuccess: " + "token获取失败");
            }
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error);
    }
}
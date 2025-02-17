package com.xuptggg.module.login.VerifyLogin;

import com.xuptggg.module.login.Register.RegisterContract;
import com.xuptggg.module.login.base.BaseView;
import com.xuptggg.module.login.base.LoadTasksCallBack;

public interface VerifyLoginContract {
    interface Model {
        void getVerifyLoginInfo(String email,  String verificationCode, LoadTasksCallBack callBack);

        void getVerificationCode(String email, LoadTasksCallBack callBack);
    }

    interface Presenter {
        void getVerifyLoginInfo(String email, String password, String phone, String verificationCode);

        void onstart();

        void unSubscribe();

        void onVerifyLoginClick(String email, String verificationCode);

        void getVerificationCode(String string);
    }

    interface View extends BaseView<VerifyLoginContract.Presenter> {
        void showError();

        //        void setLoginInData(musicData starData);
        Boolean isACtive();
    }
}
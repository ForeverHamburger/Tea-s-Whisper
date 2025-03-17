package com.xuptggg.module.login.Forget;

import com.xuptggg.module.login.base.BaseView;
import com.xuptggg.module.login.base.LoadTasksCallBack;

public interface ForgetContract {
    interface Model {
        void getForgetInfo(String email, String verificationCode,String password, String password1, LoadTasksCallBack<String> callBack);
        void getVerificationCode(String email, LoadTasksCallBack<String> callBack);
    }

    interface Presenter {
        void getForgetInfo(String email, String verificationCode,String password, String confirmPassword);
        void getVerificationCode(String email);
        void onstart();

        void unSubscribe();

        void onForgetClick(String email, String verificationCode,String password, String confirmPassword);
    }

    interface View extends BaseView<ForgetContract.Presenter> {
        void showError();

        Boolean isACtive();
    }
}
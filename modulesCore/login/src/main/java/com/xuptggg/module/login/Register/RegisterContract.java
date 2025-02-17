package com.xuptggg.module.login.Register;

import com.xuptggg.module.login.LoginIn.LoginInContract;
import com.xuptggg.module.login.base.BaseView;
import com.xuptggg.module.login.base.LoadTasksCallBack;

public class RegisterContract {
    interface Model {
        void getRegisterInfo(String email, String password,String phone, String verificationCode, LoadTasksCallBack callBack);
        void getVerificationCode(String email,LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getRegisterInfo(String email, String password,String phone, String verificationCode);
        void onstart();
        void unSubscribe();

        void onRegisterClick(String email, String password,String phone, String verificationCode);

        void getVerificationCode(String string);
    }
    interface View extends BaseView<RegisterContract.Presenter> {
        void showError();
        //        void setLoginInData(musicData starData);
        Boolean isACtive();
    }
}

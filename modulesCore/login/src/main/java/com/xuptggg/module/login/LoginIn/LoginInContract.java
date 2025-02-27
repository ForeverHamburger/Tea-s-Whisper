package com.xuptggg.module.login.LoginIn;

import com.xuptggg.module.login.base.BaseView;
import com.xuptggg.module.login.base.LoadTasksCallBack;

public interface LoginInContract {
    interface Model {
        void getLoginInInfo(String username, String password, LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getLoginInInfo(String username, String password);
        void onstart();
        void unSubscribe();

        void onLoginClick(String username, String password);

    }
    interface View extends BaseView<Presenter> {
        void showError();
//        void setLoginInData(musicData starData);
        Boolean isACtive();
        void loginSuccess();
    }
}
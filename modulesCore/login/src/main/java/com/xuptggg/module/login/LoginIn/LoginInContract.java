package com.xuptggg.module.login.LoginIn;

import com.xuptggg.module.login.base.BaseView;
import com.xuptggg.module.login.base.LoadTasksCallBack;

public interface LoginInContract {
    interface Model {
        void getLoginInInfo(String ip, LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getLoginInInfo(String ip);
        void onstart();
        void unSubscribe();
    }
    interface View extends BaseView<Presenter> {
        void showError();
//        void setLoginInData(musicData starData);
        Boolean isACtive();
    }
}
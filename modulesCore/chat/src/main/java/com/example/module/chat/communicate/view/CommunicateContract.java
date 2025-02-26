package com.example.module.chat.communicate.view;

import com.example.module.chat.communicate.base.BaseView;
import com.example.module.chat.communicate.base.LoadTasksCallBack;

public interface CommunicateContract {
    interface Model {
        void getCommunicateInfo(String username, String password, LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getCommunicateInfo(String username, String password);
        void unSubscribe();
    }
    interface View extends BaseView<Presenter> {
        void showError();
        Boolean isACtive();
    }
}
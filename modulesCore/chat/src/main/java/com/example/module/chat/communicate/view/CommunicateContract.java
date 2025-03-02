package com.example.module.chat.communicate.view;

import com.example.module.chat.base.other.BaseView;
import com.example.module.chat.base.other.LoadTasksCallBack;

public interface CommunicateContract {
    interface Model {
        void getCommunicateInfo(String message,  LoadTasksCallBack callBack);
    }
    interface Presenter {
        void getCommunicateInfo(String message);
        void unSubscribe();
    }
    interface View extends BaseView<Presenter> {
        void showError();
        Boolean isACtive();
        String aiResponse(String userInput);
    }
}
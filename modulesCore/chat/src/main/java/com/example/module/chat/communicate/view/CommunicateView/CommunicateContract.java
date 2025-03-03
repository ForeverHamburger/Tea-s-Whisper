package com.example.module.chat.communicate.view.CommunicateView;

import com.example.module.chat.base.other.BaseView;
import com.example.module.chat.base.other.LoadTasksCallBack;

public interface CommunicateContract {
    interface Model {
        void getCommunicateInfo(String content, String sessionId, LoadTasksCallBack callBack);
    }

    interface Presenter {
        void getCommunicateInfo(String content, String sessionId);

        void unSubscribe();
    }

    interface View extends BaseView<Presenter> {
        void showError();

        Boolean isACtive();

        String aiResponse(String content);
        void setSessionId(String SessionId);
    }
}
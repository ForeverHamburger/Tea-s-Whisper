package com.example.module.chat.communicate.view.SelectView;

import com.example.module.chat.base.other.BaseView;
import com.example.module.chat.base.other.LoadTasksCallBack;

public interface SelectContract {
    interface Model {
        void getSelectInfo(String content, String sessionId, LoadTasksCallBack callBack);
    }

    interface Presenter {
        void getSelectInfo(String content, String sessionId);

        void unSubscribe();
    }

    interface View extends BaseView<Presenter> {
        void showError();

        Boolean isACtive();
    }
}
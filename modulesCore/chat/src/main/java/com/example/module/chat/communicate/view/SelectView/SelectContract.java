package com.example.module.chat.communicate.view.SelectView;

import com.example.module.chat.base.database.select.Agent;
import com.example.module.chat.base.database.select.DataItem;
import com.example.module.chat.base.other.BaseView;
import com.xuptggg.libnetwork.aword.LoadTasksCallBack;

import java.util.List;

public interface SelectContract {
    interface Model {
        void getSelectInfo(String content, String sessionId, LoadTasksCallBack callBack);

        void getHistoryListInfo( LoadTasksCallBack callBack);
        void getToken(String token);
        List<Agent> getAgents();
    }

    interface Presenter {
        void getSelectInfo(String content, String sessionId);
        void getHistoryDataInfo();
        List<Agent> getAgents();
        void unSubscribe();

        void getToken(String token);
    }

    interface View extends BaseView<Presenter> {
        void showError();

        Boolean isACtive();

        void displayHistoryData(List<DataItem> data,String errorCode);

        void showEmptyHistory();
    }
}
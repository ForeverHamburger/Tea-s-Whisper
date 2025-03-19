package com.example.module.chat.communicate.view.CommunicateView;

import com.example.module.chat.base.other.BaseView;
import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.example.module.chat.communicate.base.ChatMessage;

import java.util.List;

public interface CommunicateContract {
    interface Model {
        void getCommunicateInfo(String content, String sessionId, LoadTasksCallBack callBack);
        void getHistoryInfo(String sessionId, LoadTasksCallBack callBack);
        void getToken(String token);
    }

    interface Presenter {
        void getCommunicateInfo(String content, String sessionId);
        void getHistoryInfo(String sessionId);
        void unSubscribe();

        void getToken(String token);
    }

    interface View extends BaseView<Presenter> {
        void showError();

        Boolean isACtive();

        void aiResponse(ChatMessage chatMessage);
        void setSessionId(String SessionId);

        void historyResponse(List<ChatMessage> chatMessageList);
    }
}
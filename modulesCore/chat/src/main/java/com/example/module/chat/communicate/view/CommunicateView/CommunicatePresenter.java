package com.example.module.chat.communicate.view.CommunicateView;

import com.example.module.chat.base.database.communicate.Data;
import com.example.module.chat.base.other.LoadTasksCallBack;
import com.example.module.chat.communicate.base.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class CommunicatePresenter implements CommunicateContract.Presenter, LoadTasksCallBack<List<Data>> {
    private CommunicateContract.View mView;
    private CommunicateContract.Model mModel;
    public CommunicatePresenter(CommunicateContract.View mView, CommunicateContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
        mView.setPresenter(this);
    }
    @Override
    public void getCommunicateInfo(String content, String sessionId) {
        mModel.getCommunicateInfo(content,sessionId,this);
    }

    @Override
    public void getHistoryInfo(String sessionId) {
        mModel.getHistoryInfo(sessionId,this);
    }

    @Override
    public void unSubscribe() {
        mModel = null;
        mView = null;
    }

    @Override
    public void onSuccess(List<Data> data) {
        if (mView != null && mView.isACtive()) {
            if (data != null) {
                if (data.size() == 1) {

                    mView.aiResponse(new ChatMessage(ChatMessage.TYPE_RECEIVED, data.get(0).getContent()));
                    mView.setSessionId(data.get(0).getSessionID());

                } else {

                    List<ChatMessage> chatMessageList = new ArrayList<>();
                    for (int i = 1; i < data.size(); i++) {
                        Data item = data.get(i);
                        int type = "user".equals(item.getRole()) ? ChatMessage.TYPE_SENT : ChatMessage.TYPE_RECEIVED;
                        chatMessageList.add(new ChatMessage(type, item.getContent(),item.getTimestamp().getTime()));
                    }
                    mView.setSessionId(data.get(0).getSessionID());
                    mView.historyResponse(chatMessageList);

                }
            }
        }
    }

    @Override
    public void onFailed(String error) {
        System.out.println(error.toString());
        if (mView != null && mView.isACtive()) {
            mView.aiResponse(new ChatMessage(ChatMessage.TYPE_RECEIVED, error));
        }
    }
}
package com.example.module.chat.communicate.view.CommunicateView;

import com.example.module.chat.base.other.LoadTasksCallBack;
import com.example.module.chat.base.other.NetworkHelper;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.request.RequestParams;

public class CommunicateModel implements CommunicateContract.Model {
    private final NetworkHelper networkHelper = new NetworkHelper();
    String sessionId;

    @Override
    public void getCommunicateInfo(String content, String sessionId, LoadTasksCallBack callBack) {
        if (sessionId != null && !sessionId.equals(this.sessionId)) {
            this.sessionId = sessionId;
        }
        RequestParams params = new RequestParams();
        params.put("content", content);
        params.put("session_id", sessionId);

        networkHelper.performPostRequest(URL.CHAT_URL, params, callBack);
    }

}
package com.example.module.chat.communicate.view.SelectView;

import com.example.module.chat.base.other.LoadTasksCallBack;
import com.example.module.chat.base.other.NetworkHelper;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.request.RequestParams;

public class SelectModel implements SelectContract.Model {
    private final NetworkHelper networkHelper = new NetworkHelper();

    @Override
    public void getSelectInfo(String content, String sessionId, LoadTasksCallBack callBack) {
        RequestParams params = new RequestParams();
        params.put("content", content);
        params.put("session_id", sessionId);

        networkHelper.performPostRequest(URL.CHAT_URL, params, callBack);
    }

}
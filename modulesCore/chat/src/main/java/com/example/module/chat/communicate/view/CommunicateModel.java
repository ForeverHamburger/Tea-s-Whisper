package com.example.module.chat.communicate.view;

import com.example.module.chat.base.other.LoadTasksCallBack;
import com.example.module.chat.base.other.NetworkHelper;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.request.RequestParams;

public class CommunicateModel implements CommunicateContract.Model {
    private final NetworkHelper networkHelper = new NetworkHelper();
    @Override
    public void getCommunicateInfo(String message,LoadTasksCallBack callBack) {
        RequestParams params = new RequestParams();
        params.put("message", message);

        networkHelper.performPostRequest(URL.CHAT_URL, params, callBack);
    }

}
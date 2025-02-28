package com.example.module.chat.base.other;

import android.util.Log;

import com.example.module.chat.base.database.MyResponse;
import com.google.gson.Gson;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkHelper {

    private static final String TAG = "NetworkHelper";

    public void performPostRequest(String url, RequestParams params, LoadTasksCallBack<String> callBack) {
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                handleMyResponse(url, (MyResponse) responseObj, callBack);
            }

            @Override
            public void onFailure(Object reasonObj) {
                callBack.onFailed(reasonObj.toString());
            }
        }, MyResponse.class);

        MyOkHttpClient.post(MyRequest.PostRequest(url, params), handle);
    }

    private void handleMyResponse(String url, MyResponse responseObj, LoadTasksCallBack<String> callBack) {
        Log.d(TAG, "Response in chat: " + responseObj);

        if (responseObj == null || responseObj.toString().trim().equals("")) {
            callBack.onFailed("响应为空");
            return;
        }

        if (responseObj.getCode() == 1) {
            if (url.equals(URL.CHAT_URL)) {
                if (responseObj.getMsg().equals("success"))
                    callBack.onSuccess(responseObj.getData().getChoices().get(0).getMessage().getContent());
                else
                    callBack.onFailed(responseObj.getMsg());
            }
        } else {
            callBack.onFailed(responseObj.getMsg());
        }
    }
}
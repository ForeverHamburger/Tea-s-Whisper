package com.example.module.chat.base.other;

import android.util.Log;

import com.example.module.chat.base.database.Data;
import com.example.module.chat.base.database.MyResponse;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.exception.MyHttpException;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

public class NetworkHelper {

    private static final String TAG = "NetworkHelper";
    String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjotMTc5MjEzNzczMDEzMDQ0MDE5MiwidXNlcm5hbWUiOiJ1c2VybmFtZSIsImV4cCI6MTc3MjUxNTE1MywiaXNzIjoiTEJES0lORyJ9.OO3zLux1aEMPhnPEkaXSFX_oLXPBf_bWXOmcUr6uG68";

    RequestParams mToken = new RequestParams();

    public void performPostRequest(String url, RequestParams params, LoadTasksCallBack<Data> callBack) {
        mToken.put("Authorization", "Bearer " + apiKey);
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                handleMyResponse(url, (MyResponse) responseObj, callBack);
            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj != null) {
                    if (reasonObj instanceof MyHttpException) {
                        callBack.onFailed(((MyHttpException) reasonObj).getErrorMessage());
                    }
                }
                callBack.onFailed(reasonObj.toString());
            }
        }, MyResponse.class);

        MyOkHttpClient.post(MyRequest.PostRequest(url, params, mToken), handle);
    }


    private void handleMyResponse(String url, MyResponse responseObj, LoadTasksCallBack<Data> callBack) {
        Log.d(TAG, "Response in chat: " + responseObj);
        Log.d(TAG, "Response in chat: " + responseObj.getData().getContent());

        if (responseObj == null || responseObj.toString().trim().equals("")) {
            callBack.onFailed("响应为空");
            return;
        }
        //getCode校验
        if (responseObj.getCode() == 1) {
            //URL校验
            if (url.equals(URL.CHAT_URL)) {
                //getMsg校验
                if (responseObj.getMsg().equals("success")) {
                    //getData校验
                    if (responseObj.getData() != null)
                        callBack.onSuccess(responseObj.getData());
                    else
                        callBack.onFailed("数据为空");
                } else {
                    callBack.onFailed(responseObj.getMsg());
                }
            } else {
                callBack.onFailed(responseObj.getMsg());
            }
        } else {
            callBack.onFailed(responseObj.getMsg() + "code错误");
        }
    }
}
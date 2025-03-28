package com.example.module.chat.base.other;

import android.util.Log;

import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.example.module.chat.base.database.BaseResponse;
import com.example.module.chat.base.database.communicate.Data;
import com.example.module.chat.base.database.communicate.MyResponse;
import com.example.module.chat.base.database.select.DataItem;
import com.example.module.chat.base.database.select.HistoryResponse;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.aword.aWordHelper;
import com.xuptggg.libnetwork.exception.MyHttpException;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import java.util.List;

public class NetworkHelper {
    private static NetworkHelper instance;
    private static final String TAG = "NetworkHelper.chat";
//    String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjotMTc5MjEyODg3MDU3MzQwNDE2MCwidXNlcm5hbWUiOiJ1c2VybmFtZSIsImV4cCI6MTc3MzE0NTMwNywiaXNzIjoiTEJES0lORyJ9.1TvcabhhEOMPf7TIDdV-4M2PwIv6IAiuLx18Q96PIwA";
    String apiKey = null;
    private NetworkHelper() {
    }

    public static synchronized NetworkHelper getInstance() {
        if (instance == null) {
            instance = new NetworkHelper();
        }
        return instance;
    }
    public void performPostRequest(String url, RequestParams params, LoadTasksCallBack<List<Data>> callBack) {
        RequestParams mToken = new RequestParams();
        mToken.put("Authorization", "Bearer " + apiKey);
        Log.d(TAG, "performPostRequest: " + "apiKey=" + apiKey);
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, "performPostRequest: " + "onSuccess");
                handleMyResponse(url, (MyResponse) responseObj, callBack);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, "performPostRequest: " + "onFailure");
                if (reasonObj != null) {
                    if (reasonObj instanceof MyHttpException) {
                        callBack.onFailed(((MyHttpException) reasonObj).getErrorMessage());
                    } else {
                        callBack.onFailed(reasonObj.toString());
                    }
                }
            }
        }, MyResponse.class);

        MyOkHttpClient.post(MyRequest.PostRequest(url, params, mToken), handle);
    }
    public void performDataGetRequest(String url, RequestParams params, LoadTasksCallBack<List<Data>> callBack) {
        RequestParams mToken = new RequestParams();
        mToken.put("Authorization", "Bearer " + apiKey);
        Log.d(TAG, "performDataGetRequest: " + "apiKey=" + apiKey);
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, "performDataGetRequest: " + "onSuccess");
                handleMyResponse(url, (MyResponse) responseObj, callBack);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, "performDataGetRequest: " + "onFailure");
                if (reasonObj != null) {
                    if (reasonObj instanceof MyHttpException) {
                        callBack.onFailed(((MyHttpException) reasonObj).getErrorMessage());
                    } else {
                        callBack.onFailed(reasonObj.toString());
                    }
                }
            }
        }, MyResponse.class);

        MyOkHttpClient.get(MyRequest.GetRequest(url, params, mToken), handle);
    }
    public void performGetRequest(String url, RequestParams params, LoadTasksCallBack<List<DataItem>> callBack) {
        RequestParams mToken = new RequestParams();
        mToken.put("Authorization", "Bearer " + apiKey);
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, "performGetRequest: " + "onSuccess");
                handleMyResponse(url, (HistoryResponse) responseObj, callBack);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, "performGetRequest: " + "onFailure");
                if (reasonObj != null) {
                    if (reasonObj instanceof MyHttpException) {
                        callBack.onFailed(((MyHttpException) reasonObj).getErrorMessage());
                    } else {
                        callBack.onFailed(reasonObj.toString());
                    }
                }
            }
        }, HistoryResponse.class);

        MyOkHttpClient.get(MyRequest.GetRequest(url, params, mToken), handle);
    }

    private <T> void handleMyResponse(String url, BaseResponse<T> responseObj, LoadTasksCallBack<T> callBack) {
        Log.d(TAG, "Response in apiKey: " + apiKey);
        Log.d(TAG, "Response in chat: " + responseObj);
        Log.d(TAG, "Response in chat:code= " + responseObj.getCode());
        if (responseObj.getData() != null) {
            Log.d(TAG, "Response in chat: code= " + responseObj.getData().toString());
            Log.d(TAG, "Response in chat: content= " + responseObj.getData());
        }
        else
            Log.d(TAG, "Response in chat: " + responseObj.getMsg() + "data==null");

        if (responseObj == null || responseObj.toString().trim().equals("")) {
            callBack.onFailed("响应为空");
            return;
        }
        Log.e(TAG, "-------------- " );
        // 统一校验 code
        if (responseObj.getCode() != 1) {
            callBack.onFailed(responseObj.getMsg() + " (错误码: " + responseObj.getCode() + ")");
            return;
        }
        //getCode校验
        if (responseObj.getCode() == 1) {
            //URL校验
            if (url.equals(URL.CHAT_URL)) {
                //getMsg校验
                if (responseObj.getMsg().equals("success")) {
                    //getData校验
                    if (responseObj.getData() != null) {
                        Log.d(TAG, "handleMyResponse: " + "getData校验成功");
                        callBack.onSuccess(responseObj.getData());
                    }
                    else {
                        callBack.onFailed("数据为空");
                    }
                } else {
                    callBack.onFailed(responseObj.getMsg());
                }
            } else if (url.equals(URL.CHAT_HISTORY_URL)|| url.equals(URL.CHAT_HISTORYS_URL)) {
                if (responseObj.getMsg().equals("success")) {
//                    //getData校验
                    if (responseObj.getData() != null) {
                        Log.d(TAG, "handleMyResponse: " + "getData校验成功");
                        callBack.onSuccess(responseObj.getData());
                    }
                    else
                        callBack.onFailed("历史聊天数据为空");
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
    public void setToken(String token) {
        this.apiKey = token;
    }
}
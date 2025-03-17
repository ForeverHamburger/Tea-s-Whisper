package com.xuptggg.module.login.base;

import android.util.Log;

import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;
import com.xuptggg.module.login.base.LoadTasksCallBack;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkHelper {

    private static final String TAG = "NetworkHelper";

    public void performPostRequest(String url, RequestParams params, LoadTasksCallBack<String> callBack) {
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                handleResponse(url, responseObj, callBack);
            }

            @Override
            public void onFailure(Object reasonObj) {
                callBack.onFailed(reasonObj.toString());
            }
        });

        MyOkHttpClient.post(MyRequest.PostRequest(url, params), handle);
    }

    private void handleResponse(String url, Object responseObj, LoadTasksCallBack<String> callBack) {
        Log.d(TAG, "Response in login: " + responseObj);

        if (responseObj == null || responseObj.toString().trim().equals("")) {
            callBack.onFailed("响应为空");
            return;
        }

        try {
            JSONObject json = new JSONObject(responseObj.toString());
            int code = json.optInt("code", -1);
            String msg = json.optString("msg", "未知错误");
            JSONObject data = json.optJSONObject("data");
            String token = data.optString("token");

            Log.d(TAG, "handleResponse: " + code);

            if (code == 1) {
                if (url.equals(URL.LOGIN_CODE_URL)) {
                    if (msg.equals("success"))
                        callBack.onSuccess("验证码发送成功");
                    else
                        callBack.onFailed(msg);
                } else if (url.equals(URL.LOGIN_SIGNUP_URL)) {
                    callBack.onSuccess("注册成功");
                } else if (url.equals(URL.LOGIN_LOGIN_URL)) {
                    callBack.onSuccess(token);
                }
                else if (url.equals(URL.LOGIN_FORGET_URL)) {
                    if (msg.equals("success"))
                        callBack.onSuccess("验证码发送成功");
                    else
                        callBack.onFailed(msg);
                }

            } else {
                callBack.onFailed(msg);
            }
        } catch (JSONException e) {
            callBack.onFailed("JSON 解析错误: " + e.getMessage());
        }
    }
}
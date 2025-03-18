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
                Log.d(TAG, "performPostRequest: onSuccess");
                handleResponse(url, responseObj, callBack);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, "performPostRequest: onFailure");
                String errorMsg = (reasonObj != null) ? reasonObj.toString() : "未知网络错误";
                callBack.onFailed(errorMsg);
            }
        });

        MyOkHttpClient.post(MyRequest.PostRequest(url, params), handle);
    }

    private void handleResponse(String url, Object responseObj, LoadTasksCallBack<String> callBack) {
        Log.d(TAG, "Response in login: " + responseObj);

        if (responseObj == null || responseObj.toString().trim().isEmpty()) {
            callBack.onFailed("服务器返回空响应");
            return;
        }
        try {
            JSONObject json = new JSONObject(responseObj.toString());
            int code = json.optInt("code", -1);
            String msg = json.optString("msg", "未知错误");
            JSONObject data = json.optJSONObject("data");
            Log.d(TAG, "handleResponse: code=" + code + ", msg=" + msg);

            if (code != 1) {
                callBack.onFailed(msg);
                return;
            }

            if (msg.equals("success")) {
                if (url.equals(URL.LOGIN_CODE_URL)) {
                    callBack.onSuccess("验证码发送成功");
                } else if (url.equals(URL.LOGIN_FORGET_URL)) {
                    callBack.onSuccess("密码修改成功");
                } else if (url.equals(URL.LOGIN_SIGNUP_URL)) {
                    callBack.onSuccess("注册成功");
                } else if (url.equals(URL.LOGIN_LOGIN_URL)||url.equals(URL.LOGIN_VERIFY_URL)) {
                    if (data == null) {
                        callBack.onFailed("服务器未返回 token");
                        return;
                    }
                    String token = data.optString("token", "");
                    if (token.isEmpty()) {
                        callBack.onFailed("token 为空");
                    } else {
                        callBack.onSuccess(token);
                    }
                } else {
                    callBack.onFailed("未知请求类型");
                }
            } else {
                callBack.onFailed(msg);
            }

        } catch (JSONException e) {
            String errorMsg = "JSON 解析失败: " + e.getMessage() + "\n原始数据: " + responseObj;
            Log.e(TAG, errorMsg);
            callBack.onFailed(errorMsg);
        }
    }
}
package com.xuptggg.module.login.VerifyLogin;

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

public class VerifyLoginModel implements VerifyLoginContract.Model {
    private static final String TAG = "VerifyLoginModel";

    @Override
    public void getVerifyLoginInfo(String email, String verificationCode, LoadTasksCallBack callBack) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("verificationCode", verificationCode);
        performPostRequest(URL.LOGIN_SIGNUP_URL, params, callBack);
    }

    @Override
    public void getVerificationCode(String email, LoadTasksCallBack callBack) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        performPostRequest(URL.LOGIN_CODE_URL, params, callBack);
    }

    private void performPostRequest(String url, RequestParams params, LoadTasksCallBack<String> callBack) {
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                handleResponse(url,responseObj, callBack);
            }

            @Override
            public void onFailure(Object reasonObj) {
                callBack.onFailed(reasonObj.toString());
            }


        });

        MyOkHttpClient.post(MyRequest.PostRequest(url, params), handle);
    }

    private void handleResponse(String url,Object responseObj, LoadTasksCallBack<String> callBack) {
        Log.d(TAG, "Response: " + responseObj);

        if (responseObj == null || responseObj.toString().trim().equals("")) {
            callBack.onFailed("响应为空");
            return;
        }

        try {
            JSONObject json = new JSONObject(responseObj.toString());
            int code = json.optInt("code", -1);
            String msg = json.optString("msg", "未知错误");

            if (code == 1) { // 假设 code == 1 表示成功
                if(url.equals(URL.LOGIN_CODE_URL)){
                    callBack.onSuccess("验证码发送成功");
                }else if (url.equals(URL.LOGIN_SIGNUP_URL)){
                    callBack.onSuccess("注册成功");
                }
            } else {
                callBack.onFailed(msg);
            }
        } catch (JSONException e) {
            callBack.onFailed("JSON 解析错误: " + e.getMessage());
        }
    }
}
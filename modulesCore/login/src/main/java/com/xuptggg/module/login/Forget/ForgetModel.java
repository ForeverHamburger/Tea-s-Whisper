package com.xuptggg.module.login.Forget;

import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.request.RequestParams;
import com.xuptggg.module.login.base.LoadTasksCallBack;
import com.xuptggg.module.login.base.NetworkHelper;

public class ForgetModel implements ForgetContract.Model {

    private final NetworkHelper networkHelper = new NetworkHelper();
    @Override
    public void getForgetInfo(String email, String verificationCode, String password, String repassword, LoadTasksCallBack<String> callBack) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        params.put("re_password", repassword);
        params.put("verificationcode", verificationCode);
        networkHelper.performPostRequest(URL.LOGIN_FORGET_URL, params, callBack);
    }

    @Override
    public void getVerificationCode(String email, LoadTasksCallBack<String> callBack) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        networkHelper.performPostRequest(URL.LOGIN_CODE_URL, params, callBack);
    }
}
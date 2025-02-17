package com.xuptggg.module.login.Register;

import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.request.RequestParams;
import com.xuptggg.module.login.base.LoadTasksCallBack;
import com.xuptggg.module.login.base.NetworkHelper;

public class RegisterModel implements RegisterContract.Model {

    private final NetworkHelper networkHelper = new NetworkHelper();

    @Override
    public void getRegisterInfo(String email, String password, String phone, String verificationCode, LoadTasksCallBack callBack) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        params.put("phone", phone);
        params.put("verificationcode", verificationCode);

        networkHelper.performPostRequest(URL.LOGIN_SIGNUP_URL, params, callBack);
    }

    @Override
    public void getVerificationCode(String email, LoadTasksCallBack callBack) {
        RequestParams params = new RequestParams();
        params.put("email", email);

        networkHelper.performPostRequest(URL.LOGIN_CODE_URL, params, callBack);
    }
}
package com.xuptggg.module.login.Register;


import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;
import com.xuptggg.module.login.base.LoadTasksCallBack;

public class RegisterModel implements RegisterContract.Model{
    @Override
    public void getRegisterInfo(String email, String password, String phone, String verificationCode, LoadTasksCallBack callBack) {

        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        params.put("username", phone);
        params.put("verificationCode", verificationCode);

        // 创建数据处理对象，并传入自定义的 MyDataListener 来处理请求结果
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onSuccess 方法
                    callBack.onSuccess((String) responseObj);
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailed("no");
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
//                // 调用 LoadTasksCallBack 的 onFailed 方法
//                callBack.onFailed();
                if (reasonObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onSuccess 方法
                    callBack.onFailed((String) reasonObj);
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailed(reasonObj.toString());
                }
            }
        });

        MyOkHttpClient.post(MyRequest.PostRequest(URL.LOGIN_SIGNUP_URL, params), handle);
    }


    @Override
    public void getVerificationCode(String email,LoadTasksCallBack callBack) {
        //test 接口验证码
        RequestParams params = new RequestParams();
//        params1.put("email", "1120774555@qq.com");
        params.put("email", email);
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onSuccess 方法
                    callBack.onSuccess((String) responseObj);
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailed("no");
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
//                // 调用 LoadTasksCallBack 的 onFailed 方法
//                callBack.onFailed();
                if (reasonObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onSuccess 方法
                    callBack.onFailed((String) reasonObj);
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailed(reasonObj.toString());
                }
            }
        });
        MyOkHttpClient.post(MyRequest.PostRequest(URL.LOGIN_CODE_URL, params), handle);
    }
}

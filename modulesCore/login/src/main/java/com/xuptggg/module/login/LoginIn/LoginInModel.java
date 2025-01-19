package com.xuptggg.module.login.LoginIn;

import com.example.libnetwork.MyOkHttpClient;
import com.example.libnetwork.listener.MyDataHandle;
import com.example.libnetwork.listener.MyDataListener;
import com.example.libnetwork.request.RequestParams;
import com.xuptggg.module.login.base.LoadTasksCallBack;


public class LoginInModel implements LoginInContract.Model {
    @Override
    public void getLoginInInfo(String username, String password, LoadTasksCallBack callBack) {
        // 创建请求参数对象，添加用户名和密码作为参数
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        // 创建数据处理对象，并传入自定义的 MyDataListener 来处理请求结果
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onSuccess 方法
                    callBack.onSuccess((String) responseObj);
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailed();
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                // 调用 LoadTasksCallBack 的 onFailed 方法
                callBack.onFailed();
            }
        });

        // 发起 GET 请求，使用之前封装好的 MyOkHttpClient
        MyOkHttpClient.get("https://api.example.com/login", params, handle);
    }

    public static void getSomeDate() {
        // 这个方法可以用来实现其他数据获取逻辑，比如获取一些额外的信息
    }

}
package com.xuptggg.module.login.LoginIn;

import com.xuptggg.module.login.base.LoadTasksCallBack;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;
import org.json.JSONObject;


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
                if (responseObj instanceof JSONObject) {
                    // 将响应结果转换为 JSONObject 类型并传递给 LoadTasksCallBack 的 onSuccess 方法
                    callBack.onSuccess( responseObj.toString());
                } else {
                    // 如果响应结果不是 JSONObject 类型，视为请求失败
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
        //test 接口验证码
        RequestParams params1 = new RequestParams();
        params1.put("email", "1120774555@qq.com");
        System.out.println("params1 = " + params1.urlParams);
        MyOkHttpClient.post(MyRequest.PostRequest(URL.LOGIN_CODE_URL, params1), handle);
    }

    public static void getSomeDate() {
        // 这个方法可以用来实现其他数据获取逻辑，比如获取一些额外的信息
    }

}
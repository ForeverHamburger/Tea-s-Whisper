package com.xuptggg.libnetwork.response;


import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.xuptggg.libnetwork.exception.MyHttpException;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
// 自定义的回调类，实现了 okhttp3.Callback 接口，用于处理网络请求的结果
public class JsonCallback implements Callback {

    // 逻辑层异常相关的常量定义，这些常量可以根据不同的应用程序进行调整
    protected final String RESULT_CODE = "ecode"; // 有返回则对于 http 请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    // 代码层异常相关的常量定义，用于区分不同类型的异常
    protected final int NETWORK_ERROR = -1; // 网络错误
    protected final int JSON_ERROR = -2; // json 错误
    protected final int OTHER_ERROR = -3; // 其他

    // Handler 用于将其他线程的数据转发到 UI 线程
    private Handler mHandler;
    // 用于处理请求结果的监听器
    private MyDataListener mListener;
    // 期望将网络请求返回的数据转换为的目标 Java 类类型
    private Class<?> mClass;

    // 构造函数，接收一个 MyDataHandle 对象，从中获取监听器和期望的数据类型
    public JsonCallback(MyDataHandle handle) {
        // 获取 MyDataHandle 中的监听器
        this.mListener = handle.mListener;
        // 获取期望的数据类型
        this.mClass = handle.mClass;
        // 创建一个与主线程关联的 Handler，用于将结果切换到 UI 线程
        this.mHandler = new Handler(Looper.getMainLooper());
    }
    private boolean isValidJson(String json) {
        try {
            new JSONObject(json);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
    // 当网络请求失败时的回调方法
    @Override
    public void onFailure(final Call call, final IOException ioexception) {
        // 由于此回调发生在非 UI 线程，使用 Handler 将结果转发到 UI 线程
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 调用监听器的 onFailure 方法，并将网络错误封装成 MyHttpException 传递
                mListener.onFailure(new MyHttpException(NETWORK_ERROR, ioexception + "-网络错误"));
            }
        });
    }

    // 当网络请求成功时的回调方法
    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        // 获取响应体的内容并存储在 result 中
        final String result = response.body().string();
        // 使用 Handler 将结果转发到 UI 线程
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 调用 handleResponse 方法处理响应结果
                handleResponse(result);
            }
        });
    }

    // 处理响应结果的方法
    private void handleResponse(Object responseObj) {
        // 如果响应对象为空或为空字符串，认为请求失败，调用监听器的 onFailure 方法，传递网络错误
        if (responseObj == null || responseObj.toString().trim().equals("")) {
            mListener.onFailure(new MyHttpException(NETWORK_ERROR, EMPTY_MSG+"-网络错误"));
            return;
        }

        try {
            // 将响应结果转换为 JSONObject
            JSONObject result = new JSONObject(responseObj.toString());
            // 如果没有指定期望的数据类型，直接将结果以 JSONObject 形式传递给监听器的 onSuccess 方法
            if (mClass == null) {
                mListener.onSuccess(result);
            } else {
                // 使用 Gson 将结果转换为期望的数据类型
                Object obj = new Gson().fromJson(responseObj.toString(), mClass);
                // 如果转换成功，将转换后的对象传递给监听器的 onSuccess 方法
                if (obj!= null) {
                    mListener.onSuccess(obj);
                } else {
                    // 转换失败，调用监听器的 onFailure 方法，传递 JSON 错误
                    mListener.onFailure(new MyHttpException(JSON_ERROR, EMPTY_MSG+"-JSON 错误"));
                }
            }
        } catch (Exception e) {
            // 出现其他异常，调用监听器的 onFailure 方法，将异常信息封装成 MyHttpException 传递
            mListener.onFailure(new MyHttpException(OTHER_ERROR, e.getMessage() + "-其他错误"));
            e.printStackTrace();
        }
    }
}
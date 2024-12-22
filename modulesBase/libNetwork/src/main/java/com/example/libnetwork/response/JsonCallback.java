package com.example.libnetwork.response;


import android.os.Handler;
import android.os.Looper;

import com.example.libnetwork.exception.MyHttpException;
import com.example.libnetwork.listener.MyDataHandle;
import com.example.libnetwork.listener.MyDataListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class JsonCallback implements Callback {

    //逻辑层异常，可能在不同的应用程序中改变

    protected final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    //代码层异常，与逻辑错误不同

    protected final int NETWORK_ERROR = -1; // 网络错误
    protected final int JSON_ERROR = -2; // json错误
    protected final int OTHER_ERROR = -3; // 其他

    //将其它线程的数据转发到UI线程

    private Handler mHandler;
    private MyDataListener mListener;
    private Class<?> mClass;

    public JsonCallback(MyDataHandle handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(final Call call, final IOException ioexception) {
        //此时还在非UI线程，因此要转发
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new MyHttpException(NETWORK_ERROR, ioexception));
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        final String result = response.body().string();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(Object responseObj) {
        if (responseObj == null || responseObj.toString().trim().equals("")) {
            mListener.onFailure(new MyHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        try {

            JSONObject result = new JSONObject(responseObj.toString());
            if (mClass == null) {
                mListener.onSuccess(result);
            } else {
                Object obj = new Gson().fromJson(responseObj.toString(), mClass);
                if (obj != null) {
                    mListener.onSuccess(obj);
                } else {
                    mListener.onFailure(new MyHttpException(JSON_ERROR, EMPTY_MSG));
                }
            }
        } catch (Exception e) {
            mListener.onFailure(new MyHttpException(OTHER_ERROR, e.getMessage()));
            e.printStackTrace();
        }
    }
}
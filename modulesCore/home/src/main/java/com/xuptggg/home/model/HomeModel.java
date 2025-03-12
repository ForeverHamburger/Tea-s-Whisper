package com.xuptggg.home.model;

import android.util.Log;

import com.google.gson.Gson;
import com.xuptggg.home.contract.IHomeContract;
import com.xuptggg.home.model.infos.TeaInfo;
import com.xuptggg.home.model.jsonutil.JsonParser;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Response;

public class HomeModel implements IHomeContract.IHomeModel<String> {
    private final static String TAG = "HomeModel";
    @Override
    public void execute(String data, LoadHomeInfoCallBack callBack) {
        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, "onSuccess: " + responseObj);

                List<TeaInfo> teaInfos = JsonParser.parseTeaInfoList((JSONObject)responseObj);
                Log.d(TAG, "onSuccess: " + teaInfos);
                callBack.onSuccess(teaInfos);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, "onFailure: " + reasonObj);
                if (reasonObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onSuccess 方法
                    callBack.onFailed();
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailed();
                }
            }
        });

        Log.d(TAG, "execute: " + "开始请求");
        MyOkHttpClient.get(MyRequest.GetRequest(URL.TEA_DETAIL_URL,null),myDataHandle);
    }
}

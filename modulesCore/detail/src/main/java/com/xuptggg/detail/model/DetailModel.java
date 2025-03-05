package com.xuptggg.detail.model;

import android.util.Log;

import com.xuptggg.detail.contract.IDetailContract;
import com.xuptggg.detail.model.infos.DetailInfo;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import org.json.JSONObject;

import java.util.List;

public class DetailModel implements IDetailContract.IDetailModel<String> {
    private static final String TAG = "DetailModel";
    @Override
    public void execute(String data, LoadDetailInfoCallBack callBack) {
        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, "onSuccess: " + responseObj);
                DetailInfo teaInfos = JsonParser.parseTeaInfoList((JSONObject)responseObj);
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
        MyOkHttpClient.get(MyRequest.GetRequest(URL.TEA_DETAIL_URL,new RequestParams("name",data)),myDataHandle);
    }
}

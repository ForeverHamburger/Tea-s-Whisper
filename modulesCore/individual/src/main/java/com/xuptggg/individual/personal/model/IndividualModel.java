package com.xuptggg.individual.personal.model;


import android.util.Log;

import com.google.gson.Gson;
import com.xuptggg.individual.personal.contract.IIndividualContract;
import com.xuptggg.individual.personal.util.JsonParser;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import org.json.JSONObject;

public class IndividualModel implements IIndividualContract.IIndividualModel<String> {
    @Override
    public void execute(String data, LoadIndividualInfoCallBack callBack) {
        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                IndividualInfo individualInfo = JsonParser.parseForumJson(responseObj.toString());
                Log.d("IndividualModel", "onSuccess: " + individualInfo);
                callBack.onSuccess(individualInfo);
            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onSuccess 方法
                    callBack.onFailed();
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailed();
                }
            }
        });

        RequestParams mToken = new RequestParams();
        mToken.put("Authorization", "Bearer " + data);

        MyOkHttpClient.get(MyRequest.GetRequest(URL.INDIVIDUAL_GET_URL,null,mToken),myDataHandle);
    }
}

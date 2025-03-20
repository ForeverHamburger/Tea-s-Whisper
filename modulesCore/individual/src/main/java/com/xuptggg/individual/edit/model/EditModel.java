package com.xuptggg.individual.edit.model;

import android.util.Log;

import com.xuptggg.individual.edit.contract.IEditContract;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.personal.util.JsonParser;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import org.json.JSONObject;

public class EditModel implements IEditContract.IEditModel {
    @Override
    public void execute(Object data, LoadEditInfoCallBack callBack) {
        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                IndividualInfo individualInfo = JsonParser.parseForumJson(responseObj.toString());
                Log.d("TAG", "onSuccess: " + individualInfo);
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

    @Override
    public void postInfo(Object data, BaseIndividualInfo info, LoadPostEditCallBack callBack) {
        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                IndividualInfo individualInfo = JsonParser.parseForumJson(responseObj.toString());
                Log.d("TAG", "onSuccess: " + individualInfo);
                callBack.onPostSuccess(individualInfo);
            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onSuccess 方法
                    callBack.onFailedPost();
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailedPost();
                }
            }
        });

        RequestParams mToken = new RequestParams();
        mToken.put("Authorization", "Bearer " + data);

        MyOkHttpClient.get(MyRequest.GetRequest(URL.INDIVIDUAL_GET_URL,null,mToken),myDataHandle);
    }
}

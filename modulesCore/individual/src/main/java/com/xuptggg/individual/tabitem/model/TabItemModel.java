package com.xuptggg.individual.tabitem.model;


import android.util.Log;

import com.xuptggg.individual.personal.contract.IIndividualContract;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.personal.model.LoadIndividualInfoCallBack;
import com.xuptggg.individual.personal.util.JsonParser;
import com.xuptggg.individual.tabitem.contract.ITabItemContract;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import java.util.List;

public class TabItemModel implements ITabItemContract.ITabItemModel<String> {
    private final static String TAG = "TabItemModel";
    @Override
    public void execute(String data,String info, LoadTabItemCallBack callBack) {
        Log.d(TAG, "execute: " + "开始请求");
        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, "onSuccess: " + responseObj);
                List<ForumInfo> forumList = JsonParser.parseForumJson(responseObj.toString());
                callBack.onSuccess(forumList);
            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj instanceof String) {
                    Log.d(TAG, "onFailure: " + reasonObj);
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

        if (info.equals("我的")) {
            MyOkHttpClient.get(MyRequest.GetRequest(URL.INDIVIDUAL_TABITEM_GETMY_URL,null,mToken),myDataHandle);
        } else if (info.equals("赞过")) {
            MyOkHttpClient.get(MyRequest.GetRequest(URL.INDIVIDUAL_TABITEM_GETVOTE_URL,null,mToken),myDataHandle);
        } else if (info.equals("收藏")) {
            MyOkHttpClient.get(MyRequest.GetRequest(URL.INDIVIDUAL_TABITEM_GETCOLLECT_URL,null,mToken),myDataHandle);
        } else {

        }
    }
}

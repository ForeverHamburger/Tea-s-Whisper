package com.xuptggg.forum.square.model;

import android.util.Log;

import com.xuptggg.forum.R;
import com.xuptggg.forum.square.contract.IForumContract;
import com.xuptggg.forum.square.utils.JsonParser;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForumModel implements IForumContract.IForumModel<String> {
    private final static String TAG = "XIXI";

    @Override
    public void execute(String data, LoadForumInfoCallBack callBack) {

        Log.d(TAG, "execute: ");
        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, "onSuccess: ." + responseObj);

                List<ForumInfo> forumList = JsonParser.parseForumJson(responseObj.toString());

                Log.d(TAG, "onSuccess: " + forumList.toArray());
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

        RequestParams requestParams = new RequestParams();
        requestParams.put("page","1");
        requestParams.put("size","12");
        MyOkHttpClient.get(MyRequest.GetRequest(URL.FORUM_SQUARE_URL,requestParams,mToken),myDataHandle);

        callBack.onStart();
    }
}

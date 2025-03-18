package com.xuptggg.forum.thread.model;

import android.util.Log;

import com.xuptggg.forum.R;
import com.xuptggg.forum.square.model.ForumInfo;
import com.xuptggg.forum.square.utils.JsonParser;
import com.xuptggg.forum.thread.contract.IThreadContract;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class ThreadModel implements IThreadContract.IThreadModel<String> {
    @Override
    public void execute(String data,String postId, LoadThreadCallBack callBack) {

        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d("TAG", "onSuccess: " + responseObj);
                callBack.onSuccess(responseObj);
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

        RequestParams requestParams = new RequestParams();
        requestParams.put("post_id",postId);

        MyOkHttpClient.get(MyRequest.GetRequest(URL.FORUM_SQUARE_URL,requestParams,mToken),myDataHandle);
    }
}

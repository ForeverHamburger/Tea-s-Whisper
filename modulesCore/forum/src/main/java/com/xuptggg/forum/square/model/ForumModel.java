package com.xuptggg.forum.square.model;

import android.util.Log;

import com.xuptggg.forum.R;
import com.xuptggg.forum.square.contract.IForumContract;
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

        MyOkHttpClient.get(MyRequest.GetRequest(URL.FORUM_SQUARE_URL,null),myDataHandle);


        // 造一点数据
        List<ForumInfo> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new ForumInfo(ForumInfo.NOTE, R.drawable.dog_head,"硅基龙吟穿透大气层 碳基文明以茶汤解码"
                    ,R.drawable.dog_head,"奶龙大王","15.1k"));
            list.add(new ForumInfo(ForumInfo.NOTE, R.drawable.dog_head,"当奶龙的声浪漫过银河 地球在茶盏里泛起涟漪"
                    ,R.drawable.dog_head,"奶龙小王","20.1k"));
            list.add(new ForumInfo(ForumInfo.NOTE, R.drawable.dog_head,"奶龙的量子触须轻点盖碗 宇宙在茶沫中完成第一次握手"
                    ,R.drawable.dog_head,"奶龙long王","1.1k"));
            list.add(new ForumInfo(ForumInfo.NOTE, R.drawable.dog_head,"当外星分贝超过90赫兹 我们教会奶龙听雨煮茶"
                    ,R.drawable.dog_head,"奶龙美王","12.1k"));
            list.add(new ForumInfo(ForumInfo.NOTE, R.drawable.dog_head,"奶龙第七舰队着陆武夷山 岩骨花香是最强的电磁屏障"
                    ,R.drawable.dog_head,"奶龙舞王","23.1k"));
        }
        callBack.onSuccess(list);
    }
}

package com.xuptggg.libnetwork.aword;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.exception.MyHttpException;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class aWordHelper {
    private static final String API_URL = "https://v1.hitokoto.cn/?c=i&encode=json";
    private static final String TAG = "aWordHelper";
    private static final String BASE_URL = "https://v1.hitokoto.cn/";
    private static aWordHelper instance;
    private aWord.aWordMain latestData = new aWord.aWordMain(
            "壶畔空杯承露久，青山倒映已无痕。",
            "i",
            "《行香子（清夜无尘）》",
            "苏轼");;

    private aWordHelper() {
    }

    public static synchronized aWordHelper getInstance() {
        if (instance == null) {
            instance = new aWordHelper();
        }
        return instance;
    }

    public aWord.aWordMain getLatestData() {
        return latestData;
    }
    public void fetchaWord(LoadTasksCallBack<aWord.aWordMain> callBack) {
        RequestParams params = new RequestParams();
        params.put("c", "i"); // 类型参数
        params.put("encode", "json"); // 返回格式为 JSON
        MyDataHandle handle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, "fetchaWord: onSuccess");
                handleaWordResponse((aWord) responseObj, callBack);
            }
            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, "fetchaWord: onFailure");
                if (reasonObj != null) {
                    Log.d(TAG, "fetchaWord: no null");
                    if (reasonObj instanceof MyHttpException) {
                        callBack.onFailed(((MyHttpException) reasonObj).getErrorMessage());
                    } else {
                        Log.d(TAG, reasonObj.toString());
                        callBack.onFailed(reasonObj.toString());
                    }
                }
            }
        }, aWord.class);

        MyOkHttpClient.get(MyRequest.GetRequest(BASE_URL, params), handle);
    }

    private void handleaWordResponse(aWord responseObj, LoadTasksCallBack<aWord.aWordMain> callBack) {
        Log.d(TAG, "Response in aWord: " + responseObj);
        if (responseObj == null) {
            callBack.onSuccess(latestData);
            return;
        }

        // 检查响应数据
        if (responseObj.getHitokoto() != null) {
            Log.d(TAG, "handleaWordResponse: 数据校验成功");
            latestData = responseObj.getMain();
            callBack.onSuccess(latestData);
        } else {
            callBack.onSuccess(latestData);
        }
    }
    public void fetchaWordBest(LoadTasksCallBack<aWord.aWordMain> callBack) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        // 使用异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: ");
                new Handler(Looper.getMainLooper()).post(() -> {
                    callBack.onSuccess(latestData);
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    Log.d(TAG, "onResponse: " + json);
                    Gson gson = new Gson();
                    aWord.aWordMain word = gson.fromJson(json, aWord.class).getMain();
                    latestData= word;
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Log.d(TAG, "onHandler: " + word.toString());
                        callBack.onSuccess(word);
                    });
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        callBack.onSuccess(latestData);
                    });
                }
            }
        });
    }
}
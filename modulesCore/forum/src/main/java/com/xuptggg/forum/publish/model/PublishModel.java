package com.xuptggg.forum.publish.model;

import com.google.gson.Gson;
import com.xuptggg.forum.publish.contract.IPublishContract;
import com.xuptggg.libnetwork.MyOkHttpClient;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.listener.MyDataListener;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PublishModel implements IPublishContract.IPublishModel<String> {
    @Override
    public void getUriFromFile(List<File> files, String token, LoadImageUriCallBack callBack) {
        uploadFiles(files, 0, token, callBack);
    }

    private void uploadFiles(final List<File> files, final int index, final String token, final LoadImageUriCallBack callBack) {
        List<String> imageUriList = new ArrayList<>();

        if (index >= files.size()) {
            // 所有文件都上传成功
            callBack.onSuccess(imageUriList);
            return;
        }

        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                    String imageUri = jsonObject.optString("data");
                    imageUriList.add(imageUri);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                // 继续上传下一个文件
                uploadFiles(files, index + 1, token, callBack);
            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onFailed 方法
                    callBack.onFailed();
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailed();
                }
            }
        });

        RequestParams mToken = new RequestParams();
        mToken.put("Authorization", "Bearer " + token);

        RequestParams requestParams = new RequestParams();

        try {
            requestParams.put("file", files.get(index));
        } catch (FileNotFoundException e) {
            callBack.onFailed();
            return;
        }

        MyOkHttpClient.get(MyRequest.TestMultiPostRequest(URL.PICTURE_UPLOAD_URL, requestParams, mToken), myDataHandle);
    }

    @Override
    public void publishThread(PublishInfo publishInfo, String token, LoadImageUriCallBack callBack) {
        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onFailed 方法
                    callBack.onFailed();
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onFailed();
                }
            }
        });

        RequestParams mToken = new RequestParams();
        mToken.put("Authorization", "Bearer " + token);

        RequestParams requestParams = new RequestParams();
        requestParams.put("content",publishInfo.getPublishContent());
        requestParams.put("status",publishInfo.getStatus());
        requestParams.put("title",publishInfo.getPublishTitle());
        Gson gson = new Gson();
        requestParams.put("url",gson.toJson(publishInfo.toString()));


        MyOkHttpClient.get(MyRequest.PostRequest(URL.FORUM_PUBLISH_URL, requestParams, mToken), myDataHandle);
    }
}

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

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
                callBack.onPostSuccess("个人信息更新成功");
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

        RequestParams requestParams = new RequestParams();
        requestParams.put("introduction",info.getIntroduction());
        requestParams.put("sex",info.getSex());
        requestParams.put("url",info.getUrl());
        requestParams.put("username",info.getUsername());

        Log.d("EditModel", "postInfo: " + info);

        MyOkHttpClient.post(MyRequest.PostRequest(URL.INDIVIDUAL_EDIT_POST_URL,requestParams,mToken),myDataHandle);
    }

    @Override
    public void getUriFromFile(File files, String token, LoadImageUriCallBack callBack) {
        MyDataHandle myDataHandle = new MyDataHandle(new MyDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                try {
                    JSONObject jsonObject = new JSONObject(responseObj.toString());
                    String imageUri = jsonObject.optString("data");
                    callBack.onUploadSuccess(imageUri);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj instanceof String) {
                    // 将响应结果转换为 String 类型并传递给 LoadTasksCallBack 的 onFailed 方法
                    callBack.onUploadFailed();
                } else {
                    // 如果响应结果不是 String 类型，视为请求失败
                    callBack.onUploadFailed();
                }
            }
        });

        RequestParams mToken = new RequestParams();
        mToken.put("Authorization", "Bearer " + token);

        RequestParams requestParams = new RequestParams();

        try {
            requestParams.put("file", files);
        } catch (FileNotFoundException e) {
            callBack.onUploadFailed();
            return;
        }

        MyOkHttpClient.get(MyRequest.TestMultiPostRequest(URL.PICTURE_UPLOAD_URL, requestParams, mToken), myDataHandle);
    }
}

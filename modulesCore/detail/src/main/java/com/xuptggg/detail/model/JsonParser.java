package com.xuptggg.detail.model;

import android.util.Log;

import com.google.gson.Gson;
import com.xuptggg.detail.model.infos.DetailInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    private static final String TAG = "JsonParser";
    public static DetailInfo parseTeaInfoList(JSONObject json) {
        try {
            Log.d(TAG, "parseTeaInfoList: " + json);
            // 从 JSONObject 中提取 "data" 字段对应的 JSONArray
            JSONObject dataArray = json.getJSONObject("data");
            Gson gson = new Gson();
            // 将该 JSON 对象转换为字符串
            String teaInfoJsonString = dataArray.toString();
            Log.d(TAG, "parseTeaInfoList: " + dataArray);
            // 使用 Gson 将 JSON 字符串解析为 TeaInfo 对象
            DetailInfo teaInfo = gson.fromJson(teaInfoJsonString, DetailInfo.class);
            return teaInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
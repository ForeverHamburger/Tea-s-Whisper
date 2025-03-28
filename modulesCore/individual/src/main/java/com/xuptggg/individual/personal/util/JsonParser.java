package com.xuptggg.individual.personal.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.tabitem.model.ForumInfo;
import com.xuptggg.individual.tabitem.model.ForumResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static IndividualInfo parseIndividualJson(String json){
        Log.d("JsonParser", "parseForumJson: " + json);
        try {
            JSONObject jsonString = new JSONObject(json);
            JSONObject jsonObject = jsonString.optJSONObject("data");
            Gson gson = new Gson();
            IndividualInfo individualInfo = gson.fromJson(String.valueOf(jsonObject), IndividualInfo.class);
            return individualInfo;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null; // 异常处理
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ForumInfo> parseForumJson(String json) {
        try {
            Gson gson = new Gson();

            ForumResponse response = gson.fromJson(json, ForumResponse.class);

            // 直接返回列表（自动类型转换）
            return response != null ? response.getForumList() : new ArrayList<>();

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return new ArrayList<>(); // 异常处理
        }
    }
}
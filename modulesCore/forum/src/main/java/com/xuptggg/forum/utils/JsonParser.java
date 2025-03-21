package com.xuptggg.forum.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xuptggg.forum.square.model.ForumInfo;
import com.xuptggg.forum.square.model.ForumResponse;
import com.xuptggg.forum.thread.model.ThreadInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
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

    public static ThreadInfo parseThreadJson(String json) {
        try {
            JSONObject jsonOb = new JSONObject(json);
            JSONObject jsonObject = jsonOb.optJSONObject("data");

            Gson gson = new Gson();
            ThreadInfo response = gson.fromJson(String.valueOf(jsonObject), ThreadInfo.class);

            return response;

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null; // 异常处理
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
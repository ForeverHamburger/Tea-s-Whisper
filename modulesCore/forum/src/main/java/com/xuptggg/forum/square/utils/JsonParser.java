package com.xuptggg.forum.square.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.xuptggg.forum.square.model.ForumInfo;
import com.xuptggg.forum.square.model.ForumResponse;

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
}
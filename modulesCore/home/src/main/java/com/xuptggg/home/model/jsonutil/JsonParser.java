package com.xuptggg.home.model.jsonutil;

import com.google.gson.Gson;
import com.xuptggg.home.model.infos.TeaInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<TeaInfo> parseTeaInfoList(JSONObject json) {
        List<TeaInfo> teaInfoList = new ArrayList<>();
        try {
            // 从 JSONObject 中提取 "data" 字段对应的 JSONArray
            JSONArray dataArray = json.getJSONArray("data");
            Gson gson = new Gson();
            // 遍历 JSONArray 中的每个元素
            for (int i = 0; i < dataArray.length(); i++) {
                // 获取 JSONArray 中的每个元素（即每个茶叶信息的 JSON 对象）
                JSONObject teaInfoJson = dataArray.getJSONObject(i);
                // 将该 JSON 对象转换为字符串
                String teaInfoJsonString = teaInfoJson.toString();
                // 使用 Gson 将 JSON 字符串解析为 TeaInfo 对象
                TeaInfo teaInfo = gson.fromJson(teaInfoJsonString, TeaInfo.class);
                // 将解析后的 TeaInfo 对象添加到列表中
                teaInfoList.add(teaInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return teaInfoList;
    }
}
package com.xuptggg.individual.personal.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xuptggg.individual.personal.model.IndividualInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {
    public static IndividualInfo parseForumJson(String json){
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
}
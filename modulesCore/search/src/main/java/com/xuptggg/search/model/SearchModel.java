package com.xuptggg.search.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;
import com.xuptggg.search.R;
import com.xuptggg.search.base.NetworkHelper;
import com.xuptggg.search.base.SearchHistoryUtils;
import com.xuptggg.search.contract.ISearchContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchModel implements ISearchContract.ISearchModel<String> {
    private final NetworkHelper networkHelper = NetworkHelper.getInstance();

    @Override
    public void execute(String data, LoadSearchInfoCallBack callBack) {

    }

    @Override
    public void getToken(String token) {
        networkHelper.setToken(token);
    }

    @Override
    public List<String> getTeaSearchCommend() {
        List<String> list = new ArrayList<>();
        list.add("西湖龙井");
        list.add("信阳毛尖");
        list.add("碧螺春");
        list.add("黄山毛峰");
        list.add("六安瓜片");
        list.add("祁门红茶");
        list.add("安溪铁观音");
        list.add("武夷岩茶");
        return list;
    }

    @Override
    public List<TeaShowInfo> getTeaShow() {
        List<TeaShowInfo> teaShowInfos = new ArrayList<>();
        teaShowInfos.add(new TeaShowInfo("杀青", R.drawable.tea_item2));
        teaShowInfos.add(new TeaShowInfo("萎凋",R.drawable.tea_item2));
        teaShowInfos.add(new TeaShowInfo("渥堆",R.drawable.tea_item2));
        return teaShowInfos;
    }

    @Override
    public List<String> getTeaSearchHistory() {
        return SearchHistoryUtils.getSearchHistory();
    }
}

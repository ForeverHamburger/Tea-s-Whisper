package com.xuptggg.search.model;

import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.xuptggg.libnetwork.request.RequestParams;
import com.xuptggg.search.base.NetworkHelper;
import com.xuptggg.search.contract.ResultContract;
import java.util.ArrayList;
import java.util.List;
public class ResultModel implements ResultContract.Model {
    private final NetworkHelper networkHelper = NetworkHelper.getInstance();


    @Override
    public void getResultInfo(String content, String tag, String size, LoadTasksCallBack callBack) {
        RequestParams params = new RequestParams();
        params.put("content", content);
        params.put("tag", tag);
        params.put("size", size);
        networkHelper.performGetRequest(URL.SEARCH_URL, params, callBack);

    }

    @Override
    public void getToken(String token) {
        networkHelper.setToken(token);
    }


}
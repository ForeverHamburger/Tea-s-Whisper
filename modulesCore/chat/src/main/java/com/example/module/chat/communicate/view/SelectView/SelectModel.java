package com.example.module.chat.communicate.view.SelectView;

import com.example.module.chat.R;
import com.example.module.chat.base.database.select.Agent;
import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.example.module.chat.base.other.NetworkHelper;
import com.xuptggg.libnetwork.URL;
import com.xuptggg.libnetwork.request.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class SelectModel implements SelectContract.Model {
    private final NetworkHelper networkHelper = new NetworkHelper();

    @Override
    public void getSelectInfo(String content, String sessionId, LoadTasksCallBack callBack) {
        RequestParams params = new RequestParams();
        params.put("content", content);
        params.put("session_id", sessionId);

        networkHelper.performGetRequest(URL.CHAT_HISTORY_URL, params, callBack);
    }

    @Override
    public void getHistoryListInfo(LoadTasksCallBack callBack) {
        networkHelper.performGetRequest(URL.CHAT_HISTORYS_URL, null, callBack);
    }

    @Override
    public void getToken(String token) {
        networkHelper.setToken(token);
    }

    @Override
    public List<Agent> getAgents() {
        List<Agent> agents = new ArrayList<>();
        agents.add(new Agent("0", "奶龙1大王", R.drawable.dog, "一个可以让你开心的奶龙大王啊"));
        agents.add(new Agent("1", "奶龙2大王", R.drawable.dog, "2个可以让你开心的奶龙大王啊"));
        agents.add(new Agent("2", "奶龙3大王", R.drawable.dog, "3个可以让你开心的奶龙大王啊"));
        agents.add(new Agent("3", "奶龙4大王", R.drawable.dog, "4个可以让你开心的奶龙大王啊"));
        return agents;
    }

}
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
    private final NetworkHelper networkHelper = NetworkHelper.getInstance();

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
        agents.add(new Agent("0", "陆羽", R.drawable.pic_luyu, "悦来客满是茶香"));
        agents.add(new Agent("1", "卢仝", R.drawable.pic_lutong, "二碗破孤闷"));
        agents.add(new Agent("2", "汪士慎", R.drawable.pic_wss, "胸中清苦味"));
        agents.add(new Agent("3", "张岱", R.drawable.pic_zhangdai, "茶淫枯虐"));
        return agents;
    }

}
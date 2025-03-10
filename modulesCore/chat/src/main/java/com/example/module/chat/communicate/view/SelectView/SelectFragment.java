package com.example.module.chat.communicate.view.SelectView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.module.chat.R;
import com.example.module.chat.base.database.select.Agent;
import com.example.module.chat.base.database.select.DataItem;
import com.example.module.chat.communicate.recycleviewUtil.ChatCommunicateAdapter;
import com.example.module.chat.communicate.recycleviewUtil.ChatSelectAgentAdapter;
import com.example.module.chat.communicate.recycleviewUtil.ChatSelectHistoryAdapter;
import com.example.module.chat.databinding.FragmentSelectBinding;

import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;

public class SelectFragment extends Fragment implements SelectContract.View {
    public FragmentSelectBinding binding;
    private SelectContract.Presenter mPresenter;
    private ChatSelectHistoryAdapter historyAdapter;
    private ChatSelectAgentAdapter agentAdapter;
    private Markwon markwon;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSelectBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        markwon = Markwon.create(requireContext());
        mPresenter.getHistoryDataInfo();
        historyAdapter = new ChatSelectHistoryAdapter(markwon);
        agentAdapter = new ChatSelectAgentAdapter();
        binding.rvAgents.setAdapter(agentAdapter);
        binding.rvHistory.setAdapter(historyAdapter);

    }


    @Override
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }

    @Override
    public void displayHistoryData(List<DataItem> data, String isOk) {
        if (isOk == null) {
            historyAdapter.addAllHistoryDataList(data);
            List<Agent> agents = new ArrayList<>();
            agents.add(new Agent("0","奶龙1大王", R.drawable.dog,"一个可以让你开心的奶龙大王啊"));
            agents.add(new Agent("1","奶龙2大王", R.drawable.dog,"2个可以让你开心的奶龙大王啊"));
            agents.add(new Agent("2","奶龙3大王", R.drawable.dog,"3个可以让你开心的奶龙大王啊"));
            agents.add(new Agent("3","奶龙4大王", R.drawable.dog,"4个可以让你开心的奶龙大王啊"));
            agentAdapter.addAllMessageDataList(agents);
        } else {
            historyAdapter.addWithError(data, isOk);
        }

    }

    @Override
    public void setPresenter(SelectContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
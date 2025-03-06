package com.example.module.chat.communicate.view.SelectView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.module.chat.communicate.recycleviewUtil.ChatSelectAgentAdapter;
import com.example.module.chat.communicate.recycleviewUtil.ChatSelectHistoryAdapter;
import com.example.module.chat.databinding.FragmentSelectBinding;

public class SelectFragment extends Fragment implements SelectContract.View {
    public FragmentSelectBinding binding;
    private SelectContract.Presenter mPresenter;
    private ChatSelectHistoryAdapter historyAdapter;
    private ChatSelectAgentAdapter agentAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSelectBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyAdapter = new ChatSelectHistoryAdapter();
        agentAdapter = new ChatSelectAgentAdapter();
        binding.rvAgents.setAdapter(agentAdapter);
        binding.rvHistory.setAdapter(historyAdapter);
        mPresenter.getHistoryDataInfo();

    }


    @Override
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }

    @Override
    public void setPresenter(SelectContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
package com.example.module.chat.communicate.view.SelectView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.chat.R;
import com.example.module.chat.base.database.select.Agent;
import com.example.module.chat.base.database.select.DataItem;
import com.example.module.chat.communicate.base.AnimUtil;
import com.example.module.chat.communicate.base.ItemActionListener;
import com.example.module.chat.communicate.recycleviewUtil.ChatSelectAgentAdapter;
import com.example.module.chat.communicate.recycleviewUtil.ChatSelectHistoryAdapter;
import com.example.module.chat.communicate.view.CommunicateActivity;
import com.example.module.chat.databinding.FragmentSelectBinding;
import com.xuptggg.module.libbase.eventbus.TokenManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;
@Route(path = "/chat/SelectFragment")
public class SelectFragment extends Fragment implements SelectContract.View, ItemActionListener<DataItem> {
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        mPresenter.getToken(tokenManager.getToken());
    }

    @Override
    public void onStart() {
        super.onStart();
        // 检查是否已经注册，如果没有注册则进行注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // 取消注册
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SelectPresenter presenter = new SelectPresenter(this, new SelectModel());
        markwon = Markwon.create(requireContext());
//        mPresenter.getHistoryDataInfo();
        historyAdapter = new ChatSelectHistoryAdapter(markwon, this);

        agentAdapter = new ChatSelectAgentAdapter(new ItemActionListener<Agent>() {
            @Override
            public void onItemClick(Agent item, int position) {
                Intent intent = new Intent(requireActivity(), CommunicateActivity.class);
                intent.putExtra("name", item.getName());
                startActivity(intent);
            }

            @Override
            public void onLongClick(Agent item, int position) {
            }
        });
        binding.rvAgents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rvAgents.setAdapter(agentAdapter);
        binding.rvHistory.setAdapter(historyAdapter);
        binding.btnSelectAgent.setOnClickListener(v -> {
            showNewDialogConfirmation();
        });
        binding.btnSelectCancel.setOnClickListener(v -> {
            if (isAnimating) return;
            isAnimating = true;
            if (isExpanded) {
                // 执行收回动画
                AnimUtil.reverseImageTransition(binding.bgOverlay, v, () -> {
                    isAnimating = false;
                    binding.bgOverlay.setVisibility(View.INVISIBLE);
                });
            } else {
                // 执行展开动画
                binding.bgOverlay.setVisibility(View.VISIBLE);
                AnimUtil.startImageTransition(binding.bgOverlay, v, R.drawable.lbd);
                isAnimating = false;
            }
            updateButtonState(!isExpanded);
            isExpanded = !isExpanded;
        });
    }

    private void updateButtonState(boolean expanded) {
        int iconRes = expanded ? R.drawable.olp : R.drawable.lbd;
        binding.btnSelectCancel.setImageResource(iconRes);
    }

    private boolean isExpanded = false;
    private boolean isAnimating = false;

    public void showNewDialogConfirmation() {
        new AlertDialog.Builder(requireContext())
                .setTitle("开启新对话") // 对话框标题
                .setMessage("确定要开启新对话吗？当前对话将关闭。") // 提示信息
                .setPositiveButton("确定", (dialog, which) -> {
                    // 用户确认后替换 Fragment
                    initFragment();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss(); // 关闭对话框
                })
                .create()
                .show();
    }

    private void initFragment() {
        Intent intent = new Intent(requireActivity(), CommunicateActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }

    @Override
    public void displayHistoryData(List<DataItem> data, String errorCode) {
        if (errorCode == null) {
            addAgents();
            historyAdapter.addAllHistoryDataList(data);
        } else {
            addAgents();
            historyAdapter.addWithError(data, errorCode);
        }

    }
    public void addAgents() {
        agentAdapter.addAllMessageDataList(mPresenter.getAgents());
    }
    @Override
    public void showEmptyHistory() {
        addAgents();
        historyAdapter.addWithError(new ArrayList<>(), "error");
    }
    // 实现接口方法
    @Override
    public void onItemClick(DataItem item, int position) {
        Intent intent = new Intent(requireActivity(), CommunicateActivity.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("sessionID", item.getMsg().getSessionID());
        startActivity(intent);
    }

    @Override
    public void onLongClick(DataItem item, int position) {
//        new MaterialAlertDialogBuilder(requireContext())
//                .setItems(R.array.history_options, (d, which) -> {
//                    handleOption(which, item);
//                })
//                .show();
        Log.d("test", "onLongClick: " + position);
    }

    @Override
    public void onDestroyView() {
        // 重置动画状态
        binding.bgOverlay.clearAnimation();
        binding.bgOverlay.setVisibility(View.INVISIBLE);
        super.onDestroyView();
    }

    @Override
    public void setPresenter(SelectContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
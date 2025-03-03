package com.example.module.chat.communicate.view.CommunicateView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.chat.communicate.base.ChatMessage;
import com.example.module.chat.communicate.recycleviewUtil.ChatCommunicateAdapter;
import com.example.module.chat.databinding.FragmentCommunicateBinding;

@Route(path = "/chat/CommunicateFragment")
public class CommunicateFragment extends Fragment implements CommunicateContract.View {
    public FragmentCommunicateBinding binding;
    private CommunicateContract.Presenter mPresenter;
    public ChatCommunicateAdapter adapter;
    public String sessionId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCommunicateBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ChatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatCommunicateAdapter();
//        addMessageDataList
        binding.ChatRecyclerView.setAdapter(adapter);

        binding.ChatEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 在文本变化之前的操作，这里不需要处理
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本发生变化时调用
                if (s.length() > 0) {
                    binding.ChatSend.setColorFilter(Color.BLUE);
                } else {
//                    binding.ChatSend.setColorFilter(Color.GRAY);
                    binding.ChatSend.setImageTintList(ColorStateList.valueOf(Color.GRAY));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 在文本变化之后的操作
            }
        });

        binding.ChatSend.setOnClickListener(v -> {
            String content = binding.ChatEdit.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                sendMessage(content);
                binding.ChatEdit.setText("");
            }
        });
    }

    private void sendMessage(String message) {
        // 添加用户消息
        ChatMessage userMsg = new ChatMessage(ChatMessage.TYPE_SENT, message);
        adapter.addMessageDataList(userMsg);
        binding.ChatRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
        getAIResponse(message);
    }

    private void getAIResponse(String content) {
//        new Thread(() -> {
        mPresenter.getCommunicateInfo(content, sessionId);
        Log.d("CommunicateFragment", "getAIResponse: " + content);
        Log.d("CommunicateFragment", "getAIResponse: " + sessionId);
//        }).start();
    }

    @Override
    public String aiResponse(String content) {
        System.out.println(content + "aiResponse");

        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                // 添加 AI 回复
                ChatMessage aiMsg = new ChatMessage(ChatMessage.TYPE_RECEIVED, content);
                adapter.addMessageDataList(aiMsg);
                binding.ChatRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
            });
        }
        return content;
    }
    @Override
    public void setSessionId(String SessionId) {
        sessionId = SessionId;
    }

    @Override
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }

    @Override
    public void setPresenter(CommunicateContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
package com.example.module.chat.communicate.view.CommunicateView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.chat.communicate.base.ChatMessage;
import com.example.module.chat.communicate.recycleviewUtil.ChatCommunicateAdapter;
import com.example.module.chat.databinding.FragmentCommunicateBinding;

import java.util.List;

import io.noties.markwon.Markwon;

@Route(path = "/chat/CommunicateFragment")
public class CommunicateFragment extends Fragment implements CommunicateContract.View {
    public FragmentCommunicateBinding binding;
    private static final String ARG_TITLE = "title";
    private static final String ARG_SESSION_ID = "sessionID";
    private String title;
    private boolean isFirstLaunch = true; // 标记是否首次启动
    private CommunicateContract.Presenter mPresenter;
    public ChatCommunicateAdapter adapter;
    public String sessionId;
    // 初始化 Markwon
    private Markwon markwon;
    public static CommunicateFragment newInstance(String title, String sessionID) {
        CommunicateFragment fragment = new CommunicateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_SESSION_ID, sessionID);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            sessionId = getArguments().getString(ARG_SESSION_ID);
        }
        binding = FragmentCommunicateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ChatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        markwon = Markwon.create(requireContext());
        adapter = new ChatCommunicateAdapter(markwon);
//        adapter = new ChatCommunicateAdapter();
//        addMessageDataList
        binding.ChatRecyclerView.setAdapter(adapter);
        ViewCompat.setOnApplyWindowInsetsListener(binding.ChatRecyclerView, (v, insets) -> {
            // 当键盘弹出时，自动滚动到底部
            binding.ChatRecyclerView.post(() -> {
                binding.ChatRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
            });
            return insets;
        });
        binding.ChatEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 在文本变化之前的操作，这里不需要处理
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.ChatSend.animate().cancel(); // 取消之前的动画
                if (s.length() > 0) {
                    if(binding.ChatSend.getVisibility() == View.GONE) {
                        // 从右侧滑入
                        binding.ChatSend.setVisibility(View.VISIBLE);
                        binding.ChatSend.setTranslationX(200); // 初始偏移量
                        binding.ChatSend.animate()
                                .translationX(0)
                                .setDuration(400)
                                .setInterpolator(new DecelerateInterpolator())
                                .start();
                    }
                } else {
                    // 向右侧滑出
                    binding.ChatSend.animate()
                            .translationX(200)
                            .setDuration(400)
                            .withEndAction(() -> {
                                binding.ChatSend.setVisibility(View.GONE);
                                binding.ChatSend.setTranslationX(0); // 复位位置
                            })
                            .start();
                }
                // 在文本变化之后的操作
//                binding.ChatSend.animate().cancel();
//                binding.ChatEdit.animate().cancel();
//
//                ConstraintLayout.LayoutParams editParams = (ConstraintLayout.LayoutParams) binding.ChatEdit.getLayoutParams();
//                int originalMargin = editParams.rightMargin; // 原始右侧边距
//
//                if (s.length() > 0) {
//                    // 按钮显示动画（淡入 + 缩放）
//                    binding.ChatSend.setVisibility(View.VISIBLE);
//                    binding.ChatSend.setAlpha(0f);
//                    binding.ChatSend.setScaleX(0.5f);
//                    binding.ChatSend.setScaleY(0.5f);
//                    binding.ChatSend.animate()
//                            .alpha(1f)
//                            .scaleX(1f)
//                            .scaleY(1f)
//                            .setDuration(300)
//                            .setInterpolator(new OvershootInterpolator())
//                            .start();
//
//                    // EditText 缩短动画（边距减小）
//                    ValueAnimator marginAnimator = ValueAnimator.ofInt(originalMargin, 0);
//                    marginAnimator.addUpdateListener(animation -> {
//                        editParams.rightMargin = (int) animation.getAnimatedValue();
//                        binding.ChatEdit.requestLayout();
//                    });
//                    marginAnimator.setDuration(300).start();
//
//                } else {
//                    // 按钮隐藏动画（淡出 + 缩放）
//                    binding.ChatSend.animate()
//                            .alpha(0f)
//                            .scaleX(0.5f)
//                            .scaleY(0.5f)
//                            .setDuration(200)
//                            .setInterpolator(new AccelerateInterpolator())
//                            .withEndAction(() -> binding.ChatSend.setVisibility(View.GONE))
//                            .start();
//
//                    // EditText 伸长动画（边距恢复为按钮宽度）
//                    int targetMargin = binding.ChatSend.getWidth(); // 按钮的宽度作为目标边距
//                    ValueAnimator marginAnimator = ValueAnimator.ofInt(originalMargin, targetMargin);
//                    marginAnimator.addUpdateListener(animation -> {
//                        editParams.rightMargin = (int) animation.getAnimatedValue();
//                        binding.ChatEdit.requestLayout();
//                    });
//                    marginAnimator.setDuration(300).start();
//                }
            }
        });

        binding.ChatSend.setOnClickListener(v -> {
            String content = binding.ChatEdit.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                sendMessage(content);
                binding.ChatEdit.setText("");
            }
        });

        initDefault();
    }

    private void initDefault() {
        if (title != null && !title.isEmpty()) {
            binding.ChatTitle.setText(title);
        } else {
            binding.ChatTitle.setText("新对话");
        }

        if (sessionId != null && !sessionId.isEmpty()) {
            mPresenter.getHistoryInfo(sessionId);
        } else if (isFirstLaunch) {
            showDefaultWelcomeMessage();
            isFirstLaunch = false;
        }
    }

    private void showDefaultWelcomeMessage() {
        String welcomeText = "你好，我是奶龙，一名热衷于中国茶文化的爱好者。我对茶的历史、品种、泡饮方法以及茶艺都有浓厚的兴趣。在我国悠久的茶文化中，我深感茶道不仅是品茗的艺术，更是一种修身养性的生活方式。在接下来的时间里，我很乐意与你探讨关于茶的各种话题。";
        aiResponse(new ChatMessage(ChatMessage.TYPE_RECEIVED, welcomeText));
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
    public void aiResponse(ChatMessage chatMessage) {
        System.out.println(chatMessage.toString() + "aiResponse");

        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                adapter.addMessageDataList(chatMessage);
                binding.ChatRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
            });
        }
    }

    @Override
    public void historyResponse(List<ChatMessage> chatMessageList) {
        System.out.println(chatMessageList.toString() + "historyResponse");
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                // 添加 AI 回复
                adapter.addAllMessageDataList(chatMessageList);
                binding.ChatRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
            });
        }
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
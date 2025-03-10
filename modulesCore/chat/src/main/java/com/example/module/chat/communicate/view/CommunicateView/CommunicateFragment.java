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

import io.noties.markwon.Markwon;

@Route(path = "/chat/CommunicateFragment")
public class CommunicateFragment extends Fragment implements CommunicateContract.View {
    public FragmentCommunicateBinding binding;
    private CommunicateContract.Presenter mPresenter;
    public ChatCommunicateAdapter adapter;
    public String sessionId;
    // 初始化 Markwon
    private Markwon markwon;
    public static CommunicateFragment newInstance(Bundle args) {
        CommunicateFragment fragment = new CommunicateFragment();
        fragment.setArguments(args);
        return fragment;
    }
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
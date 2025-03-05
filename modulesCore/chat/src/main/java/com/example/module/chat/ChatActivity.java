package com.example.module.chat;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.chat.base.SoftKeyBoard;
import com.example.module.chat.communicate.view.CommunicateView.CommunicateFragment;
import com.example.module.chat.communicate.view.CommunicateView.CommunicateModel;
import com.example.module.chat.communicate.view.CommunicateView.CommunicatePresenter;
import com.example.module.chat.databinding.ActivityChatBinding;

@Route(path = "/chat/ChatActivity")
public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        initViews();
    }

    private void initViews() {
        initFragment();
        initListener();
        SoftKeyBoard.assistActivity(this);
    }

    private void initListener() {

    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        CommunicateFragment communicateFragment = (CommunicateFragment) fm.findFragmentById(R.id.fragment_container);
        FragmentTransaction ft = fm.beginTransaction();
        if (communicateFragment == null) {
            communicateFragment = new CommunicateFragment();
        }
        CommunicatePresenter loginInPresenter = new CommunicatePresenter(communicateFragment, new CommunicateModel());
        communicateFragment.setPresenter(loginInPresenter);
        ft.add(R.id.fragment_container, communicateFragment);
        ft.commit();
    }
}
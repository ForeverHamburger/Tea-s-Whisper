package com.example.module.chat;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.chat.base.SoftKeyBoard;
import com.example.module.chat.base.SoftKeyBoard0;
import com.example.module.chat.base.SoftKeyBoard1;
import com.example.module.chat.communicate.view.CommunicateView.CommunicateFragment;
import com.example.module.chat.communicate.view.CommunicateView.CommunicateModel;
import com.example.module.chat.communicate.view.CommunicateView.CommunicatePresenter;
import com.example.module.chat.communicate.view.SelectView.SelectFragment;
import com.example.module.chat.communicate.view.SelectView.SelectModel;
import com.example.module.chat.communicate.view.SelectView.SelectPresenter;
import com.example.module.chat.databinding.ActivityChatBinding;

@Route(path = "/chat/ChatActivity")
public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            getWindow().setDecorFitsSystemWindows(true);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//        // 1. 设置全屏模式（内容延伸到状态栏下方）
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            getWindow().setDecorFitsSystemWindows(false);
//        } else {
//            // 旧版本兼容
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            );
//        }
//
        // 2. 设置状态栏颜色为透明
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//
        // 3. 调整布局避开状态栏（可选）
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, 0); // 顶部避开状态栏高度
            return insets;
        });

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        initViews();
    }

    private void initViews() {
        initFragment();
        initListener();
//        SoftKeyBoard.assistActivity(this);
//        SoftKeyBoard0.assistActivity(this);
//        SoftKeyBoard1.assistActivity(this);
    }

    private void initListener() {

    }

    private void initFragment() {
//        FragmentManager fm = getSupportFragmentManager();
//        SelectFragment selectFragment = (SelectFragment) fm.findFragmentById(R.id.fragment_container);
//        FragmentTransaction ft = fm.beginTransaction();
//        if (selectFragment == null) {
//            selectFragment = new SelectFragment();
//        }
//        SelectPresenter selectPresenter = new SelectPresenter(selectFragment, new SelectModel());
//        selectFragment.setPresenter(selectPresenter);
//        ft.add(R.id.fragment_container, selectFragment);
//        ft.commit();
//
        FragmentManager fm = getSupportFragmentManager();
        CommunicateFragment communicateFragment = (CommunicateFragment) fm.findFragmentById(R.id.fragment_container);
        FragmentTransaction ft = fm.beginTransaction();
        if (communicateFragment == null) {
            communicateFragment = new CommunicateFragment();
        }
        CommunicatePresenter communicatePresenter = new CommunicatePresenter(communicateFragment, new CommunicateModel());
        communicateFragment.setPresenter(communicatePresenter);
        ft.add(R.id.fragment_container, communicateFragment);
        ft.commit();
    }
}
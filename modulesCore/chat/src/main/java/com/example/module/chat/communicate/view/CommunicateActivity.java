package com.example.module.chat.communicate.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.module.chat.R;
import com.example.module.chat.communicate.view.CommunicateView.CommunicateFragment;
import com.example.module.chat.communicate.view.CommunicateView.CommunicateModel;
import com.example.module.chat.communicate.view.CommunicateView.CommunicatePresenter;
import com.example.module.chat.communicate.view.SelectView.SelectFragment;
import com.example.module.chat.communicate.view.SelectView.SelectModel;
import com.example.module.chat.communicate.view.SelectView.SelectPresenter;
import com.example.module.chat.databinding.ActivityChatBinding;
import com.example.module.chat.databinding.ActivityCommunicateBinding;

public class CommunicateActivity extends AppCompatActivity {
    private ActivityCommunicateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_communicate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, 0); // 顶部避开状态栏高度
            return insets;
        });

        binding = ActivityCommunicateBinding.inflate(getLayoutInflater());
        initViews();
    }

    private void initViews() {
        initFragment();
        initListener();
    }

    private void initListener() {

    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        CommunicateFragment communicateFragment = (CommunicateFragment) fm.findFragmentById(R.id.fragment_container);

        if (communicateFragment == null) {
            communicateFragment = new CommunicateFragment();
            CommunicatePresenter presenter = new CommunicatePresenter(communicateFragment, new CommunicateModel());
            communicateFragment.setPresenter(presenter);

            fm.beginTransaction()
                    .replace(R.id.fragment_container, communicateFragment)
                    .commit();
        }
    }
}
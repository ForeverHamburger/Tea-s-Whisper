package com.xuptggg.individual;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xuptggg.individual.R;
import com.xuptggg.individual.personal.base.myviewpageradapter;
import com.xuptggg.individual.databinding.ActivityIndividualBinding;
import com.xuptggg.individual.personal.view.IndividualFragment;
import com.xuptggg.module.libbase.eventbus.TokenManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class IndividualActivity extends AppCompatActivity {
    ActivityIndividualBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIndividualBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViewpager();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 取消注册
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initViewpager() {
        myviewpageradapter adapter = new myviewpageradapter(this);
        adapter.addFragment(IndividualFragment.newInstance(), "文章");
        adapter.addFragment(IndividualFragment.newInstance(), "讨论");
        adapter.addFragment(IndividualFragment.newInstance(), "爱茶");
        binding.ViewPager2.setAdapter(adapter);


        new TabLayoutMediator(binding.TabLayout, binding.ViewPager2, (tab, position) -> {
            tab.setText(adapter.getTabTitle(position));
        }).attach();
    }
}
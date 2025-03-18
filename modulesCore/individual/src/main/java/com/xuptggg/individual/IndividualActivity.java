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


public class IndividualActivity extends AppCompatActivity {
    ActivityIndividualBinding binding;

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
        initView();
        initViewpager();
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

    private void initView() {
        Glide.with(this)
                .load(R.drawable.dog)
                .circleCrop()
                .into(binding.foregroundImageView);
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                float ratio = (float) Math.abs(verticalOffset) / totalScrollRange;
                float alphaTitle = Math.min(1, Math.max(0, ratio));
                float alphaBottomContent = 1 - alphaTitle; // 底部内容透明度与标题相反，形成互补效果
                // 获取标题和底部内容的当前透明度
                float currentAlphaTitle = binding.title.getAlpha();
                float currentAlphaBottomContent = binding.bottomContent.getAlpha();

                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    // 当完全折叠时，隐藏底部内容，显示并完整显示标题
                    binding.bottomContent.setVisibility(View.GONE);
                    binding.foregroundImageView.setVisibility(View.GONE);
                    binding.man.setVisibility(View.VISIBLE);
                    binding.title.setVisibility(View.VISIBLE);
                    binding.title.setAlpha(1f);
                    binding.bottomContent.setAlpha(0f); // 完全透明
                } else {
                    // 当未完全折叠时，显示底部内容和标题，并根据滚动比例设置各自透明度，实现渐变效果
                    binding.man.setVisibility(View.GONE);
                    binding.foregroundImageView.setVisibility(View.VISIBLE);
                    binding.bottomContent.setVisibility(View.VISIBLE);
                    binding.title.setVisibility(View.VISIBLE);
                    binding.title.setAlpha(alphaTitle);
                    binding.bottomContent.setAlpha(alphaBottomContent);
                    binding.foregroundImageView.setAlpha(alphaBottomContent);
                }
                if (currentAlphaTitle!= alphaTitle) {
                    AlphaAnimation alphaAnimationTitle = new AlphaAnimation(currentAlphaTitle, alphaTitle);
                    alphaAnimationTitle.setDuration(0);
                    alphaAnimationTitle.setFillAfter(true);
                    binding.title.startAnimation(alphaAnimationTitle);
                }
                if (currentAlphaBottomContent!= alphaBottomContent) {
                    AlphaAnimation alphaAnimationBottomContent = new AlphaAnimation(currentAlphaBottomContent, alphaBottomContent);
                    alphaAnimationBottomContent.setDuration(0);
                    alphaAnimationBottomContent.setFillAfter(true);
                    binding.bottomContent.startAnimation(alphaAnimationBottomContent);
                }
            }
        });
    }

}
package com.xuptggg.guidepage.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.jem.liquidswipe.clippathprovider.LiquidSwipeClipPathProvider;
import com.xuptggg.guidepage.adapter.SwipePagerAdapter;
import com.xuptggg.guidepage.contract.IGuideContract;
import com.xuptggg.guidepage.databinding.ActivityGuidePageBinding;
import com.xuptggg.guidepage.model.GuideInfo;
import com.xuptggg.guidepage.model.GuideModel;
import com.xuptggg.guidepage.presenter.GuidePresenter;

import java.util.List;

public class GuidePageActivity extends AppCompatActivity implements IGuideContract.IGuideView {
    private ActivityGuidePageBinding binding;
    private IGuideContract.IGuidePresenter mPresenter;
    private LiquidSwipeClipPathProvider[] liquidSwipeClipPathProviders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityGuidePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setPresenter(new GuidePresenter(this, new GuideModel()));
        mPresenter.getGuideInfo("Guide");

        binding.vpGuide.setOffscreenPageLimit(4);

        binding.vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    binding.btnGuideGotologin.setVisibility(View.VISIBLE);
                } else {
                    binding.btnGuideGotologin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void setPresenter(IGuideContract.IGuidePresenter presenter) {
        mPresenter = presenter;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void showGuideInfomation(List<GuideInfo> guideInfos) {
        liquidSwipeClipPathProviders = new LiquidSwipeClipPathProvider[5];
        for (int i = 0; i < 5; i++) {
            liquidSwipeClipPathProviders[i] = new LiquidSwipeClipPathProvider();
        }
        SwipePagerAdapter swipePagerAdapter = new SwipePagerAdapter(this,liquidSwipeClipPathProviders
                ,guideInfos);
        binding.vpGuide.setAdapter(swipePagerAdapter);

        binding.vpGuide.setOnTouchListener((v, event) -> {
            float waveCenterY = event.getY();
            for (LiquidSwipeClipPathProvider provider : liquidSwipeClipPathProviders) {
                provider.setWaveCenterY(waveCenterY);
            }
            return false;
        });
    }

    @Override
    public void showError() {

    }
}
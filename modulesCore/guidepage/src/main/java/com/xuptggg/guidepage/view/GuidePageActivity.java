package com.xuptggg.guidepage.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jem.liquidswipe.clippathprovider.LiquidSwipeClipPathProvider;
import com.xuptggg.guidepage.R;
import com.xuptggg.guidepage.adapter.SwipePagerAdapter;
import com.xuptggg.guidepage.contract.IGuideContract;
import com.xuptggg.guidepage.databinding.ActivityGuidePageBinding;
import com.xuptggg.guidepage.model.GuideInfo;
import com.xuptggg.guidepage.model.GuideModel;
import com.xuptggg.guidepage.presenter.GuidePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuidePageActivity extends AppCompatActivity implements IGuideContract.IGuideView {
    private ActivityGuidePageBinding binding;

    private IGuideContract.IGuidePresenter mPresenter;
    private List<Integer> backgroundColorArray;
    private List<Integer> resourceArray;
    private List<String> titleArray;
    private LiquidSwipeClipPathProvider[] liquidSwipeClipPathProviders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityGuidePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setPresenter(new GuidePresenter(this, new GuideModel()));
        mPresenter.getGuideInfo("Guide");


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
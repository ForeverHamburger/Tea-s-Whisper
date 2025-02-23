package com.xuptggg.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.home.R;
import com.xuptggg.home.model.TeaInfo;
import com.xuptggg.home.view.TeaCardAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/home/HomeFragment")
public class HomeFragment extends Fragment {

    private Banner<Integer, BannerImageAdapter<Integer>> banner;
    private RecyclerView recyclerView;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // 初始化轮播图
        initBanner(view);

        // 初始化 RecyclerView
        initRecyclerView(view);

        return view;
    }


    private void initBanner(View view) {
        banner = view.findViewById(R.id.banner);
        List<Integer> bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.tea_posters1);
        bannerImages.add(R.drawable.tea_posters2);

        banner.setAdapter(new BannerImageAdapter<Integer>(bannerImages) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                        holder.imageView.setImageResource(data);
                    }
                })
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(requireContext()));
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<TeaInfo> teaInfoList = new ArrayList<>();
        teaInfoList.add(new TeaInfo("龙井茶叶", "浙江杭州", R.drawable.img));
        teaInfoList.add(new TeaInfo("碧螺春", "江苏苏州", R.drawable.img));
        teaInfoList.add(new TeaInfo("铁观音", "福建安溪", R.drawable.img));

        TeaCardAdapter adapter = new TeaCardAdapter(teaInfoList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int childCount = recyclerView.getChildCount();
                int width = recyclerView.getWidth();
                int padding = (width - recyclerView.getChildAt(0).getWidth()) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                    } else {
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                    }
                    v.setScaleX(1 - rate * 0.3f);
                    v.setScaleY(1 - rate * 0.3f);
                }
            }
        });
    }
}
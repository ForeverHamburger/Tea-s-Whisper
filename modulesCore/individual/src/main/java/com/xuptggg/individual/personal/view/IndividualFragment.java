package com.xuptggg.individual.personal.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xuptggg.individual.R;
import com.xuptggg.individual.databinding.FragmentIndividualBinding;
import com.xuptggg.individual.personal.view.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/individual/IndividualFragment")
public class IndividualFragment extends Fragment {

    private FragmentIndividualBinding binding;

    public IndividualFragment() {
        // Required empty public constructor
    }

    public static IndividualFragment newInstance() {
        IndividualFragment fragment = new IndividualFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIndividualBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // ViewPager2与TabLayout绑定 与 数据初始化
        List<TabFragment> fragmentList = new ArrayList<>();

        fragmentList.add(new TabFragment());
        fragmentList.add(new TabFragment());
        fragmentList.add(new TabFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), fragmentList);
        binding.vpMy.setAdapter(viewPagerAdapter);
        binding.vpMy.setOffscreenPageLimit(3);

        new TabLayoutMediator(binding.tabMy, binding.vpMy, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                if (i == 0) {
                    tab.setText("我的");
                } else if (i == 1) {
                    tab.setText("赞过");
                } else {
                    tab.setText("收藏");
                }
            }
        }).attach();

        binding.editInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/individual/EditActivity").navigation();
            }
        });
    }
}
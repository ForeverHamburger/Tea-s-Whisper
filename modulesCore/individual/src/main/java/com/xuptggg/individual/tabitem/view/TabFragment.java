package com.xuptggg.individual.tabitem.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuptggg.individual.databinding.FragmentTabBinding;
import com.xuptggg.individual.personal.model.ForumInfo;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.tabitem.contract.ITabItemContract;
import com.xuptggg.individual.tabitem.model.TabItemModel;
import com.xuptggg.individual.tabitem.presenter.TabItemPresenter;
import com.xuptggg.individual.tabitem.view.adapter.WaterFallAdapter;
import com.xuptggg.module.libbase.eventbus.TokenManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment implements ITabItemContract.ITabItemView {
    private FragmentTabBinding binding;
    private ITabItemContract.ITabItemPresenter mPresenter;
    private String tabTag;
    public TabFragment(String tabTag) {
        this.tabTag = tabTag;
    }

    public static TabFragment newInstance(String tabTag) {
        TabFragment fragment = new TabFragment(tabTag);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPresenter(new TabItemPresenter(this,new TabItemModel()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        Log.d("TabFragment", "getTokenIndividual: " + tokenManager.getToken());
        mPresenter.getTabItem(tabTag,tokenManager.getToken());
    }

    @Override
    public void onStop() {
        super.onStop();
        // 取消注册
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void setPresenter(ITabItemContract.ITabItemPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showTabItemInfomation(List<com.xuptggg.individual.tabitem.model.ForumInfo> forumInfoList) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rvIndividualTabItem.setLayoutManager(layoutManager);
        WaterFallAdapter waterFallAdapter = new WaterFallAdapter(forumInfoList,getActivity());
        binding.rvIndividualTabItem.setAdapter(waterFallAdapter);
    }

    @Override
    public void showError() {

    }
}
package com.xuptggg.search.view.Result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xuptggg.module.libbase.eventbus.TokenManager;
import com.xuptggg.search.R;
import com.xuptggg.search.contract.ResultContract;

import com.xuptggg.search.databinding.FragmentResultBinding;
import com.xuptggg.search.model.ResultModel;
import com.xuptggg.search.presenter.ResultPresenter;
import com.xuptggg.search.view.Search.SearchFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultFragment extends Fragment implements ResultContract.View<String>{
    private FragmentResultBinding binding;
    private ResultContract.Presenter mPresenter;


    public ResultFragment() {
    }

    public static ResultFragment newInstance(String content) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString("CONTENT", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        mPresenter.getToken(tokenManager.getToken());
        Log.d("ResultFragment", "getToken: " + tokenManager.getToken());
//        mPresenter.getResultInfo(getArguments().getString("CONTENT"),);
        if (getArguments() != null){
            initView(getArguments().getString("CONTENT"));
        }
    }

    private void initView(String content) {
        TabLayout tabLayout = binding.TabLayout;
        ViewPager2 viewPager =binding.viewPager2;

        List<String> tabTitles = Arrays.asList("用户", "内容", "户用","容内","其他");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(initUserFragment());
        fragmentList.add(initArticleFragment());
        fragmentList.add(initUserFragment());
        fragmentList.add(initArticleFragment());
        fragmentList.add(initArticleFragment());

        MyPagerAdapter adapter = new MyPagerAdapter(requireActivity(),fragmentList);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabTitles.get(position));
        }).attach();

    }

    private Fragment initUserFragment() {
            ResultUserFragment resultUserFragment = ResultUserFragment.newInstance(getArguments().getString("CONTENT"));
            ResultPresenter presenter = new ResultPresenter(resultUserFragment, new ResultModel());
            resultUserFragment.setPresenter(presenter);
            return resultUserFragment;
    }
    private Fragment initArticleFragment() {
        ResultArticleFragment resultArticleFragment = ResultArticleFragment.newInstance(getArguments().getString("CONTENT"));
        ResultPresenter presenter = new ResultPresenter(resultArticleFragment, new ResultModel());
        resultArticleFragment.setPresenter(presenter);
        return resultArticleFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    public void setPresenter(ResultContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }

    @Override
    public void showResult(List<String> data) {

    }

}
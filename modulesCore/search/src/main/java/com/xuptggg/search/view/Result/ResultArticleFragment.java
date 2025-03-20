package com.xuptggg.search.view.Result;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.xuptggg.search.databinding.FragmentResultArticleBinding;
import com.xuptggg.search.databinding.FragmentResultUserBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

public class ResultArticleFragment extends Fragment implements ResultContract.View{
    private ResultContract.Presenter mPresenter;
    private FragmentResultArticleBinding binding;

    public ResultArticleFragment() {

    }
    public static ResultArticleFragment newInstance(String content) {
        ResultArticleFragment fragment = new ResultArticleFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultArticleBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void setPresenter(ResultContract.Presenter presenter) {
        mPresenter = presenter;
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        mPresenter.getToken(tokenManager.getToken());
        Log.d("ResultFragment", "getToken: " + tokenManager.getToken());
//        mPresenter.getResultInfo(getArguments().getString("CONTENT"),);
        if (getArguments() != null){
            mPresenter.getResultInfo(getArguments().getString("CONTENT"),"User","3");
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // 检查是否已经注册，如果没有注册则进行注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }
}
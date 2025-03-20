package com.xuptggg.forum.thread.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuptggg.forum.R;
import com.xuptggg.forum.thread.contract.IThreadContract;
import com.xuptggg.forum.thread.model.ThreadInfo;
import com.xuptggg.forum.thread.model.ThreadModel;
import com.xuptggg.forum.thread.presenter.ThreadPresenter;
import com.xuptggg.module.libbase.eventbus.TokenManager;
import com.youth.banner.indicator.CircleIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ThreadFragment extends Fragment implements IThreadContract.IThreadView {
    private IThreadContract.IThreadPresenter mPresenter;
    private String postid;

    public ThreadFragment() {
        // Required empty public constructor
    }

    public static ThreadFragment newInstance() {
        ThreadFragment fragment = new ThreadFragment();
        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postid = getArguments().getString("postid");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thread, container, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        mPresenter.getThreadInfo(tokenManager.getToken(),postid);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPresenter(new ThreadPresenter(this,new ThreadModel()));
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
    public void setPresenter(IThreadContract.IThreadPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showThreadInfomation(ThreadInfo threadInfo) {

//        binding.bannerNotePage.setAdapter(new BannerAdapter(list))
//                .addBannerLifecycleObserver(this)
//                .setIndicator(new CircleIndicator(this));
    }

    @Override
    public void showError() {

    }
}
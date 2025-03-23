package com.xuptggg.search.view.Result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuptggg.module.libbase.eventbus.TokenManager;
import com.xuptggg.search.R;
import com.xuptggg.search.base.data.User;
import com.xuptggg.search.contract.ResultContract;
import com.xuptggg.search.databinding.FragmentResultBinding;
import com.xuptggg.search.databinding.FragmentResultUserBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ResultUserFragment extends Fragment implements ResultContract.View<User>{
    private ResultContract.Presenter<User> mPresenter;
    private FragmentResultUserBinding binding;

    public ResultUserFragment() {

    }

    public static ResultUserFragment newInstance(String content) {
        ResultUserFragment fragment = new ResultUserFragment();
        Bundle args = new Bundle();
        args.putString("CONTENT", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showError() {

    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }

    @Override
    public void showResult(List<User> data) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultUserBinding.inflate(inflater);
        return binding.getRoot();
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        mPresenter.getToken(tokenManager.getToken());
        Log.d("ResultFragment", "getToken: " + tokenManager.getToken());
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (getArguments() != null) {
                    mPresenter.getResultInfo(getArguments().getString("CONTENT"),"user","3");
                }
            }
        });
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
    public void setPresenter(ResultContract.Presenter<User> presenter) {
        mPresenter = presenter;
    }

}
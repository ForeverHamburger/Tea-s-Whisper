package com.xuptggg.search.view.Result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xuptggg.module.libbase.eventbus.TokenManager;
import com.xuptggg.search.R;
import com.xuptggg.search.base.data.Post;
import com.xuptggg.search.contract.ResultContract;
import com.xuptggg.search.databinding.FragmentResultArticleBinding;
import com.xuptggg.search.databinding.FragmentResultUserBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

public class ResultArticleFragment extends Fragment implements ResultContract.View<Post> {
    private ResultContract.Presenter<Post> mPresenter;
    private FragmentResultArticleBinding binding;
    private MultiTypeAdapter<Post> mAdapter;

    public ResultArticleFragment() {
        // 默认构造函数
    }

    public static ResultArticleFragment newInstance(String content) {
        ResultArticleFragment fragment = new ResultArticleFragment();
        Bundle args = new Bundle();
        args.putString("CONTENT", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showError() {
        // 显示错误信息
        Toast.makeText(getContext(), "加载失败，请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Boolean isACtive() {
        return isAdded();
    }

    @Override
    public void showResult(List<Post> data) {
        // 显示文章数据
        mAdapter.addAllDataList(data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化适配器
        mAdapter = new MultiTypeAdapter<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultArticleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 设置 RecyclerView
        binding.rvArticleList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rvArticleList.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getToken(TokenManager tokenManager) {
        // 获取 Token 并请求数据
        mPresenter.getToken(tokenManager.getToken());
        Log.d("ResultArticleFragment", "getToken: " + tokenManager.getToken());
        new Handler(Looper.getMainLooper()).post(() -> {
            if (getArguments() != null) {
                mPresenter.getResultInfo(getArguments().getString("CONTENT"), "post", "3");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册 EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // 取消注册 EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void setPresenter(ResultContract.Presenter<Post> presenter) {
        mPresenter = presenter;
    }
}
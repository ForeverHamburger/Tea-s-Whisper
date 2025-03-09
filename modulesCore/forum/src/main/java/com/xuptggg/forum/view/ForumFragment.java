package com.xuptggg.forum.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.forum.R;
import com.xuptggg.forum.contract.IForumContract;
import com.xuptggg.forum.databinding.FragmentForumBinding;
import com.xuptggg.forum.model.ForumInfo;
import com.xuptggg.forum.model.ForumModel;
import com.xuptggg.forum.presenter.ForumPresenter;
import com.xuptggg.forum.view.adapters.WaterFallAdapter;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

@Route(path = "/forum/ForumFragment")
public class ForumFragment extends Fragment implements IForumContract.IForumView{
    private FragmentForumBinding binding;
    private IForumContract.IForumPresenter mPresenter;
    private String requestType = "default";

    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance() {
        ForumFragment fragment = new ForumFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPresenter(new ForumPresenter(this,new ForumModel()));

        mPresenter.getForumInfo(requestType);

        PtrFrameLayout ptrFrame = binding.ptrFrame;
        MaterialHeader materialHeader = new MaterialHeader(getActivity());
        materialHeader.setPadding(0, PtrLocalDisplay.dp2px(35),0,0);

        ptrFrame.setHeaderView(materialHeader);
        ptrFrame.addPtrUIHandler(materialHeader);
        ptrFrame.disableWhenHorizontalMove(true);

        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 刷新逻辑
                ptrFrame.refreshComplete();
            }
        });
    }

    @Override
    public void setPresenter(IForumContract.IForumPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showForumInfomation(List<ForumInfo> forumInfos) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rvWaterFall.setLayoutManager(layoutManager);
        WaterFallAdapter waterFallAdapter = new WaterFallAdapter(forumInfos,getActivity());
        binding.rvWaterFall.setAdapter(waterFallAdapter);
    }

    @Override
    public void showError() {

    }
}
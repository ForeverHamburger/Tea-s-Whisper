package com.xuptggg.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.search.R;
import com.xuptggg.search.databinding.FragmentSearchBinding;
import com.xuptggg.search.model.TeaShowInfo;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/search/SearchFragment")
public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<TeaShowInfo> teaShowInfos = new ArrayList<>();
        teaShowInfos.add(new TeaShowInfo("杀青",R.drawable.tea_item2));
        teaShowInfos.add(new TeaShowInfo("萎凋",R.drawable.tea_item2));
        teaShowInfos.add(new TeaShowInfo("渥堆",R.drawable.tea_item2));

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rvTeaShow.setLayoutManager(layoutManager);

        TeaShowAdapter teaShowAdapter= new TeaShowAdapter(teaShowInfos);
        binding.rvTeaShow.setAdapter(teaShowAdapter);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        binding.rvTeaCommendSearch.setLayoutManager(layoutManager1);

        List<String> list = new ArrayList<>();
        list.add("西湖龙井");
        list.add("信阳毛尖");
        list.add("碧螺春");
        list.add("黄山毛峰");
        list.add("六安瓜片");
        list.add("祁门红茶");
        list.add("安溪铁观音");
        list.add("武夷岩茶");


        TeaSearchCommendAdapter teaSearchCommendAdapter = new TeaSearchCommendAdapter(list);
        binding.rvTeaCommendSearch.setAdapter(teaSearchCommendAdapter);
    }
}
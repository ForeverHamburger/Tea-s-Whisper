package com.xuptggg.individual.tabitem.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuptggg.individual.databinding.FragmentTabBinding;
import com.xuptggg.individual.personal.model.ForumInfo;
import com.xuptggg.individual.tabitem.view.adapter.WaterFallAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {
    private FragmentTabBinding binding;

    public TabFragment() {
        // Required empty public constructor
    }

    public static TabFragment newInstance(String param1, String param2) {
        TabFragment fragment = new TabFragment();
        return fragment;
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

        List<ForumInfo> forumInfos = new ArrayList<>();

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rvIndividualTabItem.setLayoutManager(layoutManager);
        WaterFallAdapter waterFallAdapter = new WaterFallAdapter(forumInfos,getActivity());
        binding.rvIndividualTabItem.setAdapter(waterFallAdapter);
    }
}
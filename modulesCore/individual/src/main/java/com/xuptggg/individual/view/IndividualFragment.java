package com.xuptggg.individual.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.individual.R;

@Route(path = "/Individual/IndividualFragment")
public class IndividualFragment extends Fragment {


    public IndividualFragment() {
        // Required empty public constructor
    }


    public static IndividualFragment newInstance(String param1, String param2) {
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
        return inflater.inflate(R.layout.fragment_individual, container, false);
    }
}
package com.xuptggg.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuptggg.home.R;

public class TeaDetailFragment extends Fragment {

    public TeaDetailFragment() {
        // Required empty public constructor
    }
    public static TeaDetailFragment newInstance(String param1, String param2) {
        TeaDetailFragment fragment = new TeaDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tea_detail, container, false);
    }
}
package com.xuptggg.forum.thread.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuptggg.forum.R;

public class ThreadFragment extends Fragment {

    public ThreadFragment() {
        // Required empty public constructor
    }

    public static ThreadFragment newInstance() {
        ThreadFragment fragment = new ThreadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thread, container, false);
    }


}
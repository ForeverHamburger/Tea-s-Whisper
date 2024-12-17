package com.xuptggg.guidepage.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuptggg.guidepage.R;
import com.xuptggg.guidepage.databinding.FragmentDummyBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DummyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DummyFragment extends Fragment {

    private static final String ARG_BACKGROUND_COLOR = "param1";
    private static final String ARG_RESOURCE = "param2";
    private static final String ARG_TITLE = "param3";

    private Integer param1;
    private Integer param2;
    private String param3;
    private FragmentDummyBinding binding;

    public DummyFragment() {

    }

    public static DummyFragment newInstance(int param1, int param2, String param3) {
        DummyFragment fragment = new DummyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BACKGROUND_COLOR, param1);
        args.putInt(ARG_RESOURCE, param2);
        args.putString(ARG_TITLE, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getInt(ARG_BACKGROUND_COLOR);
            param2 = getArguments().getInt(ARG_RESOURCE);
            param3 = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDummyBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(param1 != null ? param1 : Color.RED);

//        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottieAnimationView);
//        lottieAnimationView.setAnimation(param2 != null ? param2 : R.raw.mountain);
//        lottieAnimationView.repeatCount(LottieDrawable.INFINITE);
//        lottieAnimationView.repeatMode(LottieDrawable.REVERSE);
//        lottieAnimationView.playAnimation();
        binding.tvDummyContent.setText(param3);
    }
}
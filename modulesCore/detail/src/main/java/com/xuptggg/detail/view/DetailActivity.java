package com.xuptggg.detail.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.detail.R;
import com.xuptggg.detail.databinding.ActivityDetailBinding;
import com.xuptggg.detail.utils.AnimationUtils;
@Route(path = "/detail/DetailActivity")
public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private boolean isLiked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ctlDetail.setTitle("西湖龙井");

        binding.fabDetailLike.setOnClickListener(v -> {
            if (!isLiked) {
                AnimationUtils.performLikeAnimation(this, binding.fabDetailLike);
                isLiked = true;
            } else {
                AnimationUtils.performUnlikeAnimation(this, binding.fabDetailLike);
                isLiked = false;
            }
        });


    }
}
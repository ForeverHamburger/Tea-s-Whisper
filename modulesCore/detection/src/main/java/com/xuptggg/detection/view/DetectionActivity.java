package com.xuptggg.detection.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.xuptggg.detection.R;
import com.xuptggg.detection.contract.IDetectionContract;
import com.xuptggg.detection.databinding.ActivityDetectionBinding;
import com.xuptggg.detection.model.DetectionInfo;

import java.util.List;

public class DetectionActivity extends AppCompatActivity implements IDetectionContract.IDetectionView {
    private ActivityDetectionBinding binding;
    private boolean isStartDetection = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.ivCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartDetection) {
                    binding.tvDetectionState.setText("点击开始识茶");
                    binding.tvDetectionAttach.setVisibility(View.VISIBLE);
                    isStartDetection = false;
                } else {
                    binding.tvDetectionState.setText("录音中");
                    binding.tvDetectionAttach.setVisibility(View.INVISIBLE);
                    isStartDetection = true;
                }
                binding.rippleView.toggleAnimations();
            }
        });

    }

    @Override
    public void setPresenter(IDetectionContract.IDetectionPresenter presenter) {

    }

    @Override
    public void showDetectionInfomation(DetectionInfo detectionInfos) {

    }

    @Override
    public void showError() {

    }
}
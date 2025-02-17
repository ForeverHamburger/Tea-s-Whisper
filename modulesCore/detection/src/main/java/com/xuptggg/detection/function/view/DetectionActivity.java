package com.xuptggg.detection.function.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.detection.R;
import com.xuptggg.detection.function.contract.IDetectionContract;
import com.xuptggg.detection.databinding.ActivityDetectionBinding;
import com.xuptggg.detection.function.model.DetectionInfo;
import com.xuptggg.detection.history.view.DetectionHistoryActivity;

@Route(path = "/detection/DetectionActivity")
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

        binding.tbDetection.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_history) {
                    Intent intent = new Intent(DetectionActivity.this,DetectionHistoryActivity.class);
                    startActivity(intent);
                }
                return false;
            }
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
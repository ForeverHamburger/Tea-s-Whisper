package com.xuptggg.detection.history.view;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.detection.R;
import com.xuptggg.detection.databinding.ActivityDetectionHistoryBinding;
import com.xuptggg.detection.history.adapter.HistoryAdapter;
import com.xuptggg.detection.history.contract.IDetectionHistoryContract;
import com.xuptggg.detection.history.model.DetectionHistoryInfo;
import com.xuptggg.detection.history.model.DetectionHistoryModel;
import com.xuptggg.detection.history.presenter.DetectionHistoryPresenter;

import java.util.List;


@Route(path = "/detection/DetectionHistoryActivity")
public class DetectionHistoryActivity extends AppCompatActivity implements IDetectionHistoryContract.IDetectionView {

    private ActivityDetectionHistoryBinding binding;
    private IDetectionHistoryContract.IDetectionPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetectionHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detection_history), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setPresenter(new DetectionHistoryPresenter(new DetectionHistoryModel(),this));
        mPresenter.getDetectionInfo("history");
    }

    @Override
    public void setPresenter(IDetectionHistoryContract.IDetectionPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showDetectionInfomation(List<DetectionHistoryInfo> detectionHistoryInfos) {
        binding.historyList.setLayoutManager(new LinearLayoutManager(this));
        HistoryAdapter historyAdapter = new HistoryAdapter(detectionHistoryInfos, new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DetectionHistoryInfo item) {

            }

            @Override
            public void onMoreButtonClick(DetectionHistoryInfo item) {

            }
        });
        binding.historyList.setAdapter(historyAdapter);
    }

    @Override
    public void showError() {

    }
}
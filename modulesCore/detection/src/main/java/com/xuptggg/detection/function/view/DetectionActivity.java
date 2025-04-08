package com.xuptggg.detection.function.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xuptggg.detection.R;
import com.xuptggg.detection.card.AoqiDialog;
import com.xuptggg.detection.card.FlippableDialog;
import com.xuptggg.detection.function.contract.IDetectionContract;
import com.xuptggg.detection.databinding.ActivityDetectionBinding;
import com.xuptggg.detection.function.model.DetectionInfo;
import com.xuptggg.detection.history.view.DetectionHistoryActivity;

@Route(path = "/detection/DetectionActivity")
public class DetectionActivity extends AppCompatActivity implements IDetectionContract.IDetectionView {
    private ActivityDetectionBinding binding;
    private boolean isStartDetection = false;
    private Handler handler = new Handler();
    private boolean aoqi = false;
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
        binding.tvWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aoqi) {
                    binding.tvWish.setText("今宵更有湘江月，照出菲菲满碗花。");
                } else {
                    binding.tvWish.setText("江畔何人初见月？江月何年初照人。");
                }
                aoqi = !aoqi;
            }
        });

        binding.tbDetection.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tbDetection.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_history) {
                    ARouter.getInstance().build("/detection/DetectionHistoryActivity").navigation();
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
                    handler.removeCallbacksAndMessages(null); // 取消可能存在的延迟任务
                } else {
                    binding.tvDetectionState.setText("录音中");
                    binding.tvDetectionAttach.setVisibility(View.INVISIBLE);
                    isStartDetection = true;
                    // 延迟 3 秒后弹出卡片
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showFlippableDialog();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.rippleView.toggleAnimations();
                                    binding.tvDetectionState.setText("点击开始识茶");
                                    binding.tvDetectionAttach.setVisibility(View.VISIBLE);
                                    isStartDetection = false;
                                }
                            });
                        }
                    }, 3000);


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

    private void showFlippableDialog() {
        if (aoqi) {
            final AoqiDialog aoqiDialog = new AoqiDialog(this);
            aoqiDialog.show();
        }else {
            final FlippableDialog flippableDialog = new FlippableDialog(this);
            flippableDialog.show();
        }
    }
}
package com.xuptggg.navigation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.xuptggg.navigation.R;
import com.xuptggg.navigation.contract.INavigationContract;
import com.xuptggg.navigation.databinding.ActivityNavigationBinding;
import com.xuptggg.navigation.model.NavigationInfo;
import com.xuptggg.navigation.model.NavigationModel;
import com.xuptggg.navigation.presenter.NavigationPresenter;

import java.util.List;

@Route(path = "/navigation/NavigationActivity")
public class NavigationActivity extends AppCompatActivity implements INavigationContract.INavigationView {
    private ActivityNavigationBinding binding;
    private INavigationContract.INavigationPresenter mPresenter;
    private boolean isSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setStatusBarColor(getResources().getColor(R.color.light_green));

        binding.bnvNavigation.setItemIconTintList(null);

        NavigationModel navigationModel = new NavigationModel();
        setPresenter(new NavigationPresenter(navigationModel,this));
        mPresenter.getNavigationInfo("开！");
    }

    @Override
    public void setPresenter(INavigationContract.INavigationPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNavigationInfomation(List<NavigationInfo> navigationInfos) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(binding.fcvNavigation.getId(), navigationInfos.get(0).getFragment());
        binding.bnvNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // 每次点击导航项时，创建一个新的 FragmentTransaction 实例
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                isSelected = false;
                // 根据导航项的标题进行不同的处理
                if ("首页".equals(menuItem.getTitle())) {
                    // 替换当前显示的 Fragment 为首页对应的 Fragment
                    fragmentTransaction.replace(binding.fcvNavigation.getId(), navigationInfos.get(0).getFragment());
                    binding.fabNavigation.setImageResource(R.drawable.ic_detect);
                    isSelected = false;
                } else if ("论坛".equals(menuItem.getTitle())) {
                    // 替换当前显示的 Fragment 为论坛对应的 Fragment
                    fragmentTransaction.replace(binding.fcvNavigation.getId(), navigationInfos.get(1).getFragment());
                    performChangeAnimation();
                    isSelected = true;
                } else if ("AI对话".equals(menuItem.getTitle())) {
                    // 替换当前显示的 Fragment 为 AI 对话对应的 Fragment
                    fragmentTransaction.replace(binding.fcvNavigation.getId(), navigationInfos.get(2).getFragment());
                    binding.fabNavigation.setImageResource(R.drawable.ic_detect);
                    isSelected = false;
                } else if ("我的".equals(menuItem.getTitle())) {
                    // 替换当前显示的 Fragment 为我的对应的 Fragment
                    fragmentTransaction.replace(binding.fcvNavigation.getId(), navigationInfos.get(3).getFragment());
                    binding.fabNavigation.setImageResource(R.drawable.ic_detect);
                    isSelected = false;
                } else if ("检测".equals(menuItem.getTitle())) {
                    // 替换当前显示的 Fragment 为检测对应的 Fragment
                    fragmentTransaction.replace(binding.fcvNavigation.getId(), navigationInfos.get(3).getFragment());
                    binding.fabNavigation.setImageResource(R.drawable.ic_detect);
                    isSelected = false;
                }

                // 提交 FragmentTransaction
                fragmentTransaction.commit();
                return true;
            }
        });

        MenuItem firstMenuItem = binding.bnvNavigation.getMenu().getItem(0);
        binding.bnvNavigation.setSelectedItemId(firstMenuItem.getItemId());

        binding.fabNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    ARouter.getInstance().build("/forum/PublishActivity").navigation();
                } else {
                    ARouter.getInstance().build("/detection/DetectionActivity").navigation();
                }
            }
        });
    }

    private void performChangeAnimation() {
        // 创建一个从 1 到 1.2 再到 1 的弹性动画
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 1.2f, 1f);
        animator.setDuration(300);
        animator.setInterpolator(new BounceInterpolator());

        animator.addUpdateListener(animation -> {
            float scale = (float) animation.getAnimatedValue();
            binding.fabNavigation.setScaleX(scale);
            binding.fabNavigation.setScaleY(scale);
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                binding.fabNavigation.setImageResource(R.drawable.ic_add);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        animator.start();
    }

    @Override
    public void showError() {

    }
}
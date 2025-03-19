package com.xuptggg.navigation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
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
    private static final int DOUBLE_CLICK_TIME_DELAY = 2000;
    private long firstBackPressedTime = 0;
    private FragmentManager fragmentManager;
    private FragmentTransaction initialTransaction;
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

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (firstBackPressedTime == 0) {
                    firstBackPressedTime = System.currentTimeMillis();
                    Toast.makeText(NavigationActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                } else {
                    long secondBackPressedTime = System.currentTimeMillis();
                    if (secondBackPressedTime - firstBackPressedTime < DOUBLE_CLICK_TIME_DELAY) {
                        finish();
                    } else {
                        firstBackPressedTime = 0;
                        Toast.makeText(NavigationActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void setPresenter(INavigationContract.INavigationPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNavigationInfomation(List<NavigationInfo> navigationInfos) {
        fragmentManager = getSupportFragmentManager();
        initialTransaction = fragmentManager.beginTransaction();
        for (NavigationInfo navigationInfo : navigationInfos) {
            Fragment fragment = navigationInfo.getFragment();
            initialTransaction.add(binding.fcvNavigation.getId(),fragment);
            initialTransaction.hide(fragment);
        }
        initialTransaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right)
                .commit();

        binding.bnvNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                isSelected = false;
                // 根据导航项的标题进行不同的处理
                getFragment(menuItem.getTitle(),navigationInfos);

                if ("首页".equals(menuItem.getTitle())) {
                    binding.fabNavigation.setImageResource(R.drawable.ic_detect);
                    isSelected = false;
                } else if ("论坛".equals(menuItem.getTitle())) {
                    performChangeAnimation();
                    isSelected = true;
                } else if ("AI对话".equals(menuItem.getTitle())) {
                    binding.fabNavigation.setImageResource(R.drawable.ic_detect);
                    isSelected = false;
                } else if ("我的".equals(menuItem.getTitle())) {
                    binding.fabNavigation.setImageResource(R.drawable.ic_detect);
                    isSelected = false;
                } else if ("检测".equals(menuItem.getTitle())) {
                    binding.fabNavigation.setImageResource(R.drawable.ic_detect);
                    isSelected = false;
                }
                return true;
            }

            private Fragment getFragment(CharSequence title, List<NavigationInfo> navigationInfos) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (NavigationInfo navigationInfo : navigationInfos) {
                    if (title.equals(navigationInfo.getFragmentName())){
                        Log.d("TAG", "getFragment: "+ "xixi");
                        fragmentTransaction.show(navigationInfo.getFragment());
                    } else {
                        Log.d("TAG", "getFragment: "+ "???" );
                        fragmentTransaction.hide(navigationInfo.getFragment());
                    }
                }
                fragmentTransaction.commit();
                return null;
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
        if(isSelected) {
            return;
        } else {
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
    }

    @Override
    public void showError() {

    }
}
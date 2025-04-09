package com.xuptggg.navigation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
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
    private FloatingActionMenu floatingActionMenu;
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
                if (floatingActionMenu != null && floatingActionMenu.isOpen()) {
                    floatingActionMenu.close(true);
                }

                if ("首页".equals(menuItem.getTitle())) {
                    binding.fabNavigation.setImageResource(R.drawable.ic_tea_drink);
                    isSelected = false;
                } else if ("论坛".equals(menuItem.getTitle())) {
                    performChangeAnimation();
                    isSelected = true;
                } else if ("AI对话".equals(menuItem.getTitle())) {
                    binding.fabNavigation.setImageResource(R.drawable.ic_tea_drink);
                    isSelected = false;
                } else if ("我的".equals(menuItem.getTitle())) {
                    binding.fabNavigation.setImageResource(R.drawable.ic_tea_drink);
                    isSelected = false;
                } else if ("检测".equals(menuItem.getTitle())) {
                    binding.fabNavigation.setImageResource(R.drawable.ic_tea_drink);
                    isSelected = false;
                }

                setOnSelectedChanged();

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

        initFloatActionButton();

    }

    private void setOnSelectedChanged() {
        if (isSelected) {
            binding.fabNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build("/forum/PublishActivity").navigation();
                }
            });
        } else {
            initFloatActionButton();
        }
    }

    private void initFloatActionButton() {
        SubActionButton.Builder builder = new SubActionButton.Builder(this);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_detect);
        SubActionButton detectionButton = builder.setContentView(imageView)
                .setLayoutParams(new FrameLayout.LayoutParams(150,150))
                .setTheme(R.style.BnvStyle)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.green_circle)).build();
        detectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/detection/DetectionActivity").navigation();
            }
        });

        ImageView imageView1 = new ImageView(this);
        imageView1.setImageResource(R.drawable.ic_ar);
        SubActionButton arButton = builder.setContentView(imageView1)
                .setLayoutParams(new FrameLayout.LayoutParams(150,150))
                .setTheme(R.style.BnvStyle)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.green_circle)).build();
        arButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExitAnimation();
                floatingActionMenu.close(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ARouter.getInstance().build("/recognition/RecognitionActivity").navigation();
                        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startReturnAnimation();
                            }
                        },200);
                    }
                },700);
            }
        });

        floatingActionMenu = new FloatingActionMenu.Builder(this)
                .setStartAngle(240)
                .setEndAngle(300)
                .addSubActionView(arButton)
                .addSubActionView(detectionButton)
                .attachTo(binding.fabNavigation)
                .build();

        floatingActionMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                binding.fabNavigation.setImageResource(R.drawable.ic_close);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                binding.fabNavigation.setImageResource(R.drawable.ic_tea_drink);
            }
        });
    }


    private void startExitAnimation() {
        // 让 fcvNavigation 从上方退出屏幕
        ObjectAnimator fcvAnimator = ObjectAnimator.ofFloat(binding.fcvNavigation, "translationY", 0f, -binding.fcvNavigation.getHeight());
        fcvAnimator.setDuration(500);
        fcvAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        fcvAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // fcvNavigation 动画结束后，开始其余视图从下方退出屏幕的动画
                ObjectAnimator coordinatorAnimator = ObjectAnimator.ofFloat(binding.coordinatorLayout, "translationY", 0f, binding.coordinatorLayout.getHeight());
                coordinatorAnimator.setDuration(300);
                coordinatorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                coordinatorAnimator.start();
            }
        });
        fcvAnimator.start();
    }


    private void startReturnAnimation() {
        // 让 coordinatorLayout 从下方归位
        ObjectAnimator coordinatorAnimator = ObjectAnimator.ofFloat(binding.coordinatorLayout, "translationY", binding.coordinatorLayout.getHeight(), 0f);
        coordinatorAnimator.setDuration(300);
        coordinatorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        coordinatorAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // coordinatorLayout 动画结束后，开始 fcvNavigation 从上方归位的动画
                ObjectAnimator fcvAnimator = ObjectAnimator.ofFloat(binding.fcvNavigation, "translationY", -binding.fcvNavigation.getHeight(), 0f);
                fcvAnimator.setDuration(300);
                fcvAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                fcvAnimator.start();
            }
        });
        coordinatorAnimator.start();
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
package com.xuptggg.module.login;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.module.login.LoginIn.LoginInFragment;
import com.xuptggg.module.login.LoginIn.LoginInModel;
import com.xuptggg.module.login.LoginIn.LoginInPresenter;
import com.xuptggg.module.login.databinding.ActivityLoginBinding;

@Route(path = "/login/LoginActivity")
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        initViews();
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    private void initViews() {
        initFragment();
        initListener();
    }

    private void initListener() {

    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LoginInFragment loginInFragment = (LoginInFragment) fm.findFragmentById(R.id.fragment_container);
        FragmentTransaction ft = fm.beginTransaction();
        if (loginInFragment == null) {
            loginInFragment = new LoginInFragment();
        }
        LoginInPresenter loginInPresenter = new LoginInPresenter(loginInFragment, new LoginInModel());
        loginInFragment.setPresenter(loginInPresenter);
        ft.add(R.id.fragment_container, loginInFragment);
        ft.commit();
    }
    public void adjustCardViewHeight(int fragmentHeight) {
        CardView cardView = binding.cardViewLogin;
        ConstraintLayout layout = binding.main;

        int screenHeight = layout.getHeight(); // 获取屏幕总高度
        float heightPercent = (float) fragmentHeight / screenHeight; // 计算占比

        // 约束调整
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) cardView.getLayoutParams();
        params.matchConstraintPercentHeight = heightPercent; // 应用计算出的高度百分比
        cardView.setLayoutParams(params);
    }

}
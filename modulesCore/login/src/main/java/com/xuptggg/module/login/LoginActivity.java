package com.xuptggg.module.login;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.textfield.TextInputEditText;
import com.tencent.mmkv.MMKV;
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
        MMKV.initialize(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        initViews();


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

    public void adjustCardViewForFragment(int fragmentHeight) {

        if (fragmentHeight <= 1400) {
            fragmentHeight = 1400;
        }
        if(fragmentHeight >= 1600){
            fragmentHeight = 1600;
        }

        ConstraintLayout constraintLayout = findViewById(R.id.main);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        int newCardViewHeight = fragmentHeight + 180;
        // 调整 CardView 高度
        constraintSet.constrainHeight(R.id.cardView_login, newCardViewHeight);
        // 调整 FragmentContainerView 高度
        constraintSet.constrainHeight(R.id.fragment_container, fragmentHeight);

//        constraintSet.connect(R.id.fragment_container, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 50);
//        constraintSet.connect(R.id.cardView_login, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 50);
//        //让 CardView 适当上移，避免底部挤压
//        constraintSet.connect(R.id.cardView_login, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 50);
        constraintSet.applyTo(constraintLayout);
    }
}
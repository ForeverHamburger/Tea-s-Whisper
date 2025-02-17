package com.xuptggg.module.login;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.module.login.LoginIn.LoginInFragment;
import com.xuptggg.module.login.LoginIn.LoginInModel;
import com.xuptggg.module.login.LoginIn.LoginInPresenter;

@Route(path = "/login/LoginActivity")
public class LoginActivity extends AppCompatActivity {

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
}
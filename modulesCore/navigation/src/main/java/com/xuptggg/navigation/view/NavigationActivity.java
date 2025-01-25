package com.xuptggg.navigation.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
        binding.bnvNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getTitle() == "首页") {
                    FragmentTransaction replace = fragmentTransaction.replace(binding.fcvNavigation.getId(),navigationInfos.get(0).getFragment());
                    replace.commit();
                } else if (menuItem.getTitle() == "论坛") {
//                    FragmentTransaction replace = fragmentTransaction.replace(binding.fcvNavigation.getId(),navigationInfos.get(1).getFragment());
//                    replace.commit();
                } else if (menuItem.getTitle() == "AI对话") {
//                    FragmentTransaction replace = fragmentTransaction.replace(binding.fcvNavigation.getId(),navigationInfos.get(2).getFragment());
//                    replace.commit();
                } else if (menuItem.getTitle() == "我的") {
//                    FragmentTransaction replace = fragmentTransaction.replace(binding.fcvNavigation.getId(),navigationInfos.get(3).getFragment());
//                    replace.commit();
                } else if (menuItem.getTitle() == "检测") {
//                    FragmentTransaction replace = fragmentTransaction.replace(binding.fcvNavigation.getId(),navigationInfos.get(4).getFragment());
//                    replace.commit();
                }
                return false;
            }
        });
    }

    @Override
    public void showError() {

    }
}
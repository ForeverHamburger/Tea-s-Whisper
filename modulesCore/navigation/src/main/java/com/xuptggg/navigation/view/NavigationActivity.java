package com.xuptggg.navigation.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuptggg.navigation.R;
import com.xuptggg.navigation.contract.INavigationContract;
import com.xuptggg.navigation.databinding.ActivityNavigationBinding;
import com.xuptggg.navigation.model.NavigationInfo;

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
    }

    @Override
    public void setPresenter(INavigationContract.INavigationPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNavigationInfomation(List<NavigationInfo> navigationInfos) {

    }

    @Override
    public void showError() {

    }
}
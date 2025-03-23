package com.xuptggg.search;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;
import com.xuptggg.search.base.SearchHistoryUtils;
import com.xuptggg.search.databinding.ActivitySearchBinding;
import com.xuptggg.search.model.ResultModel;
import com.xuptggg.search.model.SearchModel;
import com.xuptggg.search.presenter.ResultPresenter;
import com.xuptggg.search.presenter.SearchPresenter;
import com.xuptggg.search.view.Result.ResultFragment;
import com.xuptggg.search.view.Search.SearchFragment;

import java.util.List;

@Route(path = "/search/SearchActivity")
public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_search);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        Log.d("SearchActivity", "onCreate");

    }
    private void initViews() {
        initFragment();
        initListener();
    }

    private void initListener() {

        Log.d("SearchActivity", "initListener");
        binding.btnSearch.setOnClickListener(v -> {
            String searchText = binding.etSearch.getText().toString();
            Log.d("SearchActivity", "btnSearch");
            if(searchText.isEmpty()){
                Log.d("SearchActivity", "Search is empty");
            }else {
                Log.d("SearchActivity", "Search is not empty");
                FragmentManager fm = getSupportFragmentManager();
                Fragment currentFragment = fm.findFragmentById(R.id.fragment_container);

                if (currentFragment instanceof ResultFragment) {
                    ResultFragment resultFragment = (ResultFragment) currentFragment;
                } else {
                    ResultFragment resultFragment = ResultFragment.newInstance(searchText);
                    ResultPresenter presenter = new ResultPresenter(resultFragment, new ResultModel());
                    resultFragment.setPresenter(presenter);
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, resultFragment)
                            .commit();
                }
                SearchHistoryUtils.addSearchHistory(searchText);
            }
        });
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        SearchFragment searchFragment = (SearchFragment) fm.findFragmentById(R.id.fragment_container);

        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            SearchPresenter presenter = new SearchPresenter(new SearchModel(), searchFragment);
            searchFragment.setPresenter(presenter);
            fm.beginTransaction()
                    .replace(R.id.fragment_container, searchFragment)
                    .commit();
        }
    }

}
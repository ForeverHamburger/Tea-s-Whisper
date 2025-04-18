package com.xuptggg.search.view.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;
import com.xuptggg.search.Adapter.OnItemClickListener;
import com.xuptggg.search.Adapter.TeaSearchCommendAdapter;
import com.xuptggg.search.Adapter.TeaShowAdapter;
import com.xuptggg.search.contract.ISearchContract;
import com.xuptggg.search.databinding.FragmentSearchBinding;
import com.xuptggg.search.model.SearchInfo;

import java.util.List;

@Route(path = "/search/SearchFragment")
public class SearchFragment extends Fragment implements ISearchContract.ISearchView{
    private FragmentSearchBinding binding;
    private ISearchContract.ISearchPresenter mPresenter;
    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> historys = mPresenter.getTeaSearchHistory();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.rvTeaShow.setLayoutManager(layoutManager);
        TeaShowAdapter teaShowAdapter= new TeaShowAdapter(mPresenter.getTeaShow());
        binding.rvTeaShow.setAdapter(teaShowAdapter);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2);
        binding.rvRecommendSearch.setLayoutManager(layoutManager1);

        TeaSearchCommendAdapter teaSearchCommendAdapter = new TeaSearchCommendAdapter(mPresenter.getTeaSearchCommend(), (OnItemClickListener) requireActivity());
        binding.rvRecommendSearch.setAdapter(teaSearchCommendAdapter);

        binding.rvHistorySearch.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvHistorySearch.setAdapter(new TeaSearchCommendAdapter(historys, (OnItemClickListener) requireActivity()));
    }
    @Override
    public void setPresenter(ISearchContract.ISearchPresenter presenter) {
        mPresenter =presenter;
    }

    @Override
    public void showSearchInfomation(SearchInfo searchInfo) {

    }

    @Override
    public void showError() {

    }
}
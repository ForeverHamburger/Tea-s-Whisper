package com.xuptggg.search.presenter;

import androidx.fragment.app.Fragment;

import com.xuptggg.search.base.FragmentReplace;
import com.xuptggg.search.contract.ISearchContract;
import com.xuptggg.search.model.LoadSearchInfoCallBack;
import com.xuptggg.search.model.SearchInfo;
import com.xuptggg.search.model.TeaShowInfo;

import java.util.List;

public class SearchPresenter implements ISearchContract.ISearchPresenter, LoadSearchInfoCallBack<SearchInfo>{
    private ISearchContract.ISearchModel model;
    private ISearchContract.ISearchView view;

    public SearchPresenter(ISearchContract.ISearchModel model, ISearchContract.ISearchView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getSearchInfo(String info) {
        model.execute(info,this);
    }
    @Override
    public void getToken(String token) {
        model.getToken(token);
        getSearchInfo("");
    }

    @Override
    public List<String> getTeaSearchCommend() {
        return model.getTeaSearchCommend();
    }

    @Override
    public List<TeaShowInfo> getTeaShow() {
        return model.getTeaShow();
    }

    @Override
    public void onSuccess(SearchInfo searchInfo) {
        view.showSearchInfomation(searchInfo);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onFinish() {

    }
}

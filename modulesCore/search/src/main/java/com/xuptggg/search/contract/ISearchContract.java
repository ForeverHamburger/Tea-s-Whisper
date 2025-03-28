package com.xuptggg.search.contract;

import com.xuptggg.search.base.BaseView;
import com.xuptggg.search.model.LoadSearchInfoCallBack;
import com.xuptggg.search.model.SearchInfo;
import com.xuptggg.search.model.TeaShowInfo;

import java.util.List;

public interface ISearchContract {
    interface ISearchModel<T> {
        void execute(T data, LoadSearchInfoCallBack callBack);
        void getToken(String token);
        List<String> getTeaSearchCommend();
        List<TeaShowInfo> getTeaShow();

        List<String> getTeaSearchHistory();
    }

    interface ISearchPresenter{
        void getSearchInfo(String info);
        void getToken(String token);
        List<String> getTeaSearchCommend();
        List<TeaShowInfo> getTeaShow();

        List<String> getTeaSearchHistory();
    }
    interface ISearchView extends BaseView<ISearchPresenter> {
        void showSearchInfomation(SearchInfo searchInfo);
        void showError();
    }
}

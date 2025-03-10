package com.xuptggg.search.contract;

import com.xuptggg.search.base.BaseView;
import com.xuptggg.search.model.LoadSearchInfoCallBack;
import com.xuptggg.search.model.SearchInfo;

import java.util.List;

public interface ISearchContract {
    interface ISearchModel<T> {
        void execute(T data, LoadSearchInfoCallBack callBack);
    }

    interface ISearchPresenter{
        void getSearchInfo(String info);
    }
    interface ISearchView extends BaseView<ISearchPresenter> {
        void showSearchInfomation(SearchInfo searchInfo);
        void showError();
    }
}

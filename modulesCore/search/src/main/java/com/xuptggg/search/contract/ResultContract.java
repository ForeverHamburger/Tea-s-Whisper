package com.xuptggg.search.contract;

import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.xuptggg.search.base.BaseView;

import java.util.List;


public interface ResultContract {
    interface Model {
        void getResultInfo(String content, String tag, String size, LoadTasksCallBack callBack);

        void getToken(String token);

    }

    interface Presenter<T> {
        void getResultInfo(String content, String tag, String size);

        void unSubscribe();

        void getToken(String token);
    }

    interface View<T> extends BaseView<Presenter<T>> {
        void showError();

        Boolean isACtive();
        void showResult(List<T> data);


    }
}
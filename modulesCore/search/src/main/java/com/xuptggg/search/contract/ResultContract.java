package com.xuptggg.search.contract;

import com.xuptggg.libnetwork.aword.LoadTasksCallBack;
import com.xuptggg.search.base.BaseView;


public interface ResultContract {
    interface Model {
        void getResultInfo(String content, String tag, String size, LoadTasksCallBack callBack);

        void getToken(String token);

    }

    interface Presenter {
        void getResultInfo(String content, String tag, String size);

        void unSubscribe();

        void getToken(String token);
    }

    interface View extends BaseView<Presenter> {
        void showError();

        Boolean isACtive();


    }
}
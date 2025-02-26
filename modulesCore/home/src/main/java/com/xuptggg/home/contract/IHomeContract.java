package com.xuptggg.home.contract;

import com.xuptggg.home.base.BaseView;
import com.xuptggg.home.model.LoadHomeInfoCallBack;
import com.xuptggg.home.model.TeaInfo;

import java.util.List;

public interface IHomeContract {
    interface IHomeModel<T> {
        // data是请求数据用的头
        // callBack为回调方法
        void execute(T data, LoadHomeInfoCallBack callBack);
    }
    interface IHomePresenter{
        void getHomeInfo(String info);
    }
    interface IHomeView extends BaseView<IHomePresenter> {
        void showHomeInfomation(List<TeaInfo> individualInfos);
        void showError();
    }
}

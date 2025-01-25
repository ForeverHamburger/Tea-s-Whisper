package com.xuptggg.navigation.contract;

import com.xuptggg.navigation.base.BaseView;
import com.xuptggg.navigation.model.LoadNavigationInfoCallBack;
import com.xuptggg.navigation.model.NavigationInfo;

import java.util.List;

public interface INavigationContract {
    interface INavigationModel<T> {
        void execute(T data, LoadNavigationInfoCallBack callBack);
    }

    interface INavigationPresenter{
        void getNavigationInfo(String info);
    }
    interface INavigationView extends BaseView<INavigationPresenter> {
        void showNavigationInfomation(List<NavigationInfo> navigationInfos);
        void showError();
    }

}

package com.xuptggg.navigation.contract;

import com.xuptggg.navigation.model.LoadNavigationInfoCallBack;

public interface INavigationContract {
    interface INavigationModel<T> {
        void execute(T data, LoadNavigationInfoCallBack callBack);
    }


}

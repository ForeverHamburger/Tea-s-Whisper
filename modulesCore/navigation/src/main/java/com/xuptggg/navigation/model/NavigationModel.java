package com.xuptggg.navigation.model;

import com.xuptggg.navigation.contract.INavigationContract;

public class NavigationModel implements INavigationContract.INavigationModel<String> {
    @Override
    public void execute(String data, LoadNavigationInfoCallBack callBack) {



        callBack.onSuccess("success");
    }
}

package com.xuptggg.navigation.model;

import com.xuptggg.navigation.contract.INavigationContract;

import java.util.ArrayList;
import java.util.List;

public class NavigationModel implements INavigationContract.INavigationModel<String> {
    @Override
    public void execute(String data, LoadNavigationInfoCallBack callBack) {
        List<NavigationInfo> list = new ArrayList<>();


        callBack.onSuccess(list);
    }
}

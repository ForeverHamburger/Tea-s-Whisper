package com.xuptggg.navigation.model;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xuptggg.navigation.contract.INavigationContract;

import java.util.ArrayList;
import java.util.List;

public class NavigationModel implements INavigationContract.INavigationModel<String> {
    @Override
    public void execute(String data, LoadNavigationInfoCallBack callBack) {
        List<NavigationInfo> list = new ArrayList<>();

        Fragment individualFragment = (Fragment) ARouter.getInstance().build("/individual/IndividualFragment").navigation();
        list.add(new NavigationInfo("我的",individualFragment));

        Fragment homeFragment = (Fragment) ARouter.getInstance().build("/home/HomeFragment").navigation();
        list.add(new NavigationInfo("首页",homeFragment));

        callBack.onSuccess(list);
    }
}

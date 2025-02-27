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

        Fragment homeFragment = (Fragment) ARouter.getInstance().build("/home/HomeFragment").navigation();
        list.add(new NavigationInfo("首页",homeFragment));

        Fragment homeFragment1 = (Fragment) ARouter.getInstance().build("/home/HomeFragment").navigation();
        list.add(new NavigationInfo("首页",homeFragment1));

        Fragment homeFragment2 = (Fragment) ARouter.getInstance().build("/chat/CommunicateFragment").navigation();
        list.add(new NavigationInfo("首页",homeFragment2));

        Fragment homeFragment3 = (Fragment) ARouter.getInstance().build("/home/HomeFragment").navigation();
        list.add(new NavigationInfo("首页",homeFragment3));

        callBack.onSuccess(list);
    }
}

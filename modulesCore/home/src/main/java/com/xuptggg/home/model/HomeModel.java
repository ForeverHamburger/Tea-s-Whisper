package com.xuptggg.home.model;

import com.xuptggg.home.contract.IHomeContract;

public class HomeModel implements IHomeContract.IHomeModel<String> {
    @Override
    public void execute(String data, LoadHomeInfoCallBack callBack) {
        TeaInfo info = new TeaInfo("test","test",123);

        callBack.onSuccess(info);
    }
}

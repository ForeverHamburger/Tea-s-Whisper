package com.xuptggg.detail.model;

import com.xuptggg.detail.contract.IDetailContract;
import com.xuptggg.detail.model.infos.DetailInfo;

public class DetailModel implements IDetailContract.IDetailModel<String> {
    @Override
    public void execute(String data, LoadDetailInfoCallBack callBack) {
        DetailInfo info = new DetailInfo();

        callBack.onSuccess(info);
    }
}

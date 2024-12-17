package com.xuptggg.guidepage.model;

import com.xuptggg.guidepage.contract.IGuideContract;

import java.util.ArrayList;
import java.util.List;

public class GuideModel implements IGuideContract.IGuideModel<String> {
    @Override
    public void execute(String data, LoadGuideInfoCallBack callBack) {

        List<GuideInfo> list = new ArrayList<>();



        callBack.onSuccess(list);
    }
}

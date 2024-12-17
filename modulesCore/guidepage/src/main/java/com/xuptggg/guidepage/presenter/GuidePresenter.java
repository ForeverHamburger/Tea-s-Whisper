package com.xuptggg.guidepage.presenter;



import com.xuptggg.guidepage.contract.IGuideContract;
import com.xuptggg.guidepage.model.GuideInfo;
import com.xuptggg.guidepage.model.LoadGuideInfoCallBack;

import java.util.List;

public class GuidePresenter implements IGuideContract.IGuidePresenter, LoadGuideInfoCallBack<List<GuideInfo>> {
    private IGuideContract.IGuideView view;
    private IGuideContract.IGuideModel model;

    public GuidePresenter(IGuideContract.IGuideView view, IGuideContract.IGuideModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getGuideInfo(String info) {
        model.execute(info,this);
    }
    @Override
    public void onSuccess(List<GuideInfo> guideInfos) {
        view.showGuideInfomation(guideInfos);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onFinish() {

    }


}

package com.xuptggg.detail.presenter;

import com.xuptggg.detail.contract.IDetailContract;
import com.xuptggg.detail.model.LoadDetailInfoCallBack;
import com.xuptggg.detail.model.infos.DetailInfo;

import java.util.List;

public class DetailPresenter implements IDetailContract.IDetailPresenter,LoadDetailInfoCallBack<DetailInfo> {
    private IDetailContract.IDetailModel model;
    private IDetailContract.IDetailView view;

    public DetailPresenter(IDetailContract.IDetailModel model, IDetailContract.IDetailView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getDetailInfo(String info) {
        model.execute(info,this);
    }

    @Override
    public void onSuccess(DetailInfo detailInfo) {
        view.showDetailInfomation(detailInfo);
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

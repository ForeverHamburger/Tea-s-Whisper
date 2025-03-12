package com.xuptggg.detail.contract;

import com.xuptggg.detail.base.BaseView;
import com.xuptggg.detail.model.LoadDetailInfoCallBack;
import com.xuptggg.detail.model.infos.DetailInfo;

import java.util.List;

public interface IDetailContract {
    interface IDetailModel<T> {
        void execute(T data, LoadDetailInfoCallBack callBack);
    }

    interface IDetailPresenter{
        void getDetailInfo(String info);
    }
    interface IDetailView extends BaseView<IDetailPresenter> {
        void showDetailInfomation(DetailInfo navigationInfos);
        void showError();
    }
}

package com.xuptggg.individual.contract;

import com.xuptggg.individual.base.BaseView;
import com.xuptggg.individual.model.IndividualInfo;
import com.xuptggg.individual.model.LoadIndividualInfoCallBack;

import java.util.List;

public interface IIndividualContract {
    interface IIndividualModel<T> {
        // data是请求数据用的头
        // callBack为回调方法
        void execute(T data, LoadIndividualInfoCallBack callBack);
    }
    interface IIndividualPresenter{
        void getIndividualInfo(String info);
    }
    interface IIndividualView extends BaseView<IIndividualPresenter> {
        void showIndividualInfomation(IndividualInfo individualInfos);
        void showError();
    }
}

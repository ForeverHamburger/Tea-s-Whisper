package com.xuptggg.individual.personal.contract;

import com.xuptggg.individual.personal.base.BaseView;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.personal.model.LoadIndividualInfoCallBack;

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

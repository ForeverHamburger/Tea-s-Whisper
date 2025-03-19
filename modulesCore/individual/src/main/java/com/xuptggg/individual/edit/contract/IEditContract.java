package com.xuptggg.individual.edit.contract;

import com.xuptggg.individual.edit.model.LoadEditInfoCallBack;
import com.xuptggg.individual.personal.base.BaseView;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.personal.model.LoadIndividualInfoCallBack;

public interface IEditContract {
    interface IEditModel<T> {
        // data是请求数据用的头
        // callBack为回调方法
        void execute(T data, LoadEditInfoCallBack callBack);
    }
    interface IEditPresenter{
        void getEditInfo(String info);
    }
    interface IEditView extends BaseView<IEditPresenter> {
        void showMessage(IndividualInfo info);
        void showError();
    }
}

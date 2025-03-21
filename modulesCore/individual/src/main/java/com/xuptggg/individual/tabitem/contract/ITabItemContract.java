package com.xuptggg.individual.tabitem.contract;

import com.xuptggg.individual.personal.base.BaseView;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.tabitem.model.LoadTabItemCallBack;

public interface ITabItemContract {
    interface IIndividualModel<T> {
        // data是请求数据用的头
        // callBack为回调方法
        void execute(T data, LoadTabItemCallBack callBack);
    }
    interface ITabItemPresenter{
        void getTabItem(String info);
    }
    interface ITabItemView extends BaseView<ITabItemPresenter> {
        void showTabItemInfomation(IndividualInfo individualInfos);
        void showError();
    }
}

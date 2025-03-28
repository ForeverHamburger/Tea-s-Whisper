package com.xuptggg.individual.tabitem.contract;

import com.xuptggg.individual.personal.base.BaseView;
import com.xuptggg.individual.tabitem.model.ForumInfo;
import com.xuptggg.individual.tabitem.model.LoadTabItemCallBack;

import java.util.List;

public interface ITabItemContract {
    interface ITabItemModel<T> {
        // data是请求数据用的头
        // callBack为回调方法
        void execute(T data, String info, LoadTabItemCallBack callBack);
    }
    interface ITabItemPresenter{
        void getTabItem(String info, String token);
    }
    interface ITabItemView extends BaseView<ITabItemPresenter> {
        void showTabItemInfomation(List<ForumInfo> forumInfoList);
        void showError();
    }
}

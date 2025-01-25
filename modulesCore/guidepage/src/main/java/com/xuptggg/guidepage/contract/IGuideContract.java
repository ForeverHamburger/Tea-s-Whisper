package com.xuptggg.guidepage.contract;

import com.xuptggg.guidepage.base.BaseView;
import com.xuptggg.guidepage.model.GuideInfo;
import com.xuptggg.guidepage.model.LoadGuideInfoCallBack;

import java.util.List;

public interface IGuideContract {
    interface IGuideModel<T> {
        // data是请求数据用的头
        // callBack为回调方法
        void execute(T data, LoadGuideInfoCallBack callBack);
    }
    interface IGuidePresenter{
        void getGuideInfo(String info);
    }
    interface IGuideView extends BaseView<IGuidePresenter> {
        void showGuideInfomation(List<GuideInfo> guideInfos);
        void showError();
    }
}

package com.xuptggg.forum.contract;

import com.xuptggg.forum.base.BaseView;
import com.xuptggg.forum.model.ForumInfo;
import com.xuptggg.forum.model.LoadForumInfoCallBack;

import java.util.List;

public interface IForumContract {

    interface IForumModel<T> {
        void execute(T data, LoadForumInfoCallBack callBack);

        void execute(String data, LoadForumInfoCallBack callBack);
    }

    interface IForumPresenter{
        void getForumInfo(String info);
    }
    interface IForumView extends BaseView<IForumPresenter> {
        void showForumInfomation(List<ForumInfo> forumInfos);
        void showError();
    }
}

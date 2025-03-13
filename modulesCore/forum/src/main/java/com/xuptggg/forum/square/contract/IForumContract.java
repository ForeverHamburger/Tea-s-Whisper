package com.xuptggg.forum.square.contract;

import com.xuptggg.forum.square.base.BaseView;
import com.xuptggg.forum.square.model.ForumInfo;
import com.xuptggg.forum.square.model.LoadForumInfoCallBack;

import java.util.List;

public interface IForumContract {

    interface IForumModel<T> {
        void execute(T data, LoadForumInfoCallBack callBack);
    }

    interface IForumPresenter{
        void getForumInfo(String info);
    }
    interface IForumView extends BaseView<IForumPresenter> {
        void showForumInfomation(List<ForumInfo> forumInfos);
        void showError();
    }
}

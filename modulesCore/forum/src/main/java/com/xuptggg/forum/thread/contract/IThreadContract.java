package com.xuptggg.forum.thread.contract;

import com.xuptggg.forum.square.base.BaseView;
import com.xuptggg.forum.thread.model.LoadThreadCallBack;
import com.xuptggg.forum.thread.model.ThreadInfo;

public interface IThreadContract {

    interface IThreadModel<T> {
        void execute(T data,String postId, LoadThreadCallBack callBack);

    }

    interface IThreadPresenter{
        void getThreadInfo(String info,String postId);
    }
    interface IThreadView extends BaseView<IThreadPresenter> {
        void showThreadInfomation(ThreadInfo threadInfo);
        void showError();
    }
}

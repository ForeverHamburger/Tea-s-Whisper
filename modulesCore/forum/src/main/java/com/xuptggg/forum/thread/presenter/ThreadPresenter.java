package com.xuptggg.forum.thread.presenter;

import com.xuptggg.forum.square.contract.IForumContract;
import com.xuptggg.forum.square.model.ForumInfo;
import com.xuptggg.forum.square.model.LoadForumInfoCallBack;
import com.xuptggg.forum.thread.contract.IThreadContract;
import com.xuptggg.forum.thread.model.LoadThreadCallBack;
import com.xuptggg.forum.thread.model.ThreadInfo;

import java.util.List;

public class ThreadPresenter implements IThreadContract.IThreadPresenter, LoadThreadCallBack<ThreadInfo> {
    private IThreadContract.IThreadView view;
    private IThreadContract.IThreadModel model;

    public ThreadPresenter(IThreadContract.IThreadView view, IThreadContract.IThreadModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getThreadInfo(String info) {
        model.execute(info,this);
    }

    @Override
    public void onSuccess(ThreadInfo threadInfo) {
        view.showThreadInfomation(threadInfo);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onFinish() {

    }
}

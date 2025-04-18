package com.xuptggg.forum.square.presenter;

import com.xuptggg.forum.square.contract.IForumContract;
import com.xuptggg.forum.square.model.ForumInfo;
import com.xuptggg.forum.square.model.LoadForumInfoCallBack;

import java.util.List;

public class ForumPresenter implements IForumContract.IForumPresenter, LoadForumInfoCallBack<List<ForumInfo>> {
    private IForumContract.IForumView view;
    private IForumContract.IForumModel model;

    public ForumPresenter(IForumContract.IForumView view, IForumContract.IForumModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getForumInfo(String info) {
        model.execute(info,this);
    }

    @Override
    public void onSuccess(List<ForumInfo> forumInfoList) {
        view.showForumInfomation(forumInfoList);
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

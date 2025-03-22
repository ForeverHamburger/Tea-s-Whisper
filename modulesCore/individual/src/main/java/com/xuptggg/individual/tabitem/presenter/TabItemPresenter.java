package com.xuptggg.individual.tabitem.presenter;

import android.util.Log;

import com.xuptggg.individual.personal.contract.IIndividualContract;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.personal.model.LoadIndividualInfoCallBack;
import com.xuptggg.individual.tabitem.contract.ITabItemContract;
import com.xuptggg.individual.tabitem.model.ForumInfo;
import com.xuptggg.individual.tabitem.model.LoadTabItemCallBack;

import java.util.List;

public class TabItemPresenter implements LoadTabItemCallBack<List<ForumInfo>>, ITabItemContract.ITabItemPresenter {
    private ITabItemContract.ITabItemView view;
    private ITabItemContract.ITabItemModel model;

    public TabItemPresenter(ITabItemContract.ITabItemView view, ITabItemContract.ITabItemModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onSuccess(List<ForumInfo> forumInfoList) {
        view.showTabItemInfomation(forumInfoList);
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

    @Override
    public void getTabItem(String info,String token) {
        model.execute(token, info,this);
    }
}

package com.xuptggg.individual.presenter;

import com.xuptggg.individual.contract.IIndividualContract;
import com.xuptggg.individual.model.IndividualInfo;
import com.xuptggg.individual.model.LoadIndividualInfoCallBack;

public class IndividualPresenter implements IIndividualContract.IIndividualPresenter, LoadIndividualInfoCallBack<IndividualInfo> {
    private IIndividualContract.IIndividualView view;
    private IIndividualContract.IIndividualModel model;

    public IndividualPresenter(IIndividualContract.IIndividualView view, IIndividualContract.IIndividualModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getIndividualInfo(String info) {
        model.execute(info,this);
    }

    @Override
    public void onSuccess(IndividualInfo individualInfo) {
        view.showIndividualInfomation(individualInfo);
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

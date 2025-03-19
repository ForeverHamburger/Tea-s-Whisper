package com.xuptggg.individual.edit.presenter;

import com.xuptggg.individual.edit.contract.IEditContract;
import com.xuptggg.individual.edit.model.LoadEditInfoCallBack;
import com.xuptggg.individual.personal.model.IndividualInfo;

public class EditPresenter implements IEditContract.IEditPresenter , LoadEditInfoCallBack<IndividualInfo> {
    private IEditContract.IEditModel model;
    private IEditContract.IEditView view;

    public EditPresenter(IEditContract.IEditModel model, IEditContract.IEditView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getEditInfo(String info) {
        model.execute(info,this);
    }

    @Override
    public void onSuccess(IndividualInfo individualInfo) {
        view.showMessage(individualInfo);
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

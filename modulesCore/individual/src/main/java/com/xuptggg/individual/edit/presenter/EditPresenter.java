package com.xuptggg.individual.edit.presenter;

import com.xuptggg.individual.edit.contract.IEditContract;
import com.xuptggg.individual.edit.model.BaseIndividualInfo;
import com.xuptggg.individual.edit.model.LoadEditInfoCallBack;
import com.xuptggg.individual.edit.model.LoadImageUriCallBack;
import com.xuptggg.individual.edit.model.LoadPostEditCallBack;
import com.xuptggg.individual.personal.model.IndividualInfo;

import java.io.File;

public class EditPresenter implements IEditContract.IEditPresenter , LoadEditInfoCallBack<IndividualInfo> , LoadPostEditCallBack<String> , LoadImageUriCallBack<String> {
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
    public void postEditInfo(BaseIndividualInfo individualInfo, String token) {
        model.postInfo(token,individualInfo,this);
    }

    @Override
    public void getUri(File imageFile, String token) {
        model.getUriFromFile(imageFile,token,this);
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

    @Override
    public void onPostSuccess(String string) {
        view.showSuccessMessage(string);
    }

    @Override
    public void onStartPost() {

    }

    @Override
    public void onFailedPost() {

    }

    @Override
    public void onFinishPost() {

    }

    @Override
    public void onUploadSuccess(String string) {
        view.setImageUri(string);
    }

    @Override
    public void onUploadStart() {

    }

    @Override
    public void onUploadFailed() {

    }

    @Override
    public void onUploadFinish() {

    }
}

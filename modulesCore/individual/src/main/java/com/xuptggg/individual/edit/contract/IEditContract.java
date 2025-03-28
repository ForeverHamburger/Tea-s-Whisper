package com.xuptggg.individual.edit.contract;

import com.xuptggg.individual.edit.model.BaseIndividualInfo;
import com.xuptggg.individual.edit.model.LoadEditInfoCallBack;
import com.xuptggg.individual.edit.model.LoadImageUriCallBack;
import com.xuptggg.individual.edit.model.LoadPostEditCallBack;
import com.xuptggg.individual.personal.base.BaseView;
import com.xuptggg.individual.personal.model.IndividualInfo;
import com.xuptggg.individual.personal.model.LoadIndividualInfoCallBack;

import java.io.File;
import java.util.List;

public interface IEditContract {
    interface IEditModel<T> {
        // data是请求数据用的头
        // callBack为回调方法
        void execute(T data, LoadEditInfoCallBack callBack);
        void postInfo(T data, BaseIndividualInfo info, LoadPostEditCallBack callBack);
        void getUriFromFile(File files, String token, LoadImageUriCallBack callBack);
    }
    interface IEditPresenter{
        void getEditInfo(String info);
        void postEditInfo(BaseIndividualInfo individualInfo,String token);
        void getUri(File imageFile,String token);
    }
    interface IEditView extends BaseView<IEditPresenter> {
        void showMessage(IndividualInfo info);
        void showSuccessMessage(String string);
        void setImageUri(String imageUri);
        void showError();
    }
}

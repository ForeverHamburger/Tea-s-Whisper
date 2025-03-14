package com.xuptggg.forum.publish.contract;

import com.xuptggg.forum.publish.model.LoadImageUriCallBack;
import com.xuptggg.forum.publish.model.PublishInfo;
import com.xuptggg.forum.square.base.BaseView;

import java.io.File;
import java.util.List;

public interface IPublishContract {

    interface IPublishModel<T> {
        void getUriFromFile(List<File> files, String token, LoadImageUriCallBack callBack);
        void publishThread(PublishInfo publishInfo, String token, LoadImageUriCallBack callBack);
    }

    interface IPublishPresenter{
        void getUri(List<File> files, String token);
        void publishThread(PublishInfo publishInfos, String token);
    }
    interface IPublishView extends BaseView<IPublishPresenter> {
        void showMessage(List<String> strings);
        void showError();
    }
}

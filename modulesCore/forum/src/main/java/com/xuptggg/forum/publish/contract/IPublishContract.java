package com.xuptggg.forum.publish.contract;

import android.net.Uri;

import com.xuptggg.forum.publish.model.LoadPublishCallBack;
import com.xuptggg.forum.publish.model.PublishInfo;
import com.xuptggg.forum.square.base.BaseView;
import com.xuptggg.forum.square.model.ForumInfo;
import com.xuptggg.forum.square.model.LoadForumInfoCallBack;

import java.io.File;
import java.util.List;

public interface IPublishContract {

    interface IPublishModel<T> {
        void getUriFromFile(List<File> files, String token, LoadPublishCallBack callBack);
        void publishThread(PublishInfo publishInfo);
    }

    interface IPublishPresenter{
        void getUri(List<File> files, String token);
        void publishThread(PublishInfo publishInfos);
    }
    interface IPublishView extends BaseView<IPublishPresenter> {
        void showMessage(List<String> strings);
        void showError();
    }
}

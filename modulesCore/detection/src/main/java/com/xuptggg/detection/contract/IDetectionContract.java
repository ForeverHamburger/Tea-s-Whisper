package com.xuptggg.detection.contract;

import com.xuptggg.detection.base.BaseView;
import com.xuptggg.detection.model.DetectionInfo;
import com.xuptggg.detection.model.LoadDetectionInfoCallBack;

import java.util.List;

public interface IDetectionContract {
    interface IDetectionModel<T> {
        void execute(T data, LoadDetectionInfoCallBack callBack);
    }
    interface IDetectionPresenter{
        void getDetectionInfo(String info);
    }
    interface IDetectionView extends BaseView<IDetectionPresenter> {
        void showDetectionInfomation(DetectionInfo detectionInfos);
        void showError();
    }
}

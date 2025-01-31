package com.xuptggg.detection.function.contract;

import com.xuptggg.detection.base.BaseView;
import com.xuptggg.detection.function.model.DetectionInfo;
import com.xuptggg.detection.function.model.LoadDetectionInfoCallBack;

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

package com.xuptggg.detection.history.contract;

import com.xuptggg.detection.base.BaseView;
import com.xuptggg.detection.history.model.DetectionHistoryInfo;
import com.xuptggg.detection.history.model.LoadDetectionInfoCallBack;

import java.util.List;

public interface IDetectionHistoryContract {
    interface IDetectionModel<T> {
        void execute(T data, LoadDetectionInfoCallBack callBack);
    }
    interface IDetectionPresenter{
        void getDetectionInfo(String info);
    }
    interface IDetectionView extends BaseView<IDetectionPresenter> {
        void showDetectionInfomation(List<DetectionHistoryInfo> detectionHistoryInfos);
        void showError();
    }
}

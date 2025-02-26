package com.xuptggg.detection.history.presenter;

import com.xuptggg.detection.history.contract.IDetectionHistoryContract;
import com.xuptggg.detection.history.model.DetectionHistoryInfo;
import com.xuptggg.detection.history.model.LoadDetectionInfoCallBack;

import java.util.List;

public class DetectionHistoryPresenter implements IDetectionHistoryContract.IDetectionPresenter, LoadDetectionInfoCallBack<List<DetectionHistoryInfo>> {
    private IDetectionHistoryContract.IDetectionModel model;
    private IDetectionHistoryContract.IDetectionView view;

    public DetectionHistoryPresenter(IDetectionHistoryContract.IDetectionModel model, IDetectionHistoryContract.IDetectionView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getDetectionInfo(String info) {
        model.execute(info,this);
    }


    @Override
    public void onSuccess(List<DetectionHistoryInfo> detectionHistoryInfo) {
        view.showDetectionInfomation(detectionHistoryInfo);
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

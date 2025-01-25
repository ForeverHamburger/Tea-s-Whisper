package com.xuptggg.detection.presenter;

import com.xuptggg.detection.contract.IDetectionContract;
import com.xuptggg.detection.model.DetectionInfo;
import com.xuptggg.detection.model.LoadDetectionInfoCallBack;

import java.util.List;

public class DetectionPresenter implements IDetectionContract.IDetectionPresenter, LoadDetectionInfoCallBack<DetectionInfo> {
    private IDetectionContract.IDetectionModel model;
    private IDetectionContract.IDetectionView view;

    public DetectionPresenter(IDetectionContract.IDetectionModel model, IDetectionContract.IDetectionView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getDetectionInfo(String info) {
        model.execute(info,this);
    }

    @Override
    public void onSuccess(DetectionInfo detectionInfo) {
        view.showDetectionInfomation(detectionInfo);
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

package com.xuptggg.detection.history.model;

import com.xuptggg.detection.history.contract.IDetectionHistoryContract;

import java.util.ArrayList;
import java.util.List;

public class DetectionModel implements IDetectionHistoryContract.IDetectionModel<String> {
    @Override
    public void execute(String data, LoadDetectionInfoCallBack callBack) {
        List<DetectionHistoryInfo> list = new ArrayList<>();


        callBack.onSuccess(list);
    }
}

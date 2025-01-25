package com.xuptggg.detection.model;

import com.xuptggg.detection.contract.IDetectionContract;

import java.util.ArrayList;
import java.util.List;

public class DetectionModel implements IDetectionContract.IDetectionModel<String> {
    @Override
    public void execute(String data, LoadDetectionInfoCallBack callBack) {
        List<DetectionInfo> list = new ArrayList<>();


        callBack.onSuccess(list);
    }
}

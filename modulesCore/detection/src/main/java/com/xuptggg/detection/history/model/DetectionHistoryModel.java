package com.xuptggg.detection.history.model;

import com.xuptggg.detection.history.contract.IDetectionHistoryContract;

import java.util.ArrayList;
import java.util.List;

public class DetectionHistoryModel implements IDetectionHistoryContract.IDetectionModel<String> {
    @Override
    public void execute(String data, LoadDetectionInfoCallBack callBack) {

        List<DetectionHistoryInfo> list = new ArrayList<>();

        list.add(new DetectionHistoryInfo(
                "西湖龙井",
                "检测结果：优质（农药残留达标）",
                "2023-08-20 14:30",
                "https://example.com/tea1.jpg" // 替换为实际图片URL
        ));

        list.add(new DetectionHistoryInfo(
                "安溪铁观音",
                "检测异常：重金属微量超标",
                "2023-08-19 09:45",
                "https://example.com/tea2.jpg"
        ));

        list.add(new DetectionHistoryInfo(
                "云南普洱",
                "检测结果：合格（无添加剂）",
                "2023-08-18 16:15",
                "https://example.com/tea3.jpg"
        ));

        callBack.onSuccess(list);
    }
}

package com.xuptggg.detection.history.model;

public class DetectionHistoryInfo {

    private String teaName;         // 茶叶名称
    private String detectionTip;    // 检测提示
    private String detectionTime;   // 检测时间
    private String imageUrl;        // 茶叶图片URL

    public DetectionHistoryInfo(String teaName, String detectionTip, String detectionTime, String imageUrl) {
        this.teaName = teaName;
        this.detectionTip = detectionTip;
        this.detectionTime = detectionTime;
        this.imageUrl = imageUrl;
    }

    // Getter方法需要与适配器中的调用保持一致
    public String getTeaName() {
        return teaName;
    }

    public String getDetectionTip() {
        return detectionTip;
    }

    public String getDetectionTime() {
        return detectionTime;
    }

    public String getLeavesImageUrl() {
        return imageUrl;
    }
}

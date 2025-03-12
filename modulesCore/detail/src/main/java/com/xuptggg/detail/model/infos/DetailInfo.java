package com.xuptggg.detail.model.infos;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class DetailInfo {
    @SerializedName("name")
    private String teaName;
    @SerializedName("image")
    private String imageUrl;

    // 茶的简介
    @SerializedName("detail")
    private String teaBrief;
    // 茶相关历史
    @SerializedName("history")
    private String teaHistory;
    // 制茶相关
    @SerializedName("process")
    private String teaProcess;
    // 饮茶指南
    @SerializedName("guide")
    private String teaGuide;

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTeaBrief() {
        return teaBrief;
    }

    public void setTeaBrief(String teaBrief) {
        this.teaBrief = teaBrief;
    }

    public String getTeaHistory() {
        return teaHistory;
    }

    public void setTeaHistory(String teaHistory) {
        this.teaHistory = teaHistory;
    }

    public String getTeaProcess() {
        return teaProcess;
    }

    public void setTeaProcess(String teaProcess) {
        this.teaProcess = teaProcess;
    }

    public String getTeaGuide() {
        return teaGuide;
    }

    public void setTeaGuide(String teaGuide) {
        this.teaGuide = teaGuide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailInfo info = (DetailInfo) o;
        return Objects.equals(teaName, info.teaName) && Objects.equals(imageUrl, info.imageUrl) && Objects.equals(teaBrief, info.teaBrief) && Objects.equals(teaHistory, info.teaHistory) && Objects.equals(teaProcess, info.teaProcess) && Objects.equals(teaGuide, info.teaGuide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teaName, imageUrl, teaBrief, teaHistory, teaProcess, teaGuide);
    }

}
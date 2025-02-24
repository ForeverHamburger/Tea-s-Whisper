package com.xuptggg.home.model;

public class TeaHistoryInfo {
    private String title;
    private String detail;
    private int imageResId;

    public TeaHistoryInfo(String title, String detail, int imageResId) {
        this.title = title;
        this.detail = detail;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public int getImageResId() {
        return imageResId;
    }
}
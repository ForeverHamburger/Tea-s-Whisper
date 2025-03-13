package com.xuptggg.search.model;

public class TeaShowInfo {
    private String teaName;
    private int imageResId;

    public TeaShowInfo(String makeName, int imageResId) {
        this.teaName = makeName;
        this.imageResId = imageResId;
    }

    public String getTeaName() {
        return teaName;
    }


    public int getImageResId() {
        return imageResId;
    }
}

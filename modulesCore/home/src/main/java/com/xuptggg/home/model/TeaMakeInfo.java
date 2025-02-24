package com.xuptggg.home.model;

public class TeaMakeInfo {
    private String makeName;
    private String makeDetail;
    private int imageResId;

    public TeaMakeInfo(String makeName, String makeDetail, int imageResId) {
        this.makeName = makeName;
        this.makeDetail = makeDetail;
        this.imageResId = imageResId;
    }

    public String getMakeName() {
        return makeName;
    }

    public String getMakeDetail() {
        return makeDetail;
    }

    public int getImageResId() {
        return imageResId;
    }
}
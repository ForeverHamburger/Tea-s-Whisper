package com.xuptggg.home.model;

public class TeaInfo {
    private String name;
    private String origin;
    private int imageResId;

    public TeaInfo(String name, String origin, int imageResId) {
        this.name = name;
        this.origin = origin;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public int getImageResId() {
        return imageResId;
    }
}
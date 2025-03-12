package com.xuptggg.detail.model.infos;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class TeaInfo {
    @SerializedName("name")
    private String name;
    @SerializedName("detail")
    private String detail;

    private int imageResId;
    @SerializedName("image")
    private String imageAdd;

    public TeaInfo(String name, String detail, int imageResId) {
        this.name = name;
        this.detail = detail;
        this.imageResId = imageResId;
    }

    public TeaInfo(String name, String detail, String imageAdd) {
        this.name = name;
        this.detail = detail;
        this.imageAdd = imageAdd;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getImageResId() {
        return imageResId;
    }

    @Override
    public String toString() {
        return "TeaInfo{" +
                "name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", imageResId=" + imageResId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeaInfo teaInfo = (TeaInfo) o;
        return imageResId == teaInfo.imageResId && Objects.equals(name, teaInfo.name) && Objects.equals(detail, teaInfo.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, detail, imageResId);
    }
}
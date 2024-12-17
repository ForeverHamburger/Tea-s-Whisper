package com.xuptggg.guidepage.model;

public class GuideInfo {
    int colorResourse;
    int pictureResourse;
    String head;
    String subheading;
    String content;

    public GuideInfo(int colorResourse, int pictureResourse, String subheading, String head, String content) {
        this.colorResourse = colorResourse;
        this.pictureResourse = pictureResourse;
        this.subheading = subheading;
        this.head = head;
        this.content = content;
    }

    public int getColorResourse() {
        return colorResourse;
    }

    public void setColorResourse(int colorResourse) {
        this.colorResourse = colorResourse;
    }

    public int getPictureResourse() {
        return pictureResourse;
    }

    public void setPictureResourse(int pictureResourse) {
        this.pictureResourse = pictureResourse;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "GuideInfo{" +
                "colorResourse=" + colorResourse +
                ", pictureResourse=" + pictureResourse +
                ", head='" + head + '\'' +
                ", subheading='" + subheading + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

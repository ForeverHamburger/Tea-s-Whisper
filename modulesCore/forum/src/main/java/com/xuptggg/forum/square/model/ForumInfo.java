package com.xuptggg.forum.square.model;

public class ForumInfo {
    public static final String NOTE = "NOTE";
    public static final String VIDEO = "VIDEO";
    private String type;
    private int imageResource;
    private String title;
    private int headImage;
    private String userName;
    private String loveCount;

    public ForumInfo(String type, int imageResource, String title, int headImage, String userName, String loveCount) {
        this.type = type;
        this.imageResource = imageResource;
        this.title = title;
        this.headImage = headImage;
        this.userName = userName;
        this.loveCount = loveCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHeadImage() {
        return headImage;
    }

    public void setHeadImage(int headImage) {
        this.headImage = headImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(String loveCount) {
        this.loveCount = loveCount;
    }
}

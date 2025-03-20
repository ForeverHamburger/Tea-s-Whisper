package com.xuptggg.individual.edit.model;

public class BaseIndividualInfo {
    private String introduction;
    private String sex;
    private String url;
    private String username;

    public BaseIndividualInfo(String username, String sex, String url, String introduction) {
        this.introduction = introduction;
        this.sex = sex;
        this.url = url;
        this.username = username;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "BaseIndividualInfo{" +
                "introduction='" + introduction + '\'' +
                ", sex='" + sex + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

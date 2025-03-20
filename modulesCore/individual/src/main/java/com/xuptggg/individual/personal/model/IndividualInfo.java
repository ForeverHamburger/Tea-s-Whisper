package com.xuptggg.individual.personal.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class IndividualInfo {
    @SerializedName("UserID")
    String userId;
    @SerializedName("Username")
    String userName;
    @SerializedName("phone")
    String phone;
    @SerializedName("Email")
    String email;
    @SerializedName("URL")
    String url;
    @SerializedName("Introduction")
    String introduction;
    @SerializedName("Sex")
    String sex;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    @Override
    public String toString() {
        return "IndividualInfo{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", url='" + url + '\'' +
                ", introduction='" + introduction + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndividualInfo that = (IndividualInfo) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userName, that.userName) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(url, that.url) && Objects.equals(introduction, that.introduction) && Objects.equals(sex, that.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, phone, email, url, introduction, sex);
    }
}

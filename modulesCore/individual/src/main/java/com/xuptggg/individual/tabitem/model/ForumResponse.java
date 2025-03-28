package com.xuptggg.individual.tabitem.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForumResponse {
    private int code;
    private String msg;
    @SerializedName("data") // 显式指定JSON字段名
    private List<ForumInfo> forumList;

    // Getter
    public List<ForumInfo> getForumList() { return forumList; }
}
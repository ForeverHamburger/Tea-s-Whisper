package com.xuptggg.search.base.data;

import java.util.List;

public class BaseData<T>{
    private String tag;
    private List<T> info;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<T> getInfo() {
        return info;
    }

    public void setInfo(List<T> info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "tag='" + tag + '\'' +
                ", info=" + info +
                '}';
    }
}
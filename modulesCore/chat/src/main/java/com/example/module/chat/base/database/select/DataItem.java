package com.example.module.chat.base.database.select;

import com.example.module.chat.base.database.communicate.Data;

public class DataItem {
    private String title;
    private Data msg;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Data getMsg() {
        return msg;
    }

    public void setMsg(Data msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "title='" + title + '\'' +
                ", msg=" + msg +
                '}';
    }
}

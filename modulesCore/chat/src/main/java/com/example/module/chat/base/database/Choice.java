package com.example.module.chat.base.database;

public class Choice {
    private int index;
    private String finish_reason;
    private Message message;

    public int getIndex() {
        return index;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public Message getMessage() {
        return message;
    }
}

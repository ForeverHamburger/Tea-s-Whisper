package com.example.module.chat.base.database;

import java.util.List;

class Data {
    private String id;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private Object content_filter;

    public String getId() {
        return id;
    }

    public long getCreated() {
        return created;
    }

    public String getModel() {
        return model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public Object getContent_filter() {
        return content_filter;
    }
}
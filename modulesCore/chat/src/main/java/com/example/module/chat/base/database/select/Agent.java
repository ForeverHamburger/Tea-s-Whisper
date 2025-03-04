package com.example.module.chat.base.database.select;

public class Agent {
    private String id;
    private String name;
    private int icon;  // 图标资源ID
    private String description;

    public Agent(String id, String name, int icon, String description) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getIcon() { return icon; }
    public String getDescription() { return description; }
}
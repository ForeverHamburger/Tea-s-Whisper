package com.example.module.chat.communicate.base;

import java.util.Date;

public class ChatHistory {
    private String title;
    private String content;
    private Date timestamp;
    private String role;
    private String SessionID;
    private CharSequence formattedContent; // 格式化后的内容（缓存）

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

    public CharSequence getFormattedContent() {
        return formattedContent;
    }

    public void setFormattedContent(CharSequence formattedContent) {
        this.formattedContent = formattedContent;
    }

    @Override
    public String toString() {
        return "ChatHistory{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", role='" + role + '\'' +
                '}';
    }
}
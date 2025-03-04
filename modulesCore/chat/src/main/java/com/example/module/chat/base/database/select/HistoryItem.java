package com.example.module.chat.base.database.select;

public class HistoryItem {
    private ChatContent ChatContent;
    private long timestamp;
    private String agentId;
    private String sessionId;

    public HistoryItem(long timestamp, String agentId, String sessionId, ChatContent ChatContent) {
        this.timestamp = timestamp;
        this.agentId = agentId;
        this.sessionId = sessionId;
        this.ChatContent = ChatContent;
    }

    // Getter 方法
    public long getTimestamp() {
        return timestamp;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
package com.example.module.chat.communicate.base;

public class ChatMessage {
    public static final int TYPE_SENT = 0;    // 用户发送
    public static final int TYPE_RECEIVED = 1; // AI回复
    public static final int TYPE_THINKING = 2; // 新增类型

    private int type;       // 消息类型
    private String content; // 消息内容
    private long timestamp; // 时间戳
    private CharSequence formattedContent; // 格式化后的内容（缓存）
    public ChatMessage(int type, String content) {
        this.type = type;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }
    public ChatMessage(int type, String content,long timestamp) {
        this.type = type;
        this.content = content;
        this.timestamp = timestamp;
    }
    public int getType() { return type; }
    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }
    public CharSequence getFormattedContent() {
        return formattedContent;
    }

    public void setFormattedContent(CharSequence formattedContent) {
        this.formattedContent = formattedContent;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", formattedContent=" + formattedContent +
                '}';
    }
}
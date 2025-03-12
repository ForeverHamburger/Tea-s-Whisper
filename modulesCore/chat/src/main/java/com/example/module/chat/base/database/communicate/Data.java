package com.example.module.chat.base.database.communicate;

import java.util.Date;
import java.util.List;

public  class Data {
    private Long ID;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;
    private String MessageID;
    private String SessionID;
    private Long UserID;
    private String Content;
    private Date Timestamp;
    private String Role;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        DeletedAt = deletedAt;
    }

    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

    public Long getUserID() {
        return UserID;
    }

    public void setUserID(Long userID) {
        UserID = userID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "Data{" +
                "ID=" + ID +
                ", CreatedAt=" + CreatedAt +
                ", UpdatedAt=" + UpdatedAt +
                ", DeletedAt=" + DeletedAt +
                ", MessageID='" + MessageID + '\'' +
                ", SessionID='" + SessionID + '\'' +
                ", UserID=" + UserID +
                ", Content='" + Content + '\'' +
                ", Timestamp=" + Timestamp +
                ", Role='" + Role + '\'' +
                '}';
    }
}
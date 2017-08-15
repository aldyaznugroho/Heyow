package com.skripsigg.heyow.models;

/**
 * Created by Aldyaz on 12/24/2016.
 */

public class MessageChatModel {
    private String userId;
    private String userName;
    private String messageContent;
    private String messageDate;
    private String messageTime;
    private int senderOrRecipientStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public int getSenderOrRecipientStatus() {
        return senderOrRecipientStatus;
    }

    public void setSenderOrRecipientStatus(int senderOrRecipientStatus) {
        this.senderOrRecipientStatus = senderOrRecipientStatus;
    }
}
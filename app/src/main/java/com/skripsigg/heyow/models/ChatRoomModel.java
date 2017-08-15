package com.skripsigg.heyow.models;

import java.util.List;

/**
 * Created by Aldyaz on 12/23/2016.
 */

public class ChatRoomModel {
    private String chatId;
    private String matchId;
    private List<MessageChatModel> message;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public List<MessageChatModel> getMessage() {
        return message;
    }

    public void setMessage(List<MessageChatModel> message) {
        this.message = message;
    }
}

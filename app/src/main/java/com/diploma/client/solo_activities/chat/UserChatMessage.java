package com.diploma.client.solo_activities.chat;

import java.util.Date;


public class UserChatMessage {
    public Integer receiverId;
    public Integer senderId;
    public Date serverReceivedTime;
    public String text;

    public UserChatMessage(Integer receiverId, Integer senderId, Date serverReceivedTime, String  text){
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.serverReceivedTime = serverReceivedTime;
        this.text = text;
    }
}

package com.diploma.client.data.model;

import java.util.Date;


public class ChatMessage {
    public Integer receiverId;
    public Integer senderId;
    public Date serverReceivedTime;
    public String text;

    public ChatMessage(Integer receiverId, Integer senderId, Date serverReceivedTime, String  text){
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.serverReceivedTime = serverReceivedTime;
        this.text = text;
    }
}

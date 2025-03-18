/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author PC
 */
public class ChatMessages {
    private int messageID;
    private int senderID;
    private String senderType;
    private int receiverID;
    private String receiverType;
    private String messageText;
    private Date timestamp;
    
   

    public ChatMessages() {
    }

    public ChatMessages(int senderID, String senderType, int receiverID, String receiverType, String messageText, Date timestamp) {
        this.senderID = senderID;
        this.senderType = senderType;
        this.receiverID = receiverID;
        this.receiverType = receiverType;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    
    
    
    public ChatMessages(int messageID, int senderID, String senderType, int receiverID, String receiverType, String messageText, Date timestamp) {
        this.messageID = messageID;
        this.senderID = senderID;
        this.senderType = senderType;
        this.receiverID = receiverID;
        this.receiverType = receiverType;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    
    
}

package Model;

import java.util.Date;

public class Notification {
    private int notificationID;
    private String recipientType; // 'Customer' hoặc 'Staff'
    private int recipientID;
    private String message;
    private Date createdDate;
    private boolean isRead;
    private String target; // Nơi thông báo muốn chuyển đến

    // Getters và Setters
    public int getNotificationID() { return notificationID; }
    public void setNotificationID(int notificationID) { this.notificationID = notificationID; }
    public String getRecipientType() { return recipientType; }
    public void setRecipientType(String recipientType) { this.recipientType = recipientType; }
    public int getRecipientID() { return recipientID; }
    public void setRecipientID(int recipientID) { this.recipientID = recipientID; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    public boolean isRead() { return isRead; }
    public void setIsRead(boolean isRead) { this.isRead = isRead; }
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    public boolean isIsRead() {
        return isRead;
    }

    @Override
    public String toString() {
        return "Notification{" + "notificationID=" + notificationID + ", recipientType=" + recipientType + ", recipientID=" + recipientID + ", message=" + message + ", createdDate=" + createdDate + ", isRead=" + isRead + ", target=" + target + '}';
    }
    
    
    
    
}
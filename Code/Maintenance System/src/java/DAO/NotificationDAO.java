package DAO;

import Model.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

public class NotificationDAO extends DBContext {

    public boolean addNotification(Notification notification) {
        String sql = "INSERT INTO Notifications (RecipientType, RecipientID, Message, CreatedDate, IsRead, Target) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, notification.getRecipientType());
            ps.setInt(2, notification.getRecipientID());
            ps.setString(3, notification.getMessage());
            ps.setTimestamp(4, new java.sql.Timestamp(notification.getCreatedDate().getTime()));
            ps.setBoolean(5, notification.isRead());
            ps.setString(6, notification.getTarget());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Notification> getUnreadNotifications(String recipientType, int recipientID, int timeoutSeconds) {
        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeoutSeconds * 1000;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            List<Notification> notifications = new ArrayList<>();
            String sql = "SELECT * FROM Notifications WHERE RecipientType = ? AND RecipientID = ? AND IsRead = 0  ORDER BY CreatedDate DESC";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, recipientType);
                ps.setInt(2, recipientID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Notification notification = new Notification();
                    notification.setNotificationID(rs.getInt("NotificationID"));
                    notification.setRecipientType(rs.getString("RecipientType"));
                    notification.setRecipientID(rs.getInt("RecipientID"));
                    notification.setMessage(rs.getString("Message"));
                    notification.setCreatedDate(rs.getTimestamp("CreatedDate"));
                    notification.setIsRead(rs.getBoolean("IsRead"));
                    notification.setTarget(rs.getString("Target"));
                    notifications.add(notification);
                }
                if (!notifications.isEmpty()) {
                    return notifications; // Trả về ngay khi có thông báo mới
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Đợi 1 giây trước khi kiểm tra lại
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ArrayList<>();
            }
        }
        return new ArrayList<>(); // Timeout, trả về rỗng
    }

    public boolean markAsRead(int notificationID, String recipientType, int recipientID) {
        String sql = "UPDATE Notifications SET IsRead = 1 WHERE NotificationID = ? AND RecipientType = ? AND RecipientID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, notificationID);
            ps.setString(2, recipientType);
            ps.setInt(3, recipientID);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

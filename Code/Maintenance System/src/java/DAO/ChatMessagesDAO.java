/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ChatMessages;
import java.sql.Timestamp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class ChatMessagesDAO extends DBContext {

    public void addMessages(int senderId, String senderType, int recieverID, String receiverType, String messageText) {
        String sql = "INSERT INTO [dbo].[ChatMessages]\n"
                + "           ([SenderID]\n"
                + "           ,[SenderType]\n"
                + "           ,[ReceiverID]\n"
                + "           ,[ReceiverType]\n"
                + "           ,[MessageText]\n"
                + "           ,[Timestamp])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setString(2, senderType);
            ps.setInt(3, recieverID);
            ps.setString(4, receiverType);
            ps.setString(5, messageText);
            ps.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public ArrayList<ChatMessages> getMessagesByReceiverAndSender(String senderType, String receiverType, String senderName, String receiverName,
        String sortBy, String sortOrder, int offset, int fetch) {
    ArrayList<ChatMessages> listChatMessages = new ArrayList<>();

    String sql = "SELECT cm.[MessageID], cm.[SenderID], cm.[SenderType], cm.[ReceiverID], cm.[ReceiverType], "
            + "cm.[MessageText], cm.[Timestamp], "
            + "COALESCE(c1.Name, s1.Name) AS SenderName, COALESCE(c2.Name, s2.Name) AS ReceiverName "
            + "FROM [dbo].[ChatMessages] cm "
            + "LEFT JOIN [dbo].[customer] c1 ON cm.SenderType = 'customer' AND cm.SenderID = c1.CustomerID "
            + "LEFT JOIN [dbo].[staff] s1 ON cm.SenderType = 'staff' AND cm.SenderID = s1.StaffID "
            + "LEFT JOIN [dbo].[customer] c2 ON cm.ReceiverType = 'customer' AND cm.ReceiverID = c2.CustomerID "
            + "LEFT JOIN [dbo].[staff] s2 ON cm.ReceiverType = 'staff' AND cm.ReceiverID = s2.StaffID "
            + "WHERE 1=1";

    if (senderType != null && !senderType.trim().isEmpty()) {
        sql += " AND cm.SenderType LIKE ?";
    }
    if (receiverType != null && !receiverType.trim().isEmpty()) {
        sql += " AND cm.ReceiverType LIKE ?";
    }
    if (senderName != null && !senderName.trim().isEmpty()) {
        sql += " AND (c1.Name LIKE ? OR s1.Name LIKE ?)";
    }
    if (receiverName != null && !receiverName.trim().isEmpty()) {
        sql += " AND (c2.Name LIKE ? OR s2.Name LIKE ?)";
    }

    if (sortBy != null && !sortBy.trim().isEmpty()) {
        sql += " ORDER BY " + sortBy + " " + (sortOrder != null && sortOrder.equalsIgnoreCase("DESC") ? "DESC" : "ASC");
    } else {
        sql += " ORDER BY cm.MessageID ASC";
    }

    sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        int index = 1;

        if (senderType != null && !senderType.trim().isEmpty()) {
            ps.setString(index++, "%" + senderType.trim() + "%");
        }
        if (receiverType != null && !receiverType.trim().isEmpty()) {
            ps.setString(index++, "%" + receiverType.trim() + "%");
        }
        if (senderName != null && !senderName.trim().isEmpty()) {
            ps.setString(index++, "%" + senderName.trim() + "%");
            ps.setString(index++, "%" + senderName.trim() + "%");
        }
        if (receiverName != null && !receiverName.trim().isEmpty()) {
            ps.setString(index++, "%" + receiverName.trim() + "%");
            ps.setString(index++, "%" + receiverName.trim() + "%");
        }

        ps.setInt(index++, offset);
        ps.setInt(index++, fetch);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ChatMessages chatMessage = new ChatMessages();
                chatMessage.setMessageID(rs.getInt("MessageID"));
                chatMessage.setSenderID(rs.getInt("SenderID"));
                chatMessage.setSenderType(rs.getString("SenderType"));
                chatMessage.setReceiverID(rs.getInt("ReceiverID"));
                chatMessage.setReceiverType(rs.getString("ReceiverType"));
                chatMessage.setMessageText(rs.getString("MessageText"));
                chatMessage.setTimestamp(rs.getDate("Timestamp"));
                chatMessage.setSenderName(rs.getString("SenderName"));
                chatMessage.setReceiverName(rs.getString("ReceiverName"));
                listChatMessages.add(chatMessage);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return listChatMessages;
}



public int getChatPage(String senderType, String receiverType, String senderName, String receiverName) {
    String sql = "SELECT COUNT(*) FROM ("
            + "    SELECT cm.MessageID, "
            + "           COALESCE(c1.Name, s1.Name) AS SenderName, "
            + "           COALESCE(c2.Name, s2.Name) AS ReceiverName "
            + "    FROM [dbo].[ChatMessages] cm "
            + "    LEFT JOIN [dbo].[customer] c1 ON cm.SenderType = 'customer' AND cm.SenderID = c1.CustomerID "
            + "    LEFT JOIN [dbo].[staff] s1 ON cm.SenderType = 'staff' AND cm.SenderID = s1.StaffID "
            + "    LEFT JOIN [dbo].[customer] c2 ON cm.ReceiverType = 'customer' AND cm.ReceiverID = c2.CustomerID "
            + "    LEFT JOIN [dbo].[staff] s2 ON cm.ReceiverType = 'staff' AND cm.ReceiverID = s2.StaffID "
            + ") AS subquery WHERE 1=1";

    if (senderName != null && !senderName.trim().isEmpty()) {
        sql += " AND SenderName LIKE ?";
    }
    if (receiverName != null && !receiverName.trim().isEmpty()) {
        sql += " AND ReceiverName LIKE ?";
    }

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        int index = 1;
        if (senderName != null && !senderName.trim().isEmpty()) {
            ps.setString(index++, "%" + senderName.trim() + "%");
        }
        if (receiverName != null && !receiverName.trim().isEmpty()) {
            ps.setString(index++, "%" + receiverName.trim() + "%");
        }

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}


    public static void main(String[] args) {
        ChatMessagesDAO chat = new ChatMessagesDAO();
        int a = chat.getChatPage("staff", "", "", "");
        System.out.println(a);
    }
}

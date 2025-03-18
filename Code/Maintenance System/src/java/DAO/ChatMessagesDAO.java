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

    public ArrayList<ChatMessages> getMassagesByReceiverAndSender(int senderID, int recieverID) {
        ArrayList<ChatMessages> listChatMessages = new ArrayList<ChatMessages>();
        String sql = "SELECT [MessageID]\n"
                + "      ,[SenderID]\n"
                + "      ,[SenderType]\n"
                + "      ,[ReceiverID]\n"
                + "      ,[ReceiverType]\n"
                + "      ,[MessageText]\n"
                + "      ,[Timestamp]\n"
                + "  FROM [dbo].[ChatMessages]\n"
                + "  WHERE (senderID=? AND receiverID= ?) OR (senderID=? AND receiverID=?)\n"
                + "  ORDER BY timestamp ASC;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, senderID);
            ps.setInt(2, recieverID);
            ps.setInt(3, recieverID);
            ps.setInt(4, senderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatMessages chatMessage = new ChatMessages();
                chatMessage.setMessageID(rs.getInt("MessageID"));
                chatMessage.setSenderID(rs.getInt("SenderID"));
                chatMessage.setSenderType(rs.getString("SenderType"));
                chatMessage.setReceiverID(rs.getInt("ReceiverID"));
                chatMessage.setReceiverType(rs.getString("ReceiverType"));
                chatMessage.setTimestamp(rs.getTimestamp("Timestamp"));
                listChatMessages.add(chatMessage);

            }

        } catch (SQLException e) {

        }
        return listChatMessages;
    }
    
    
    
    public static void main(String[] args) {
        ChatMessagesDAO chat = new ChatMessagesDAO();
        int a1 = 1;
        String at = "admin";
        int a2 = 2;
        String bt = "customer";
        String mes="hahahah";
        
        chat.addMessages(a1, at, a2, bt, mes);
    }
}

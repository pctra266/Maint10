/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Feedback;
import Model.FeedbackLog;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Tra Pham
 */
public class FeedbackLogDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public ArrayList<FeedbackLog> getAllFeedbackLog(String action, int index) {
        ArrayList<FeedbackLog> list = new ArrayList<>();
        String query = "select * from FeedbackLog where 1=1";
        if (action != null && !action.trim().isEmpty()) {
            if (action.equalsIgnoreCase("update")) {
                query += " and Action like 'update'";
            } else {
                query += " and Action like 'delete'";
            }
        }
        query += " order by DateModified desc";
        
            query += " offset ? rows  fetch next 7 rows only;";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count =1;
                ps.setInt(count++, (index -1)* 7);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new FeedbackLog(rs.getInt("FeedbackLogID"), rs.getInt("FeedbackID"),
                        rs.getString("Action"), rs.getString("OldFeedbackText"),
                        rs.getString("NewFeedbackText"), rs.getInt("ModifiedBy"),
                        rs.getDate("DateModified")));
            }
        } catch (Exception e) {

        }

        return list;
    }
    public ArrayList<FeedbackLog> getAllFeedbackLog(String action, String feedbackID, String column, String sortOrder,int page, int pageSize) {
        ArrayList<FeedbackLog> list = new ArrayList<>();
        String query = "select * from FeedbackLog where 1=1";
        if (action != null && !action.trim().isEmpty()) {
            if (action.equalsIgnoreCase("update")) {
                query += " and Action like 'update'";
            } else {
                query += " and Action like 'delete'";
            }
        }
        if(feedbackID != null && !feedbackID.trim().isEmpty()){
            query += " and FeedbackID = ?";
        }
        if(column != null && !column.trim().isEmpty()){
             query += " order by " + column + " ";
            if(sortOrder != null && !sortOrder.trim().isEmpty()){
                query += sortOrder;
            }
        }else{
            query += " order by DateModified desc";
        }
            query += " offset ? rows  fetch next ? rows only;";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count =1;
            if(feedbackID != null && !feedbackID.trim().isEmpty()){
            ps.setString(count++, feedbackID);
        }
            int offset = (page - 1) * pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new FeedbackLog(rs.getInt("FeedbackLogID"), rs.getInt("FeedbackID"),
                        rs.getString("Action"), rs.getString("OldFeedbackText"),
                        rs.getString("NewFeedbackText"), rs.getInt("ModifiedBy"),
                        rs.getDate("DateModified")));
            }
        } catch (Exception e) {

        }

        return list;
    }

    public void createDeleteFeedbackLog(Feedback feedback,String staffId) {
        String query = "INSERT INTO FeedbackLog (FeedbackID, [Action], OldFeedbackText, NewFeedbackText, ModifiedBy, DateModified)\n"
                + "VALUES\n"
                + "  (?, 'delete', ?, ?, ?, GETDATE())";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, String.valueOf(feedback.getFeedbackID()));
            ps.setString(2, String.valueOf(feedback.getNote()));
            ps.setString(3, "");
            ps.setString(4, staffId);
            ps.executeUpdate();
        } catch (Exception e) {

        }
    }
    public void createUpdateFeedbackLog(Feedback feedback,String staffId, String newNote) {
        String query = "INSERT INTO FeedbackLog (FeedbackID, [Action], OldFeedbackText, NewFeedbackText, ModifiedBy, DateModified)\n"
                + "VALUES\n"
                + "  (?, 'update', ?, ?, ?, GETDATE())";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, String.valueOf(feedback.getFeedbackID()));
            ps.setString(2, String.valueOf(feedback.getNote()));
            ps.setString(3, newNote);
            ps.setString(4, staffId);
            ps.executeUpdate();
        } catch (Exception e) {

        }
    }
    public void deleteFeedbackLogById(String feedbackLogId){
        String query = "delete from FeedbackLog where FeedbackLogID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, feedbackLogId);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
    public FeedbackLog getFeedbackLogById(String feedbackLogId){
        String query = "select * from FeedbackLog where FeedbackLogID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, feedbackLogId);
            rs = ps.executeQuery();
            while(rs.next()){
                return  (new FeedbackLog(rs.getInt("FeedbackLogID"), rs.getInt("FeedbackID"),
                        rs.getString("Action"), rs.getString("OldFeedbackText"),
                        rs.getString("NewFeedbackText"), rs.getInt("ModifiedBy"),
                        rs.getDate("DateModified")));
            }
               
        } catch (Exception e) {

        }
        return null;
    }
    
    public int getTotalFeedbackLog(String actionOfLog,String feedbackID){
         String query = "select count(*)  from FeedbackLog where 1=1";
          if (actionOfLog != null && !actionOfLog.trim().isEmpty()) {
            if (actionOfLog.equalsIgnoreCase("update")) {
                query += " and Action like 'update'";
            } else {
                query += " and Action like 'delete'";
            }
        }
          if(feedbackID != null && !feedbackID.trim().isEmpty()){
            query += " and FeedbackID = ?";
        }
         try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count =1;
             if(feedbackID != null && !feedbackID.trim().isEmpty()){
                 ps.setString(count++, feedbackID);
             }
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
         return 0;
    }

    public static void main(String[] args) {
        FeedbackLogDAO dao = new FeedbackLogDAO();
        ArrayList<FeedbackLog> list = dao.getAllFeedbackLog("","","","", 1,5);
        for (FeedbackLog feedbackLog : list) {
            System.out.println(feedbackLog);
        }
        System.out.println(dao.getTotalFeedbackLog("update", "6"));
//        System.out.println(dao.getTotalFeedbackLog());
//            FeedbackLog f = dao.getFeedbackLogById("16");
//            System.out.println(f);
    }
}

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

    public ArrayList<FeedbackLog> getAllFeedbackLog() {
        ArrayList<FeedbackLog> list = new ArrayList<>();
        String query = "select * from FeedbackLog";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
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

    public static void main(String[] args) {
        FeedbackLogDAO dao = new FeedbackLogDAO();
//        ArrayList<FeedbackLog> list = dao.getAllFeedbackLog();
//        for (FeedbackLog feedbackLog : list) {
//            System.out.println(feedbackLog);
//        }
            FeedbackLog f = dao.getFeedbackLogById("16");
            System.out.println(f);
    }
}

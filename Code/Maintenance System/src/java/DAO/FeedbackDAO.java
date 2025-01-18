/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Feedback;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Tra Pham
 */
public class FeedbackDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public ArrayList<Feedback> getAllFeedback() {
        ArrayList<Feedback> list = new ArrayList<>();
        String query = "select f.FeedbackID,f.CustomerID,c.Name as CustomerName, f.DateCreated ,f.WarrantyCardID, "
                + "pr.ProductName,w.IssueDescription,\n"
                + "w.WarrantyStatus, f.Note, f.ImageURL, f.VideoURL, f.IsDeleted\n"
                + "from Feedback f \n"
                + "left join WarrantyCard w on f.WarrantyCardID = w.WarrantyCardID\n"
                + "left join ProductDetail p on w.ProductDetailID = p.ProductDetailID\n"
                + "left join Product pr on p.ProductID = pr.ProductID\n"
                + "left join Customer c on f.CustomerID = c.CustomerID";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(rs.getInt("FeedbackID"), rs.getInt("CustomerID"), rs.getInt("WarrantyCardID"),
                        rs.getString("Note"), rs.getString("CustomerName"), rs.getString("ImageURL"),
                        rs.getString("VideoURL"), rs.getString("ProductName"),
                        rs.getString("IssueDescription"), rs.getString("WarrantyStatus"),
                        rs.getDate("DateCreated"), rs.getBoolean("IsDeleted")));

            }
        } catch (Exception e) {

        }

        return list;
    }

    public void deleteFeedbackById(String feedbackId) {
        String query = "delete from Feedback where FeedbackID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, feedbackId);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public Feedback getFeedbackById(String feedbackId) {
        String query = "select f.FeedbackID,f.CustomerID,c.Name as CustomerName, f.DateCreated ,f.WarrantyCardID, pr.ProductName,w.IssueDescription,\n"
                + "w.WarrantyStatus, f.Note, f.ImageURL, f.VideoURL, f.IsDeleted\n"
                + "from Feedback f \n"
                + "left join WarrantyCard w on f.WarrantyCardID = w.WarrantyCardID\n"
                + "left join ProductDetail p on w.ProductDetailID = p.ProductDetailID\n"
                + "left join Product pr on p.ProductID = pr.ProductID\n"
                + "left join Customer c on f.CustomerID = c.CustomerID\n"
                + "where f.FeedbackID = ?";

        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, feedbackId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new Feedback(rs.getInt("FeedbackID"), rs.getInt("CustomerID"), rs.getInt("WarrantyCardID"),
                        rs.getString("Note"), rs.getString("CustomerName"), rs.getString("ImageURL"),
                        rs.getString("VideoURL"), rs.getString("ProductName"),
                        rs.getString("IssueDescription"), rs.getString("WarrantyStatus"),
                        rs.getDate("DateCreated"), rs.getBoolean("IsDeleted"));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void updateFeedback(String feedbackId, String note) {
        String query = "update Feedback\n"
                + "set Note = ?\n"
                + "where FeedbackID = ?";

        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, note);
            ps.setString(2, feedbackId);
            ps.executeQuery();

        } catch (Exception e) {
        }
    }

    public void createFeedback(String customerId, String warrantyCardId, String note) {
        String query = "INSERT INTO Feedback (CustomerID, WarrantyCardID, Note)\n"
                + "VALUES\n"
                + "(?, ?, ?)";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, customerId);
            ps.setString(2, warrantyCardId);
            ps.setString(3, note);
            ps.executeQuery();

        } catch (Exception e) {
        }
    }
    
    public void inActiveFeedbackById(String feedbackId){
        String query = "update Feedback\n"
                + "set IsDeleted = 1\n"
                + "where FeedbackID = ?";

        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, feedbackId);
            ps.executeQuery();

        } catch (Exception e) {
        }
    }
    public void activeFeedbackById(String feedbackId){
        String query = "update Feedback\n"
                + "set IsDeleted = 0\n"
                + "where FeedbackID = ?";

        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, feedbackId);
            ps.executeQuery();

        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        FeedbackDAO dao = new FeedbackDAO();
//        dao.deleteFeedbackById("3");
//        Feedback f = dao.getFeedbackById("5");
//        System.out.println(f);
        ArrayList<Feedback> listFeedback = dao.getAllFeedback();
        for (Feedback feedback : listFeedback) {
            System.out.println(feedback);
        }
    }
}

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
    // paging and searching

    public ArrayList<Feedback> getAllFeedback(String customerName, String customerEmail, String customerPhone, String hasImageAndVideo, int index, String column, String sortOrder) {
        ArrayList<Feedback> list = new ArrayList<>();
        String query = "select f.FeedbackID,f.CustomerID,c.Name as CustomerName, c.Email as CustomerEmail, c.Phone as CustomerPhoneNumber, f.DateCreated ,f.WarrantyCardID, \n"
                + "                pr.ProductName,w.IssueDescription,\n"
                + "                w.WarrantyStatus, f.Note, f.ImageURL, f.VideoURL, f.IsDeleted\n"
                + "                from Feedback f \n"
                + "               left join WarrantyCard w on f.WarrantyCardID = w.WarrantyCardID\n"
                + "                left join ProductDetail p on w.ProductDetailID = p.ProductDetailID\n"
                + "                left join Product pr on p.ProductID = pr.ProductID\n"
                + "                left join Customer c on f.CustomerID = c.CustomerID\n"
                + "                 where f.IsDeleted = 0 ";
        if (customerName != null && !customerName.trim().isEmpty()) {
            query += " and c.Name like ?";
        }
        if (customerEmail != null && !customerEmail.trim().isEmpty()) {
            query += " and c.Email like ?";
        }
        if (customerPhone != null && !customerPhone.trim().isEmpty()) {
            query += " and c.Phone like ?";
        }
        if (hasImageAndVideo != null && !hasImageAndVideo.trim().isEmpty()) {
            if (hasImageAndVideo.equalsIgnoreCase("empty")) {
                query += " and (f.VideoURL is null and f.ImageURL is null)";
            } else {
                query += " and (f.VideoURL is not null or f.ImageURL is not null )";
            }
        }
        if (column != null && !column.trim().isEmpty()) {
            query += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                query += sortOrder;
            }
        } else {
            query += " order by DateCreated asc\n";
        }
        query += " offset ? rows  fetch next 7 rows only;";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if (customerName != null && !customerName.trim().isEmpty()) {
                ps.setString(count++, "%" + customerName.trim() + "%");
            }
            if (customerEmail != null && !customerEmail.trim().isEmpty()) {
                ps.setString(count++, customerEmail);
            }
            if (customerPhone != null && !customerPhone.trim().isEmpty()) {
                ps.setString(count++, customerPhone);
            }
            ps.setInt(count++, (index - 1) * 7);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Feedback(rs.getInt("FeedbackID"), rs.getInt("CustomerID"), rs.getInt("WarrantyCardID"),
                        rs.getString("Note"), rs.getString("CustomerName"), rs.getString("CustomerEmail"),
                        rs.getString("CustomerPhoneNumber"), rs.getString("ImageURL"),
                        rs.getString("VideoURL"), rs.getString("ProductName"),
                        rs.getString("IssueDescription"), rs.getString("WarrantyStatus"),
                        rs.getDate("DateCreated"), rs.getBoolean("IsDeleted")));

            }
        } catch (Exception e) {

        }

        return list;
    }

    public ArrayList<Feedback> getListFeedbackByCustomerId(String customerId,int page, int pageSize) {
        ArrayList<Feedback> list = new ArrayList<>();
        String query = "select f.FeedbackID, f.CustomerID, f.Note, f.DateCreated, f.ImageURL, f.VideoURL\n"
                + "	from Feedback f\n"
                + "	where f.IsDeleted = 0";
        if (customerId != null && !customerId.trim().isEmpty()) {
            query += " and f.CustomerID like ?";
        }
        query += " order by DateCreated desc ";
        query += " offset ? rows  fetch next ? rows only;";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if (customerId != null && !customerId.trim().isEmpty()) {
                ps.setString(count++, customerId.trim());
                int offset = (page - 1) * pageSize;
                ps.setInt(count++, offset);
                ps.setInt(count++, pageSize);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Feedback f = new Feedback();
                    f.setFeedbackID(rs.getInt("FeedbackID"));
                    f.setCustomerID(rs.getInt("CustomerID"));
                    f.setNote(rs.getString("Note"));
                    f.setDateCreated(rs.getDate("DateCreated"));
                    f.setImageURL(rs.getString("ImageURL"));
                    f.setVideoURL(rs.getString("VideoURL"));
                    list.add(f);
                }
            }

        } catch (Exception e) {
        }
        return list;
    }

    public int totalFeedbackByCustomerId(String customerId) {
        String query = """
                        select count (*) 
                       \t\t\t\t\t\tfrom Feedback f
                       \t\t\t\t\t\twhere f.IsDeleted = 0""";
        if (customerId != null && !customerId.trim().isEmpty()) {
            query += " and f.CustomerID like ?";
        }
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if (customerId != null && !customerId.trim().isEmpty()) {
                ps.setString(count++, customerId.trim());
                rs = ps.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
        }
        return 0;
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
        String query = "select f.FeedbackID,f.CustomerID,c.Name as CustomerName, c.Email as CustomerEmail, c.Phone as CustomerPhoneNumber, f.DateCreated ,f.WarrantyCardID, pr.ProductName,w.IssueDescription,\n"
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
                        rs.getString("Note"), rs.getString("CustomerName"), rs.getString("CustomerEmail"), rs.getString("CustomerPhoneNumber"), rs.getString("ImageURL"),
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

    public void createFeedback(String customerId, String warrantyCardId, String note, String imageURL, String videoUrl) {
        String query = "INSERT INTO Feedback (CustomerID, WarrantyCardID, Note, DateCreated, IsDeleted, ImageURL, VideoURL) "
                + "VALUES (?, ?, ?, GETDATE(), 0, ?, ?)";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            ps.setString(count++, customerId);

            if (warrantyCardId == null || warrantyCardId.trim().isEmpty()) {
                ps.setNull(count++, java.sql.Types.VARCHAR);
            } else {
                ps.setString(count++, warrantyCardId);
            }

            if (note == null || note.trim().isEmpty()) {
                ps.setNull(count++, java.sql.Types.VARCHAR);
            } else {
                ps.setString(count++, note);
            }
            if (imageURL == null || imageURL.trim().isEmpty()) {
                ps.setNull(count++, java.sql.Types.VARCHAR);
            } else {
                ps.setString(count++, imageURL);
            }
            if (videoUrl == null || videoUrl.trim().isEmpty()) {
                ps.setNull(count++, java.sql.Types.VARCHAR);
            } else {
                ps.setString(count++, videoUrl);
            }

            ps.executeUpdate();

        } catch (Exception e) {
        }
    }

    public void inActiveFeedbackById(String feedbackId) {
        String query = "update Feedback\n"
                + "set IsDeleted = 1\n"
                + "where FeedbackID = ?";

        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, feedbackId);
            ps.executeUpdate();

        } catch (Exception e) {
        }
    }

    public void activeFeedbackById(String feedbackId) {
        String query = "update Feedback\n"
                + "set IsDeleted = 0\n"
                + "where FeedbackID = ?";

        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, feedbackId);
            ps.executeUpdate();

        } catch (Exception e) {
        }
    }

    public int getTotalFeedback(String customerName, String customerEmail, String customerPhone, String hasImageAndVideo) {
        String query = "select count(*) from Feedback f\n"
                + "                              left join WarrantyCard w on f.WarrantyCardID = w.WarrantyCardID\n"
                + "                                left join ProductDetail p on w.ProductDetailID = p.ProductDetailID\n"
                + "                               left join Product pr on p.ProductID = pr.ProductID\n"
                + "                                left join Customer c on f.CustomerID = c.CustomerID\n"
                + "                                 where f.IsDeleted = 0";
        if (customerName != null && !customerName.trim().isEmpty()) {
            query += " and c.Name like ?";
        }
        if (customerEmail != null && !customerEmail.trim().isEmpty()) {
            query += " and c.Email like ?";
        }
        if (customerPhone != null && !customerPhone.trim().isEmpty()) {
            query += " and c.Phone like ?";
        }
        if (hasImageAndVideo != null && !hasImageAndVideo.trim().isEmpty()) {
            if (hasImageAndVideo.equalsIgnoreCase("empty")) {
                query += " and (f.VideoURL is null and f.ImageURL is null)";
            } else {
                query += " and (f.VideoURL is not null or f.ImageURL is not null )";
            }
        }
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if (customerName != null && !customerName.trim().isEmpty()) {
                ps.setString(count++, "%" + customerName.trim() + "%");
            }
            if (customerEmail != null && !customerEmail.trim().isEmpty()) {
                ps.setString(count++, customerEmail);
            }
            if (customerPhone != null && !customerPhone.trim().isEmpty()) {
                ps.setString(count++, customerPhone);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        FeedbackDAO dao = new FeedbackDAO();
//        ArrayList<Feedback> list = dao.getAllFeedback("", "", 1,"CustomerName","");
//        for (Feedback feedback : list) {
//            System.out.println(feedback);
//        }
//        ArrayList<Feedback> list = dao.getListFeedbackByCustomerId("1");
//                for (Feedback feedback : list) {
//            System.out.println(feedback);
//        }
//        dao.createFeedback("1", "", "toi tao mot feedback ", "", "");
        System.out.println(dao.totalFeedbackByCustomerId("1"));
    }
}

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

/**
 *
 * @author Tra Pham
 */
public class FeedbackDAO {
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
 public ArrayList<Feedback> getAllFeedback(){
        ArrayList<Feedback> list = new ArrayList<>();
        String query = "select * from Feedback";
        try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Feedback(rs.getInt("FeedbackID"), rs.getInt("CustomerID"), 
                        rs.getInt("WarrantyCardID"), rs.getString("Note")));
            }
        }catch (Exception e){
        }
        
        return list;
    }
 
    public static void main(String[] args) {
        FeedbackDAO dao = new FeedbackDAO();
        ArrayList<Feedback> listFeedback = dao.getAllFeedback();
        for (Feedback feedback : listFeedback) {
            System.out.println(feedback);
        }
    }
}

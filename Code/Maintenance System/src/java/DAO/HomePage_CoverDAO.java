/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;
import DAO.DBContext;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomePage_CoverDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public String getBackgroundImage() {
        String defaultImage = "/img/backgrounds/bg1.jpg"; 
        String query = "SELECT MediaURL FROM Media WHERE ObjectType = 'Cover'";
        
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("MediaURL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultImage;
    }

    public boolean updateBackground(String newImageUrl) {
        String query = """
                UPDATE Media SET MediaURL = ? WHERE ObjectType = 'Cover'
                """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, newImageUrl);
            return  ps.executeUpdate() >0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
  
}



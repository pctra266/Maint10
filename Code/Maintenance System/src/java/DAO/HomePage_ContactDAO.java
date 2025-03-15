/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.ContactText;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tra Pham
 */
public class HomePage_ContactDAO {
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
 public ContactText getContactText() {
        String query = "SELECT id, title, subtitle FROM ContactText WHERE id = 1";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new ContactText(rs.getInt("id"), rs.getString("title"), rs.getString("subtitle"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 public boolean updateContactText(String title, String subtitle) {
        String query = "UPDATE ContactText SET title = ?, subtitle = ?, lastUpdated = GETDATE() WHERE id = 1";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, subtitle);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        HomePage_ContactDAO dao = new HomePage_ContactDAO();
        System.out.println(dao.getContactText());
        dao.updateContactText("tra", "pham");
        System.out.println(dao.getContactText());
    }
 
}

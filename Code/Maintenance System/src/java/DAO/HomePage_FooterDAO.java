/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Footer;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tra Pham
 */
public class HomePage_FooterDAO {
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
 public Footer getFooter(){
        
        String query = """
                       select id,slogan, address, hotline, email, copyrightYear
                       from footer_settings
                       where id = 1
                       """;
        try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                Footer footer = new Footer();
                footer.setFooterId((rs.getInt("id")));
                footer.setSlogan(rs.getString("slogan"));
                footer.setAddress(rs.getString("address"));
                footer.setHotline(rs.getString("hotline"));
                footer.setEmail(rs.getString("email"));
                footer.setCopyrightYear(rs.getString("copyrightYear"));
                
                return footer;
            }
        }catch (Exception e){
            
        }
        
        return null;
    }
 public boolean updateFooter(Footer footer) {
    String query = """
                   UPDATE footer_settings 
                   SET slogan = ?, address = ?, hotline = ?, email = ?, copyrightYear = ?, lastUpdated = GETDATE() 
                   WHERE id = 1
                   """;
    try {
        conn = new DBContext().connection;
        ps = conn.prepareStatement(query);
        ps.setString(1, footer.getSlogan());
        ps.setString(2, footer.getAddress());
        ps.setString(3, footer.getHotline());
        ps.setString(4, footer.getEmail());
        ps.setString(5, footer.getCopyrightYear());

        int rowsUpdated = ps.executeUpdate();
        return rowsUpdated > 0; 
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false; 
}

 
    public static void main(String[] args) {
        HomePage_FooterDAO footerDao = new HomePage_FooterDAO();
        System.out.println(footerDao.getFooter());
        Footer f = footerDao.getFooter();
        f.setSlogan("dit con me");
        footerDao.updateFooter(f);
        System.out.println(f);
    }
}

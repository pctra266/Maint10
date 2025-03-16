/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.ExtendedWarranty;

public class ExtendedWarrantyDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public ArrayList<ExtendedWarranty> getListExtendedWarranty() {
        ArrayList<ExtendedWarranty> list = new ArrayList<>();
        String query = """
            SELECT ExtendedWarrantyID, ExtendedWarrantyName, ExtendedPeriodInMonths, Price, ExtendedWarrantyDescription, IsDelete
            FROM ExtendedWarranty
            WHERE IsDelete = 0
            ORDER BY ExtendedWarrantyID DESC
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                ExtendedWarranty ew = new ExtendedWarranty();
                ew.setExtendedWarrantyID(rs.getInt("ExtendedWarrantyID"));
                ew.setExtendedWarrantyName(rs.getString("ExtendedWarrantyName"));
                ew.setExtendedPeriodInMonths(rs.getInt("ExtendedPeriodInMonths"));
                ew.setPrice(rs.getDouble("Price"));
                ew.setExtendedWarrantyDescription(rs.getString("ExtendedWarrantyDescription"));
                ew.setIsDelete(rs.getBoolean("IsDelete"));
                list.add(ew);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ExtendedWarranty getExtendedWarrantyByID(String id) {
        ExtendedWarranty ew = null;
        String query = """
            SELECT ExtendedWarrantyID, ExtendedWarrantyName, ExtendedPeriodInMonths, Price, ExtendedWarrantyDescription, IsDelete
            FROM ExtendedWarranty
            WHERE ExtendedWarrantyID = ? AND IsDelete = 0
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                ew = new ExtendedWarranty();
                ew.setExtendedWarrantyID(rs.getInt("ExtendedWarrantyID"));
                ew.setExtendedWarrantyName(rs.getString("ExtendedWarrantyName"));
                ew.setExtendedPeriodInMonths(rs.getInt("ExtendedPeriodInMonths"));
                ew.setPrice(rs.getDouble("Price"));
                ew.setExtendedWarrantyDescription(rs.getString("ExtendedWarrantyDescription"));
                ew.setIsDelete(rs.getBoolean("IsDelete"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ew;
    }
    
    public boolean createExtendedWarranty(ExtendedWarranty ew) {
        String query = """
            INSERT INTO ExtendedWarranty (ExtendedWarrantyName, ExtendedPeriodInMonths, Price, ExtendedWarrantyDescription)
            VALUES (?, ?, ?, ?)
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, ew.getExtendedWarrantyName());
            ps.setInt(2, ew.getExtendedPeriodInMonths());
            ps.setDouble(3, ew.getPrice());
            ps.setString(4, ew.getExtendedWarrantyDescription());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateExtendedWarranty(ExtendedWarranty ew) {
        String query = """
            UPDATE ExtendedWarranty
            SET ExtendedWarrantyName = ?, ExtendedPeriodInMonths = ?, Price = ?, ExtendedWarrantyDescription = ?
            WHERE ExtendedWarrantyID = ? AND IsDelete = 0
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, ew.getExtendedWarrantyName());
            ps.setInt(2, ew.getExtendedPeriodInMonths());
            ps.setDouble(3, ew.getPrice());
            ps.setString(4, ew.getExtendedWarrantyDescription());
            ps.setInt(5, ew.getExtendedWarrantyID());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteExtendedWarranty(String id) {
        String query = "UPDATE ExtendedWarranty SET IsDelete = 1 WHERE ExtendedWarrantyID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        ExtendedWarrantyDAO dao = new ExtendedWarrantyDAO();
        System.out.println(dao.getListExtendedWarranty());
    }
}



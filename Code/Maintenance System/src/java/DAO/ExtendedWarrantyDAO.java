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
import Model.PackageWarranty;
import java.util.Calendar;

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
    public boolean extendWarrantyDetail(String packageWarrantyID, String extendedWarrantyID) {
    int extPeriod = 0;
    String queryExt = "SELECT ExtendedPeriodInMonths FROM ExtendedWarranty WHERE ExtendedWarrantyID = ?";
    try {
        conn = new DBContext().connection;
        ps = conn.prepareStatement(queryExt);
        ps.setString(1, extendedWarrantyID);
        rs = ps.executeQuery();
        if(rs.next()){
            extPeriod = rs.getInt("ExtendedPeriodInMonths");
        } else {
            return false; 
        }
        System.out.println("da den day");
        String queryCheck = "SELECT ExtendedWarrantyDetailID, EndExtendedWarranty FROM ExtendedWarrantyDetail WHERE PackageWarrantyID = ? and ExtendedWarrantyID = ?";
        ps = conn.prepareStatement(queryCheck);
        ps.setString(1, packageWarrantyID);
        ps.setString(2, extendedWarrantyID);
        rs = ps.executeQuery();
        java.util.Date now = new java.util.Date();
        if(rs.next()){
            System.out.println("22222222222222");
            int extDetailID = rs.getInt("ExtendedWarrantyDetailID");
            java.sql.Date currentEnd = rs.getDate("EndExtendedWarranty");
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentEnd);
            cal.add(Calendar.MONTH, extPeriod);
            java.sql.Date newEnd = new java.sql.Date(cal.getTimeInMillis());
            
            String queryUpdate = "UPDATE ExtendedWarrantyDetail SET EndExtendedWarranty = ? WHERE ExtendedWarrantyDetailID = ?";
            ps = conn.prepareStatement(queryUpdate);
            ps.setDate(1, newEnd);
            ps.setInt(2, extDetailID);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } else {
            System.out.println("333333333333333333");
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.MONTH, extPeriod);
            java.sql.Date endDate = new java.sql.Date(cal.getTimeInMillis());
            String queryInsert = "INSERT INTO ExtendedWarrantyDetail (ExtendedWarrantyID, PackageWarrantyID, StartExtendedWarranty, EndExtendedWarranty) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(queryInsert);
            ps.setString(1, extendedWarrantyID);
            ps.setString(2, packageWarrantyID);
            ps.setDate(3, new java.sql.Date(now.getTime()));
            ps.setDate(4, endDate);
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
    return false;
}
public ArrayList<PackageWarranty> getExtendedWarrantyDetailList(String packageWarrantyID) {
    ArrayList<PackageWarranty> list = new ArrayList<>();
    String query = """
        SELECT EWD.ExtendedWarrantyDetailID, EW.ExtendedWarrantyName, 
               EWD.StartExtendedWarranty, EWD.EndExtendedWarranty
        FROM ExtendedWarrantyDetail EWD
        JOIN ExtendedWarranty EW ON EWD.ExtendedWarrantyID = EW.ExtendedWarrantyID
        WHERE EWD.PackageWarrantyID = ?
        """;
    try {
        conn = new DBContext().connection;
        ps = conn.prepareStatement(query);
        ps.setString(1, packageWarrantyID);
        rs = ps.executeQuery();
        while(rs.next()){
            PackageWarranty ewd = new PackageWarranty();
            ewd.setExtendedWarrantyDetailID(rs.getInt("ExtendedWarrantyDetailID"));
            ewd.setExtendedWarrantyName(rs.getString("ExtendedWarrantyName"));
            ewd.setStartExtendedWarranty(rs.getDate("StartExtendedWarranty"));
            ewd.setEndExtendedWarranty(rs.getDate("EndExtendedWarranty"));
            list.add(ewd);
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
    return list;
}
public int totalExtendedWarranty(String searchExtendedWarrantyName) {
    String query = """
                   SELECT COUNT(*)
                   FROM ExtendedWarranty
                   WHERE IsDelete = 0
                   """;
    if (searchExtendedWarrantyName != null && !searchExtendedWarrantyName.trim().isEmpty()) {
        query += " AND ExtendedWarrantyName LIKE ?";
    }
    try {
        conn = new DBContext().connection;
        ps = conn.prepareStatement(query);
        int countParam = 1;
        if (searchExtendedWarrantyName != null && !searchExtendedWarrantyName.trim().isEmpty()) {
            ps.setString(countParam++, "%" + searchExtendedWarrantyName.trim() + "%");
        }
        rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}

public ArrayList<ExtendedWarranty> getListExtendedWarranty(String searchExtendedWarrantyName, int page, int pageSize) {
    ArrayList<ExtendedWarranty> list = new ArrayList<>();
    String query = """
                   SELECT ExtendedWarrantyID, ExtendedWarrantyName, ExtendedPeriodInMonths, Price, ExtendedWarrantyDescription, IsDelete
                   FROM ExtendedWarranty
                   WHERE IsDelete = 0
                   """;
    if (searchExtendedWarrantyName != null && !searchExtendedWarrantyName.trim().isEmpty()) {
        query += " AND ExtendedWarrantyName LIKE ?";
    }
    query += " ORDER BY ExtendedWarrantyID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    
    try {
        conn = new DBContext().connection;
        ps = conn.prepareStatement(query);
        int countParam = 1;
        if (searchExtendedWarrantyName != null && !searchExtendedWarrantyName.trim().isEmpty()) {
            ps.setString(countParam++, "%" + searchExtendedWarrantyName.trim() + "%");
        }
        int offset = (page - 1) * pageSize;
        ps.setInt(countParam++, offset);
        ps.setInt(countParam++, pageSize);
        rs = ps.executeQuery();
        while (rs.next()) {
            ExtendedWarranty ew = new ExtendedWarranty();
            ew.setExtendedWarrantyID(rs.getInt("ExtendedWarrantyID"));
            ew.setExtendedWarrantyName(rs.getString("ExtendedWarrantyName"));
            ew.setExtendedPeriodInMonths(rs.getInt("ExtendedPeriodInMonths"));
            ew.setPrice(rs.getDouble("Price"));
            ew.setExtendedWarrantyDescription(rs.getString("ExtendedWarrantyDescription"));
            ew.setIsDelete(rs.getBoolean("IsDelete"));
            list.add(ew);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    
    public static void main(String[] args) {
        ExtendedWarrantyDAO dao = new ExtendedWarrantyDAO();
        System.out.println(dao.getListExtendedWarranty());
    }
}



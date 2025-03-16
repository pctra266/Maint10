package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.PackageWarranty;

public class PackageWarrantyDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    // Lấy toàn bộ danh sách PackageWarranty theo câu truy vấn
    public ArrayList<PackageWarranty> getListPackageWarranty() {
        ArrayList<PackageWarranty> list = new ArrayList<>();
        String query = """
            SELECT
                PD.ProductCode,
                C.[Name] AS CustomerName,
                C.Email,
                P.ProductName,
                
                PW.PackageWarrantyID,
                PW.WarrantyStartDate,
                PW.WarrantyEndDate,
                PW.Note,
                PW.IsActive,
                
              
            	EWD.ExtendedWarrantyDetailID,
                EWD.StartExtendedWarranty,
                EWD.EndExtendedWarranty
            
            FROM ProductDetail PD
                JOIN Customer C ON PD.CustomerID = C.CustomerID
                JOIN Product P ON PD.ProductID = P.ProductID
                LEFT JOIN PackageWarranty PW ON PD.PackageWarrantyID = PW.PackageWarrantyID
                LEFT JOIN ExtendedWarrantyDetail EWD ON PW.PackageWarrantyID = EWD.PackageWarrantyID
                LEFT JOIN ExtendedWarranty EW ON EWD.ExtendedWarrantyID = EW.ExtendedWarrantyID;
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                PackageWarranty pkg = new PackageWarranty();
                pkg.setProductCode(rs.getString("ProductCode"));
                pkg.setCustomerName(rs.getString("CustomerName"));
                pkg.setEmail(rs.getString("Email"));
                pkg.setProductName(rs.getString("ProductName"));
                
                pkg.setPackageWarrantyID(rs.getInt("PackageWarrantyID"));
                pkg.setWarrantyStartDate(rs.getDate("WarrantyStartDate"));
                pkg.setWarrantyEndDate(rs.getDate("WarrantyEndDate"));
                pkg.setNote(rs.getString("Note"));
                pkg.setActive(rs.getBoolean("IsActive"));
                
                pkg.setExtendedWarrantyDetailID(rs.getInt("ExtendedWarrantyDetailID"));
                pkg.setStartExtendedWarranty(rs.getDate("StartExtendedWarranty"));
                pkg.setEndExtendedWarranty(rs.getDate("EndExtendedWarranty"));
                
                list.add(pkg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Cập nhật IsActive trong PackageWarranty
    public boolean updateActive(PackageWarranty pkg) {
        String query = "UPDATE PackageWarranty SET IsActive = ? WHERE PackageWarrantyID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setBoolean(1, pkg.isActive());
            ps.setInt(2, pkg.getPackageWarrantyID());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Cập nhật WarrantyStartDate và WarrantyEndDate trong PackageWarranty
    public boolean updateDefaultWarrantyPackage(PackageWarranty pkg) {
        String query = "UPDATE PackageWarranty SET WarrantyStartDate = ?, WarrantyEndDate = ? WHERE PackageWarrantyID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setDate(1, new java.sql.Date(pkg.getWarrantyStartDate().getTime()));
            ps.setDate(2, new java.sql.Date(pkg.getWarrantyEndDate().getTime()));
            ps.setInt(3, pkg.getPackageWarrantyID());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Cập nhật StartExtendedWarranty và EndExtendedWarranty trong ExtendedWarrantyDetail
    public boolean updateExtendedWarrantyDetail(PackageWarranty pkg) {
        String query = "UPDATE ExtendedWarrantyDetail SET StartExtendedWarranty = ?, EndExtendedWarranty = ? WHERE PackageWarrantyID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setDate(1, new java.sql.Date(pkg.getStartExtendedWarranty().getTime()));
            ps.setDate(2, new java.sql.Date(pkg.getEndExtendedWarranty().getTime()));
            ps.setInt(3, pkg.getPackageWarrantyID());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

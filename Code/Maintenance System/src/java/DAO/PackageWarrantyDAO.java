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
    
    public ArrayList<PackageWarranty> getAllPackageWarranties(String searchProductCode, String searchCustomerName, 
            String searchEmail, String searchProductName, String filterStatusPackage,
            String sort, String order, int page, int pageSize) {
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
                            PW.IsActive
                            
                        FROM ProductDetail PD
                            JOIN Customer C ON PD.CustomerID = C.CustomerID
                            JOIN Product P ON PD.ProductID = P.ProductID
                            LEFT JOIN PackageWarranty PW ON PD.PackageWarrantyID = PW.PackageWarrantyID
                        WHERE 1=1
            """;
        if (searchProductCode != null && !searchProductCode.trim().isEmpty()) {
            query += " and PD.ProductCode like ?";
        }
        if (searchCustomerName != null && !searchCustomerName.trim().isEmpty()) {
            query += " and C.[Name] like ?";
        }
        if (searchEmail != null && !searchEmail.trim().isEmpty()) {
            query += " and C.Email like ?";
        }
        if (searchProductName != null && !searchProductName.trim().isEmpty()) {
            query += " and P.ProductName like ?";
        }
        if(filterStatusPackage != null && !filterStatusPackage.trim().isEmpty()){
            if("active".equalsIgnoreCase(filterStatusPackage)){
                query += " and IsActive = 1";
            }else{
                query += " and IsActive = 0";
            }
            
        }
        // Sắp xếp: nếu sort và order được cung cấp, dùng chúng, ngược lại sắp xếp theo PackageWarrantyID desc
        if (sort != null && !sort.trim().isEmpty() && order != null && !order.trim().isEmpty()) {
            query += " order by " + sort + " " + order;
        } else {
            query += " order by PW.PackageWarrantyID desc";
        }
        query += " offset ? rows fetch next ? rows only";
        
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if (searchProductCode != null && !searchProductCode.trim().isEmpty()) {
                ps.setString(count++, "%" + searchProductCode.trim() + "%");
            }
            if (searchCustomerName != null && !searchCustomerName.trim().isEmpty()) {
                ps.setString(count++, "%" + searchCustomerName.trim() + "%");
            }
            if (searchEmail != null && !searchEmail.trim().isEmpty()) {
                ps.setString(count++, "%" + searchEmail.trim() + "%");
            }
            if (searchProductName != null && !searchProductName.trim().isEmpty()) {
                ps.setString(count++, "%" + searchProductName.trim() + "%");
            }
            int offset = (page - 1) * pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
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
                
                list.add(pkg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Tính tổng số PackageWarranty theo các điều kiện tìm kiếm
    public int totalPackageWarranty(String searchProductCode, String searchCustomerName, 
            String searchEmail, String searchProductName, String filterStatusPackage) {
        String query = """
            SELECT count(*)
            FROM ProductDetail PD
                            JOIN Customer C ON PD.CustomerID = C.CustomerID
                            JOIN Product P ON PD.ProductID = P.ProductID
                            LEFT JOIN PackageWarranty PW ON PD.PackageWarrantyID = PW.PackageWarrantyID
                        WHERE 1=1
            """;
        if (searchProductCode != null && !searchProductCode.trim().isEmpty()) {
            query += " and PD.ProductCode like ?";
        }
        if (searchCustomerName != null && !searchCustomerName.trim().isEmpty()) {
            query += " and C.[Name] like ?";
        }
        if (searchEmail != null && !searchEmail.trim().isEmpty()) {
            query += " and C.Email like ?";
        }
        if (searchProductName != null && !searchProductName.trim().isEmpty()) {
            query += " and P.ProductName like ?";
        }
        if(filterStatusPackage != null && !filterStatusPackage.trim().isEmpty()){
            if("active".equalsIgnoreCase(filterStatusPackage)){
                query += " and IsActive = 1";
            }else{
                query += " and IsActive = 0";
            }
            
        }
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if (searchProductCode != null && !searchProductCode.trim().isEmpty()) {
                ps.setString(count++, "%" + searchProductCode.trim() + "%");
            }
            if (searchCustomerName != null && !searchCustomerName.trim().isEmpty()) {
                ps.setString(count++, "%" + searchCustomerName.trim() + "%");
            }
            if (searchEmail != null && !searchEmail.trim().isEmpty()) {
                ps.setString(count++, "%" + searchEmail.trim() + "%");
            }
            if (searchProductName != null && !searchProductName.trim().isEmpty()) {
                ps.setString(count++, "%" + searchProductName.trim() + "%");
            }
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
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
                PW.Price,
                PW.DurationMonths,
                
              
            	EWD.ExtendedWarrantyDetailID,
                EWD.StartExtendedWarranty,
                EWD.EndExtendedWarranty,
                EW.ExtendedWarrantyName
            
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
    public PackageWarranty getPackageWarrantyByID(String packageWarrantyID) {
    PackageWarranty pkg = null;
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
            
            EW.ExtendedWarrantyName,
            
            EWD.ExtendedWarrantyDetailID,
            EWD.StartExtendedWarranty,
            EWD.EndExtendedWarranty
        FROM ProductDetail PD
            JOIN Customer C ON PD.CustomerID = C.CustomerID
            JOIN Product P ON PD.ProductID = P.ProductID
            LEFT JOIN PackageWarranty PW ON PD.PackageWarrantyID = PW.PackageWarrantyID
            LEFT JOIN ExtendedWarrantyDetail EWD ON PW.PackageWarrantyID = EWD.PackageWarrantyID
            LEFT JOIN ExtendedWarranty EW ON EWD.ExtendedWarrantyID = EW.ExtendedWarrantyID
        WHERE PW.PackageWarrantyID = ?
        """;
    try {
        conn = new DBContext().connection;
        ps = conn.prepareStatement(query);
        ps.setString(1, packageWarrantyID);
        rs = ps.executeQuery();
        if(rs.next()){
            pkg = new PackageWarranty();
            pkg.setProductCode(rs.getString("ProductCode"));
            pkg.setCustomerName(rs.getString("CustomerName"));
            pkg.setEmail(rs.getString("Email"));
            pkg.setProductName(rs.getString("ProductName"));
            
            pkg.setPackageWarrantyID(rs.getInt("PackageWarrantyID"));
            pkg.setWarrantyStartDate(rs.getDate("WarrantyStartDate"));
            pkg.setWarrantyEndDate(rs.getDate("WarrantyEndDate"));
            pkg.setNote(rs.getString("Note"));
            pkg.setActive(rs.getBoolean("IsActive"));
            
            pkg.setExtendedWarrantyName(rs.getString("ExtendedWarrantyName"));
            pkg.setExtendedWarrantyDetailID(rs.getInt("ExtendedWarrantyDetailID"));
            pkg.setStartExtendedWarranty(rs.getDate("StartExtendedWarranty"));
            pkg.setEndExtendedWarranty(rs.getDate("EndExtendedWarranty"));
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
    return pkg;
}

    public static void main(String[] args) {
        PackageWarrantyDAO dao = new PackageWarrantyDAO();
        System.out.println(dao.getAllPackageWarranties("","","","","","","",1,5));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ProductDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class ProductDetailDAO extends DBContext {
    public ArrayList<ProductDetail> getProductDetailByCustomerID(int customerID, String productCode, String code, String purchaseDate, String productName, int warrantyPeriod, String sortBy, String sortOrder, int offset, int fetch) {
        ArrayList<ProductDetail> listProductDetail = new ArrayList<>();
        String searchProductCode = (productCode != null) ? "%" + productCode.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchCode = (code != null) ? "%" + code.trim().replaceAll("\\s+", "%") + "%" : "%";
        String searchProductName = (productName != null) ? "%" + productName.trim().replaceAll("\\s+", "%") + "%" : "%";

        String sql = "SELECT \n"
                + "pd.ProductDetailID,\n"
                + "pd.ProductCode,\n"
                + "pd.PurchaseDate,\n"
                + "c.CustomerID,\n"
                + "c.Name,\n"
                + "c.Email,\n"
                + "c.Phone,\n"
                + "c.Address,\n"
                + "p.Code,    \n"
                + "p.ProductName,\n"
                + "p.WarrantyPeriod\n"
                + "FROM Product p \n"
                + "LEFT JOIN ProductDetail pd ON p.ProductID = pd.ProductID\n"
                + "LEFT JOIN Customer c ON c.CustomerID = pd.CustomerID\n"
                + "WHERE c.CustomerID = ?";

        if (searchProductCode != null && !searchProductCode.trim().isEmpty()) {
            sql += " AND pd.ProductCode LIKE ?";
        }
        if (searchCode != null && !searchCode.trim().isEmpty()) {
            sql += " AND p.Code LIKE ?";
        }
        if (purchaseDate != null && !purchaseDate.trim().isEmpty()) {
            sql += " AND pd.PurchaseDate LIKE ?";
        }
        if (searchProductName != null && !searchProductName.trim().isEmpty()) {
            sql += " AND p.ProductName LIKE ?";
        }
        if (warrantyPeriod > 0) {
            sql += " AND p.WarrantyPeriod = ?";
        }

        // Fix lỗi OFFSET khi không có ORDER BY
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sql += " ORDER BY pd.ProductDetailID";  // Sắp xếp mặc định theo ProductDetailID
        } else {
            sql += " ORDER BY " + sortBy;
            if (sortOrder != null && (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
                sql += " " + sortOrder;
            } else {
                sql += " ASC";
            }
        }

        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";  // Phân trang an toàn với SQL Server

        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;
            ps.setInt(index++, customerID);

            if (searchProductCode != null && !searchProductCode.trim().isEmpty()) {
                ps.setString(index++, searchProductCode);
            }
            if (searchCode != null && !searchCode.trim().isEmpty()) {
                ps.setString(index++, searchCode);
            }
            if (purchaseDate != null && !purchaseDate.trim().isEmpty()) {
                ps.setString(index++, "%" + purchaseDate.trim() + "%");
            }
            if (searchProductName != null && !searchProductName.trim().isEmpty()) {
                ps.setString(index++, searchProductName);
            }
            if (warrantyPeriod > 0) {
                ps.setInt(index++, warrantyPeriod);
            }

            ps.setInt(index++, offset);
            ps.setInt(index++, fetch);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductDetailID(rs.getInt("ProductDetailID"));
                productDetail.setProductCode(rs.getString("ProductCode"));
                productDetail.setPurchaseDate(rs.getDate("PurchaseDate"));
                productDetail.setCustomerID(rs.getInt("CustomerID"));
                productDetail.setName(rs.getString("Name"));
                productDetail.setEmail(rs.getString("Email"));
                productDetail.setPhone(rs.getString("Phone"));
                productDetail.setAddress(rs.getString("Address"));
                productDetail.setCode(rs.getString("Code"));
                productDetail.setProductName(rs.getString("ProductName"));
                productDetail.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));

                listProductDetail.add(productDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Hiển thị lỗi SQL để debug
        }
        return listProductDetail;
    }

    public int getProductDetailByCustomerIDItems(int customerID, String productCode, String code, String purchaseDate, String productName, int warrantyPeriod) {

        String searchProductCode = (productCode != null) ? "%" + productCode.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchCode = (code != null) ? "%" + code.trim().replaceAll("\\s+", "%") + "%" : "%";
        String searchProductName = (productName != null) ? "%" + productName.trim().replaceAll("\\s+", "%") + "%" : "%";
        String sql = "SELECT COUNT(*) \n"
                + "FROM Product p \n"
                + "LEFT JOIN ProductDetail pd ON p.ProductID = pd.ProductID \n"
                + "LEFT JOIN Customer c ON c.CustomerID = pd.CustomerID \n"
                + "WHERE c.CustomerID = ? ";

        if (searchProductCode != null && !searchProductCode.isEmpty()) {
            sql += " AND pd.ProductCode LIKE ?";
        }
        if (searchCode != null && !searchCode.isEmpty()) {
            sql += " AND p.Code LIKE ?";
        }
        if (purchaseDate != null && !purchaseDate.isEmpty()) {
            sql += " AND pd.PurchaseDate LIKE ?";
        }

        if (searchProductName != null && !searchProductName.isEmpty()) {
            sql += " AND p.ProductName LIKE ?";
        }
        if (warrantyPeriod > 0) {
            sql += " AND p.WarrantyPeriod = ?";
        }

        try {
            int index = 1;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(index++, customerID);
            if (searchProductCode != null && !searchProductCode.trim().isEmpty()) {
                ps.setString(index++, searchProductCode);
            }
            if (searchCode != null && !searchCode.trim().isEmpty()) {
                ps.setString(index++, searchCode);
            }
            if (purchaseDate != null && !purchaseDate.trim().isEmpty()) {
                ps.setString(index++, "%" + purchaseDate.trim() + "%");
            }
            if (searchProductName != null && !searchProductName.trim().isEmpty()) {
                ps.setString(index++, searchProductName);
            }
            if (warrantyPeriod > 0) {
                ps.setInt(index++, warrantyPeriod);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        ProductDetailDAO p = new ProductDetailDAO();
        int a = p.getProductDetailByCustomerIDItems(2, "", "", "", "", 24);
        System.out.println(a);
    }
    
    
    
   
}

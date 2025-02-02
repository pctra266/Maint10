package DAO;

import Model.ProductDetail;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import Model.WarrantyCard;
import java.sql.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ADMIN
 */
public class WarrantyCardDAO extends DBContext {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 10;
    private SecureRandom random = new SecureRandom();

    public List<WarrantyCard> getAllWarrantyCards() {
        List<WarrantyCard> warrantyCards = new ArrayList<>();
        String query = "SELECT wc.WarrantyCardID, wc.WarrantyCardCode, pd.ProductCode, wc.ProductDetailID, wc.IssueDescription, "
                + "wc.WarrantyStatus, wc.CreatedDate, p.ProductName, c.Name AS CustomerName, c.Phone AS CustomerPhone "
                + "FROM WarrantyCard wc "
                + "JOIN ProductDetail pd ON wc.ProductDetailID = pd.ProductDetailID "
                + "JOIN Product p ON pd.ProductID = p.ProductID "
                + "JOIN Customer c ON pd.CustomerID = c.CustomerID "
                + "Order by wc.WarrantyCardID desc ";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                WarrantyCard warrantyCard = new WarrantyCard();
                warrantyCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                warrantyCard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                warrantyCard.setProductDetailID(rs.getInt("ProductDetailID"));
                warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
                warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
                warrantyCard.setCreatedDate(rs.getTimestamp("CreatedDate"));
                warrantyCard.setProductName(rs.getString("ProductName"));
                warrantyCard.setCustomerName(rs.getString("CustomerName"));
                warrantyCard.setCustomerPhone(rs.getString("CustomerPhone"));
                warrantyCard.setProductCode(rs.getString("ProductCode"));

                warrantyCards.add(warrantyCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warrantyCards;
    }

    public boolean createWarrantyCard(String productCode, String issue) {
        String query = "INSERT INTO WarrantyCard (WarrantyCardCode, ProductDetailID, IssueDescription, WarrantyStatus, CreatedDate) "
                + "SELECT ?, pd.ProductDetailID, ?, 'fixing', GETDATE() "
                + "FROM ProductDetail pd WHERE pd.ProductCode = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            String warrantyCardCode = generateWarrantyCardCode(); // Tạo mã WarrantyCardCode
            ps.setString(1, warrantyCardCode);
            ps.setString(2,issue);
            ps.setString(3, productCode);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ProductDetail getProductDetailByCode(String code) {
    String sql = "SELECT pd.ProductDetailID, pd.ProductCode, pd.PurchaseDate, c.UsernameC, c.Name, c.Email, c.Phone, c.Address, p.ProductName, p.WarrantyPeriod "
               + "FROM ProductDetail pd "
               + "JOIN Customer c ON pd.CustomerID = c.CustomerID "
               + "JOIN Product p ON pd.ProductID = p.ProductID "
               + "WHERE pd.ProductCode = ?";
    
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setNString(1, code);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            ProductDetail productDetail = new ProductDetail(
                rs.getInt("ProductDetailID"),
                rs.getString("ProductCode"),
                rs.getDate("PurchaseDate"),
                rs.getString("UsernameC"),
                rs.getString("Name"),
                rs.getString("Email"),
                rs.getString("Phone"),
                rs.getString("Address"),
                rs.getString("ProductName"),
                rs.getInt("WarrantyPeriod")
            );
            return productDetail; // Trả về đối tượng ProductDetail
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    return null; // Trả về null nếu không tìm thấy kết quả
}

    private String generateWarrantyCardCode() {
        String warrantyCode;

        do {
            warrantyCode = generateRandomCode();
        } while (isWarrantyCardCodeExists(warrantyCode)); // Kiểm tra trùng mã trong database

        return warrantyCode;
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private boolean isWarrantyCardCodeExists(String warrantyCode) {
        String query = "SELECT COUNT(*) FROM WarrantyCard WHERE WarrantyCardCode = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, warrantyCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu có mã trùng, trả về true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Không tìm thấy mã trùng
    }
    public static void main(String[] args) {
        WarrantyCardDAO d = new WarrantyCardDAO();
        System.out.println(d.getAllWarrantyCards());
    }
}

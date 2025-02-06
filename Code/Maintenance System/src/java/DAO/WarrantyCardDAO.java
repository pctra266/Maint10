package DAO;

import Model.ProductDetail;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import Model.WarrantyCard;
import Utils.SearchUtils;
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

    public int getTotalCards() {
        String sql = "SELECT COUNT(*) FROM WarrantyCard ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getTotalSearchCards(String paraSearch) {
        int total = 0;
        String query = "SELECT COUNT(*) "
                + "FROM WarrantyCard wc "
                + "JOIN ProductDetail pd ON wc.ProductDetailID = pd.ProductDetailID "
                + "JOIN Product p ON pd.ProductID = p.ProductID "
                + "WHERE wc.WarrantyCardCode LIKE ? "
                + "   OR pd.ProductCode LIKE ? "
                + "   OR p.ProductName LIKE ? "
                + "   OR wc.IssueDescription LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            String searchPattern = "%" + paraSearch + "%";
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, searchPattern);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc log lỗi phù hợp
        }
        return total;
    }

    public List<WarrantyCard> getCardsByPage(int page, Integer pageSize) {
        List<WarrantyCard> cards = new ArrayList<>();
        String query = "SELECT wc.WarrantyCardID, wc.WarrantyCardCode, pd.ProductCode, p.ProductName, c.Name AS CustomerName, c.Phone AS CustomerPhone, wc.IssueDescription, wc.WarrantyStatus, wc.CreatedDate "
                + "FROM WarrantyCard wc "
                + "JOIN ProductDetail pd ON wc.ProductDetailID = pd.ProductDetailID "
                + "JOIN Product p ON pd.ProductID = p.ProductID "
                + "JOIN Customer c ON pd.CustomerID = c.CustomerID "
                + "ORDER BY wc.CreatedDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                WarrantyCard card = new WarrantyCard();
                card.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                card.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                card.setProductCode(rs.getString("ProductCode"));
                card.setProductName(rs.getString("ProductName"));
                card.setCustomerName(rs.getString("CustomerName"));
                card.setCustomerPhone(rs.getString("CustomerPhone"));
                card.setIssueDescription(rs.getString("IssueDescription"));
                card.setWarrantyStatus(rs.getString("WarrantyStatus"));
                card.setCreatedDate(rs.getDate("CreatedDate"));

                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public List<WarrantyCard> searchCardsByPage(String paraSearch, int page, Integer pageSize) {
        List<WarrantyCard> cards = new ArrayList<>();
        String query = "SELECT wc.WarrantyCardID, wc.WarrantyCardCode, pd.ProductCode, p.ProductName, c.Name AS CustomerName, c.Phone AS CustomerPhone, wc.IssueDescription, wc.WarrantyStatus, wc.CreatedDate "
                + "FROM WarrantyCard wc "
                + "JOIN ProductDetail pd ON wc.ProductDetailID = pd.ProductDetailID "
                + "JOIN Product p ON pd.ProductID = p.ProductID "
                + "JOIN Customer c ON pd.CustomerID = c.CustomerID "
                + "WHERE wc.WarrantyCardCode LIKE ? OR p.ProductName LIKE ? OR c.Name LIKE ? OR pd.ProductCode LIKE ? "
                + "ORDER BY wc.CreatedDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            String searchPattern = "%" + paraSearch + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            ps.setInt(5, (page - 1) * pageSize);
            ps.setInt(6, pageSize);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                WarrantyCard card = new WarrantyCard();
                card.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                card.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                card.setProductCode(rs.getString("ProductCode"));
                card.setProductName(rs.getString("ProductName"));
                card.setCustomerName(rs.getString("CustomerName"));
                card.setCustomerPhone(rs.getString("CustomerPhone"));
                card.setIssueDescription(rs.getString("IssueDescription"));
                card.setWarrantyStatus(rs.getString("WarrantyStatus"));
                card.setCreatedDate(rs.getDate("CreatedDate"));

                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public boolean createWarrantyCard(String productCode, String issue) {
        String query = "INSERT INTO WarrantyCard (WarrantyCardCode, ProductDetailID, IssueDescription, WarrantyStatus, CreatedDate) "
                + "SELECT ?, pd.ProductDetailID, ?, 'fixing', GETDATE() "
                + "FROM ProductDetail pd WHERE pd.ProductCode = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            String warrantyCardCode = generateWarrantyCardCode(); // Tạo mã WarrantyCardCode
            ps.setString(1, warrantyCardCode);
            ps.setString(2, issue);
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

    /**
     * Get warranty card by customer ID
     *
     * @param customerID
     * @return
     */
    public ArrayList<WarrantyCard> getWarrantyCardByCustomerID(int customerID) {

        ArrayList<WarrantyCard> listWarrantyCard = new ArrayList<>();
        String sql = "SELECT\n"
                + "w.WarrantyCardID,\n"
                + "w.WarrantyCardCode,\n"
                + "pd.ProductDetailID,\n"
                + "pd.ProductCode,\n"
                + "w.IssueDescription,\n"
                + "w.WarrantyStatus,\n"
                + "w.CreatedDate,\n"
                + "p.ProductName,\n"
                + "c.CustomerID,\n"
                + "c.Name,\n"
                + "c.Phone\n"
                + "\n"
                + "FROM WarrantyCard w LEFT JOIN ProductDetail pd ON w.ProductDetailID = pd.ProductDetailID\n"
                + "                    LEFT JOIN Customer c ON c.CustomerID = pd.CustomerID\n"
                + "					LEFT JOIN Product p ON p.ProductID = pd.ProductID\n"
                + "WHERE c.CustomerID =?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WarrantyCard warrantyCard = new WarrantyCard();
                warrantyCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                warrantyCard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
                warrantyCard.setProductDetailID(rs.getInt("ProductDetailID"));
                warrantyCard.setProductCode(rs.getString("ProductCode"));

                warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
                warrantyCard.setCreatedDate(rs.getDate("CreatedDate"));
                warrantyCard.setProductName(rs.getString("ProductName"));
                warrantyCard.setCustomerID(rs.getInt("CustomerID"));
                warrantyCard.setCustomerName(rs.getString("Name"));
                warrantyCard.setCustomerPhone(rs.getString("Phone"));

                listWarrantyCard.add(warrantyCard);

            }
        } catch (SQLException e) {

        }
        return listWarrantyCard;

    }

    public static void main(String[] args) {
        WarrantyCardDAO d = new WarrantyCardDAO();
        //  System.out.println(d.searchCardsByPage("PDT", 1, 5));
        ArrayList<WarrantyCard> wr = d.getWarrantyCardByCustomerID(18);
        for (WarrantyCard w : wr) {
            System.out.println(w.getCustomerName());
        }
    }

}



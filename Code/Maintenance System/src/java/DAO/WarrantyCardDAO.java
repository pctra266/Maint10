package DAO;

import Model.ProductDetail;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import Model.WarrantyCard;
import Utils.FormatUtils;
import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ADMIN
 */
public class WarrantyCardDAO extends DBContext {

    private static final WarrantyCardDAO d = new WarrantyCardDAO();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 10;
    private static final String SELECT_STRING = """
    SELECT wc.WarrantyCardID, wc.WarrantyCardCode, wc.IssueDescription, wc.WarrantyStatus, 
           wc.CreatedDate, wc.ReturnDate, wc.DoneDate, wc.CompleteDate, wc.CancelDate, wc.Image, 
           COALESCE(pd.ProductCode, up.ProductCode) AS ProductCode, p.Code,
           COALESCE(p.ProductName, up.ProductName) AS ProductName,
           c.Name AS CustomerName, c.Phone AS CustomerPhone, c.CustomerID 
    FROM WarrantyCard wc 
    JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID 
    LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID 
    LEFT JOIN UnknowProduct up ON wp.UnknowProductID = up.UnknowProductID 
    LEFT JOIN Product p ON pd.ProductID = p.ProductID 
    LEFT JOIN Customer c ON COALESCE(pd.CustomerID, up.CustomerID) = c.CustomerID
    """;

    private SecureRandom random = new SecureRandom();

    public List<WarrantyCard> getAllWarrantyCards() {
        List<WarrantyCard> warrantyCards = new ArrayList<>();
        String query = SELECT_STRING + "ORDER BY wc.WarrantyCardID DESC";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                warrantyCards.add(mapWarrantyCard(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warrantyCards;
    }

    public boolean createWarrantyCard(String productCode, String issue, java.util.Date returnDate, String image, int handlerID) {
        try {
            // Tìm ProductDetailID theo ProductCode
            String findProductDetailQuery = "SELECT ProductDetailID FROM ProductDetail WHERE ProductCode = ?";
            int productDetailID = -1;

            try (PreparedStatement ps = connection.prepareStatement(findProductDetailQuery)) {
                ps.setString(1, productCode);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        productDetailID = rs.getInt("ProductDetailID");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm có mã: " + productCode);
                        return false;
                    }
                }
            }

            // Tìm hoặc tạo WarrantyProduct
            int warrantyProductID = -1;
            String findWarrantyProductQuery = "SELECT WarrantyProductID FROM WarrantyProduct WHERE ProductDetailID = ?";

            try (PreparedStatement ps = connection.prepareStatement(findWarrantyProductQuery)) {
                ps.setInt(1, productDetailID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        warrantyProductID = rs.getInt("WarrantyProductID");
                    }
                }
            }

            // Nếu chưa có WarrantyProduct, tạo mới
            if (warrantyProductID == -1) {
                String insertWarrantyProductQuery = "INSERT INTO WarrantyProduct (ProductDetailID) VALUES (?)";
                try (PreparedStatement ps = connection.prepareStatement(insertWarrantyProductQuery, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, productDetailID);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            warrantyProductID = rs.getInt(1);
                        }
                    }
                }
            }

            // Tạo mã WarrantyCardCode
            String warrantyCardCode = generateWarrantyCardCode();

            // Chèn vào bảng WarrantyCard
            String insertWarrantyCardQuery = "INSERT INTO WarrantyCard (WarrantyCardCode, WarrantyProductID, IssueDescription, WarrantyStatus, CreatedDate, ReturnDate, Image) "
                    + "VALUES (?, ?, ?, 'fixing', GETDATE(), ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(insertWarrantyCardQuery)) {
                ps.setString(1, warrantyCardCode);
                ps.setInt(2, warrantyProductID);
                ps.setString(3, issue);
                ps.setDate(4, returnDate == null ? null : new java.sql.Date(returnDate.getTime()));
                ps.setString(5, image);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    d.addWarrantyCardProcess(warrantyProductID, handlerID, "create", "");
                }
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ProductDetail getProductDetailByCode(String code) {
        String sql = "SELECT pd.ProductDetailID, pd.ProductCode, pd.PurchaseDate, "
                + "c.UsernameC, c.Name AS CustomerName, c.Email, c.Phone, c.Address, "
                + "p.ProductName, p.WarrantyPeriod "
                + "FROM ProductDetail pd "
                + "JOIN Customer c ON pd.CustomerID = c.CustomerID "
                + "JOIN Product p ON pd.ProductID = p.ProductID "
                + "WHERE pd.ProductCode = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code); // Sử dụng setString thay vì setNString nếu cột không phải NVARCHAR
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProductDetail(
                            rs.getInt("ProductDetailID"),
                            rs.getString("ProductCode"),
                            rs.getDate("PurchaseDate"),
                            rs.getString("UsernameC"),
                            rs.getString("CustomerName"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
                            rs.getString("ProductName"),
                            rs.getInt("WarrantyPeriod")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

    public ProductDetail getUnknowProductDetailByCode(String code) {
        String sql = "SELECT up.UnknowProductID, up.ProductCode, up.ProductName, up.Description, "
                + "up.PurchaseDate, c.UsernameC, c.Name AS CustomerName, c.Email, c.Phone, c.Address "
                + "FROM UnknowProduct up "
                + "JOIN Customer c ON up.CustomerID = c.CustomerID "
                + "WHERE up.ProductCode = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProductDetail(
                            rs.getInt("ProductDetailID"),
                            rs.getString("ProductCode"),
                            rs.getDate("PurchaseDate"),
                            rs.getString("UsernameC"),
                            rs.getString("CustomerName"),
                            rs.getString("Email"),
                            rs.getString("Phone"),
                            rs.getString("Address"),
                            rs.getString("ProductName"),
                            rs.getInt("WarrantyPeriod")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm không rõ nguồn gốc
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
    public List<WarrantyCard> getWarrantyCardByCustomerID(int customerID) {
        List<WarrantyCard> warrantyCards = new ArrayList<>();
        String query = SELECT_STRING + "WHERE c.CustomerID=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                warrantyCards.add(mapWarrantyCard(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warrantyCards;
    }

    public int getTotalCards(String paraSearch, String status, String type) {
        int total = 0;
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM WarrantyCard wc ");
        query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");

        // Trường hợp lấy từ ProductDetail (warranty)
        if ("warranty".equalsIgnoreCase(type)) {
            query.append("JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("JOIN Product p ON pd.ProductID = p.ProductID ");
        } // Trường hợp lấy từ UnknowProduct (repair)
        else if ("repair".equalsIgnoreCase(type)) {
            query.append("JOIN UnknowProduct up ON wp.UnknowProductID = up.UnknowProductID ");
        } // Trường hợp lấy cả hai (null)
        else {
            query.append("LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("LEFT JOIN Product p ON pd.ProductID = p.ProductID ");
            query.append("LEFT JOIN UnknowProduct up ON wp.UnknowProductID = up.UnknowProductID ");
        }

        query.append("WHERE (wc.WarrantyCardCode LIKE ? ");

        if (!"repair".equalsIgnoreCase(type)) {
            query.append("   OR pd.ProductCode LIKE ? OR p.ProductName LIKE ? ");
        }
        if (!"warranty".equalsIgnoreCase(type)) {
            query.append("   OR up.ProductCode LIKE ? OR up.ProductName LIKE ? ");
        }

        query.append("   OR wc.IssueDescription LIKE ?)");

        // Thêm điều kiện lọc theo trạng thái nếu có
        if (status != null && !status.isEmpty()) {
            query.append(" AND wc.WarrantyStatus = ?");
        }

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            String searchPattern = "%" + paraSearch + "%";
            ps.setString(1, searchPattern);

            int paramIndex = 2;
            if (!"repair".equalsIgnoreCase(type)) {
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (!"warranty".equalsIgnoreCase(type)) {
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }

            ps.setString(paramIndex++, searchPattern);

            // Nếu có trạng thái, truyền vào tham số cuối cùng
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex, status);
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

    public List<WarrantyCard> getCards(int page, Integer pageSize, String paraSearch, String status, String sort, String order, String type) {
        List<WarrantyCard> cards = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT wc.WarrantyCardID, wc.WarrantyCardCode, wc.IssueDescription, ");
        query.append("wc.WarrantyStatus, wc.CreatedDate, wc.ReturnDate, wc.DoneDate, wc.CompleteDate, wc.CancelDate, wc.Image, p.Code ");

        // Trường hợp lấy từ ProductDetail (warranty)
        if ("warranty".equalsIgnoreCase(type)) {
            query.append(", pd.ProductCode, p.ProductName, c.Name AS CustomerName, c.Phone AS CustomerPhone ");
            query.append("FROM WarrantyCard wc ");
            query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");
            query.append("JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("JOIN Product p ON pd.ProductID = p.ProductID ");
            query.append("JOIN Customer c ON pd.CustomerID = c.CustomerID ");
        } // Trường hợp lấy từ UnknowProduct (repair)
        else if ("repair".equalsIgnoreCase(type)) {
            query.append(", up.ProductCode, up.ProductName, c.Name AS CustomerName, c.Phone AS CustomerPhone ");
            query.append("FROM WarrantyCard wc ");
            query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");
            query.append("JOIN UnknowProduct up ON wp.UnknowProductID = up.UnknowProductID ");
            query.append("JOIN Customer c ON up.CustomerID = c.CustomerID ");

        } // Trường hợp lấy cả hai (null)
        else {
            query.append(", COALESCE(pd.ProductCode, up.ProductCode) AS ProductCode, ");
            query.append("COALESCE(p.ProductName, up.ProductName) AS ProductName, ");
            query.append("c.Name AS CustomerName, c.Phone AS CustomerPhone ");
            query.append("FROM WarrantyCard wc ");
            query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");
            query.append("LEFT JOIN UnknowProduct up ON wp.UnknowProductID = up.UnknowProductID ");
            query.append("LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("LEFT JOIN Product p ON pd.ProductID = p.ProductID ");
            query.append("LEFT JOIN Customer c ON COALESCE(pd.CustomerID, up.CustomerID) = c.CustomerID ");
        }

        query.append("WHERE (wc.WarrantyCardCode LIKE ? ");

        if (!"repair".equalsIgnoreCase(type)) {
            query.append("   OR pd.ProductCode LIKE ? OR p.ProductName LIKE ? ");
        }
        if (!"warranty".equalsIgnoreCase(type)) {
            query.append("   OR up.ProductCode LIKE ? OR up.ProductName LIKE ? ");
        }

        query.append("   OR wc.IssueDescription LIKE ?)");

        // Thêm điều kiện lọc theo trạng thái nếu có
        if (status != null && !status.isEmpty()) {
            query.append(" AND wc.WarrantyStatus = ?");
        }

        // Xử lý sắp xếp
        if (sort == null || sort.isEmpty()) {
            sort = "CreatedDate"; // Mặc định sắp xếp theo ngày tạo
        }
        if (order == null || !(order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC"))) {
            order = "DESC"; // Mặc định sắp xếp giảm dần
        }

        query.append(" ORDER BY ").append(sort).append(" ").append(order).append(" ");
        query.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            String searchPattern = "%" + paraSearch + "%";
            ps.setString(1, searchPattern);

            int paramIndex = 2;
            if (!"repair".equalsIgnoreCase(type)) {
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (!"warranty".equalsIgnoreCase(type)) {
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }

            ps.setString(paramIndex++, searchPattern);

            // Nếu có trạng thái, truyền vào tham số cuối cùng
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }

            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cards.add(mapWarrantyCard(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    /**
     * Get Warranty Card by Phone and warranty code
     *
     * @param phone
     * @param code
     * @return
     */
    public WarrantyCard getWarrantyCardByPhoneAndCode(String phone, String code) {
        String sql = "SELECT wc.WarrantyCardID,\n"
                + "       wc.WarrantyCardCode,\n"
                + "	   pd.ProductDetailID,\n"
                + "	   up.UnknowProductID,\n"
                + "	   p.Code,\n"
                + "	   wc.IssueDescription,\n"
                + "	   wc.WarrantyStatus,\n"
                + "	   wc.CreatedDate,\n"
                + "	   wc.ReturnDate,\n"
                + "	   wc.DoneDate,\n"
                + "	   wc.CompleteDate,\n"
                + "	   wc.CancelDate,\n"
                + "	   p.Image,\n"
                + "	   p.ProductName,\n"
                + "	   c.CustomerID,\n"
                + "	   c.Name,\n"
                + "	   c.Phone\n"
                + "\n"
                + "\n"
                + "FROM WarrantyCard wc LEFT JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID\n"
                + "                     \n"
                + "                     LEFT JOIN ProductDetail pd ON pd.ProductDetailID = wp.ProductDetailID\n"
                + "					 LEFT JOIN Product p ON p.ProductID = pd.ProductID\n"
                + "					 LEFT JOIN Customer c ON c.CustomerID = pd.CustomerID\n"
                + "					 LEFT JOIN UnknowProduct up ON up.CustomerID = c.CustomerID\n"
                + "					 WHERE c.Phone = ? AND wc.WarrantyCardCode =?\n"
                + "		 ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setString(2, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                WarrantyCard warrantyCard = new WarrantyCard();
                warrantyCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                warrantyCard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                warrantyCard.setProductDetailID(rs.getInt("ProductDetailID"));
                warrantyCard.setUnknowProductID(rs.getInt("UnknowProductID"));
                warrantyCard.setProductCode(rs.getString("Code"));
                warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
                warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
                warrantyCard.setCreatedDate(rs.getDate("CreatedDate"));
                warrantyCard.setReturnDate(rs.getDate("ReturnDate"));
                warrantyCard.setDonedDate(rs.getDate("DoneDate"));
                warrantyCard.setCompletedDate(rs.getDate("CompleteDate"));
                warrantyCard.setCanceldDate(rs.getDate("CancelDate"));
                warrantyCard.setImage(rs.getString("Image"));
                warrantyCard.setProductName(rs.getString("ProductName"));
                warrantyCard.setCustomerID(rs.getInt("CustomerID"));
                warrantyCard.setCustomerName(rs.getString("Name"));
                warrantyCard.setCustomerPhone(rs.getString("Phone"));
                return warrantyCard;

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public WarrantyCard getWarrantyCardById(int id) {
        return getWarrantyCardByField("WarrantyCardID", id + "");
    }

    public WarrantyCard getWarrantyCardByCode(String code) {
        String sql = "SELECT [WarrantyCardID]\n"
                + "      ,[WarrantyCardCode]\n"
             
                + "      ,[IssueDescription]\n"
                + "      ,[WarrantyStatus]\n"
                + "      ,[ReturnDate]\n"
                + "      ,[DoneDate]\n"
                + "      ,[CompleteDate]\n"
                + "      ,[CancelDate]\n"
                + "      ,[CreatedDate]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[WarrantyCard]\n"
                + "  WHERE WarrantyCardCode =?";
        try {
            PreparedStatement ps  = connection.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                WarrantyCard wr = new WarrantyCard();
                wr.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                wr.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                wr.setIssueDescription(rs.getString("IssueDescription"));
                wr.setWarrantyStatus(rs.getString("WarrantyStatus"));
                wr.setReturnDate(rs.getDate("ReturnDate"));
                wr.setDonedDate(rs.getDate("DoneDate"));
                wr.setCompletedDate(rs.getDate("CompleteDate"));
                wr.setCanceldDate(rs.getDate("CancelDate"));
                wr.setCreatedDate(rs.getDate("CreatedDate"));
                wr.setImage(rs.getString("Image"));
                return wr;
                
            }
        }catch(SQLException e) {
                   
                   } 
      return null;  
    }

    private WarrantyCard getWarrantyCardByField(String field, String para) {

        String sql = SELECT_STRING + "WHERE wc." + field + "=?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, para);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return mapWarrantyCard(rs);
            }

        } catch (SQLException e) {

        }
        return null;
    }

    public boolean addWarrantyCardProcess(int warrantyCardId, int handlerId, String action, String note) {
        String sql = """
                     INSERT INTO [dbo].[WarrantyCardProcess]
                                ([WarrantyCardID]
                                ,[HandlerID]
                                ,[Action]
                                ,[Note])
                          VALUES(
                                ?
                                ,?
                                ,?
                                ,?)""";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, warrantyCardId);
            ps.setInt(2, handlerId);
            ps.setString(3, action);
            ps.setString(4, note);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private WarrantyCard mapWarrantyCard(ResultSet rs) throws SQLException {
        WarrantyCard warrantyCard = new WarrantyCard();
        warrantyCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
        warrantyCard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
        warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
        warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
        warrantyCard.setCreatedDate(rs.getTimestamp("CreatedDate"));
        warrantyCard.setReturnDate(rs.getTimestamp("ReturnDate"));
        warrantyCard.setDonedDate(rs.getTimestamp("DoneDate"));
        warrantyCard.setCompletedDate(rs.getTimestamp("CompleteDate"));
        warrantyCard.setCanceldDate(rs.getTimestamp("CancelDate"));
        warrantyCard.setProductDetailCode(rs.getString("ProductCode"));
        warrantyCard.setProductCode(rs.getString("Code"));
        warrantyCard.setProductName(rs.getString("ProductName"));
        warrantyCard.setCustomerName(rs.getString("CustomerName"));
        warrantyCard.setCustomerPhone(rs.getString("CustomerPhone"));
        warrantyCard.setImage(rs.getString("Image"));
        return warrantyCard;
    }

    public static void main(String[] args) {
        WarrantyCardDAO d = new WarrantyCardDAO();
        System.out.println(d.getCards(1, 10, "", "", "", "", ""));
    }

}

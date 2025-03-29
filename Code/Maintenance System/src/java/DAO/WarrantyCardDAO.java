package DAO;

import Model.Customer;
import Model.Product;
import Model.ProductDetail;
import Model.UnknownProduct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import Model.WarrantyCard;
import Model.WarrantyCardDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public class WarrantyCardDAO extends DBContext {

    private final WarrantyCardDetailDAO wcdDao = new WarrantyCardDetailDAO();
    private static final WarrantyCardDAO d = new WarrantyCardDAO();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 10;
    private static final String SELECT_STRING = """
    SELECT wc.WarrantyCardID, wc.WarrantyCardCode, wc.IssueDescription, wc.WarrantyStatus,wc.WarrantyProductID, wc.HandlerID, 
           wc.CreatedDate, wc.ReturnDate, wc.DoneDate, wc.CompleteDate, wc.CancelDate, 
           COALESCE(pd.ProductCode, up.ProductCode) AS ProductCode, p.Code,
           COALESCE(p.ProductName, up.ProductName) AS ProductName,
           c.Name AS CustomerName, c.Phone AS CustomerPhone, c.CustomerID 
    FROM WarrantyCard wc 
    JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID 
    LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID 
    LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID 
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
            System.out.println(e);
        }

        return warrantyCards;
    }

    public boolean deleteWarrantyCard(WarrantyCard wc) {
        String sql = "DELETE FROM WarrantyCard where WarrantyCardID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, wc.getWarrantyCardID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateWarrantyCard(WarrantyCard wc) {
        String sql = "UPDATE WarrantyCard SET  WarrantyCardCode = ?, WarrantyProductID = ?, "
                + "IssueDescription = ?, WarrantyStatus = ?, ReturnDate = ?, DoneDate = ?, "
                + "CompleteDate = ?, CancelDate = ?, HandlerID = ? WHERE WarrantyCardID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, wc.getWarrantyCardCode());
            ps.setInt(2, wc.getWarrantyProductID());
            ps.setString(3, wc.getIssueDescription());
            ps.setString(4, wc.getWarrantyStatus());
            ps.setTimestamp(5, wc.getReturnDate() != null ? new java.sql.Timestamp(wc.getReturnDate().getTime()) : null);
            ps.setTimestamp(6, wc.getDonedDate() != null ? new java.sql.Timestamp(wc.getDonedDate().getTime()) : null);
            ps.setTimestamp(7, wc.getCompletedDate() != null ? new java.sql.Timestamp(wc.getCompletedDate().getTime()) : null);
            ps.setTimestamp(8, wc.getCanceldDate() != null ? new java.sql.Timestamp(wc.getCanceldDate().getTime()) : null);
            if (wc.getHandlerID() != null) { // Assuming HandlerID is positive; adjust if 0 is valid
                ps.setInt(9, wc.getHandlerID());
            } else {
                ps.setNull(9, java.sql.Types.INTEGER);
            }
            ps.setInt(10, wc.getWarrantyCardID());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                String deleteMediaQuery = "DELETE FROM Media WHERE ObjectID = ? AND ObjectType = 'WarrantyCard'";
                try (PreparedStatement psd = connection.prepareStatement(deleteMediaQuery)) {
                    psd.setInt(1, wc.getWarrantyCardID());
                    psd.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (String url : wc.getImages()) {
                    addMedia(wc.getWarrantyCardID(), "WarrantyCard", url, "image");
                }
                for (String url : wc.getVideos()) {
                    addMedia(wc.getWarrantyCardID(), "WarrantyCard", url, "video");
                }
            }

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public int createWarrantyCard(String productCode, String issue, java.util.Date returnDate, List<String> mediaPaths) {
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
                        return -1;
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
            String insertWarrantyCardQuery = "INSERT INTO WarrantyCard (WarrantyCardCode, WarrantyProductID, IssueDescription, WarrantyStatus, CreatedDate, ReturnDate) "
                    + "VALUES (?, ?, ?, 'waiting', GETDATE(), ?)";
            int warrantyCardId = -1;
            try (PreparedStatement ps = connection.prepareStatement(insertWarrantyCardQuery, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, warrantyCardCode);
                ps.setInt(2, warrantyProductID);
                ps.setString(3, issue);
                ps.setTimestamp(4, returnDate != null ? new java.sql.Timestamp(returnDate.getTime()) : null);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            warrantyCardId = rs.getInt(1); // Retrieve the generated WarrantyCardID
                        }
                    }
                    if (warrantyCardId != -1) {
                        String insertMediaQuery = "INSERT INTO Media (ObjectID, ObjectType, MediaURL, MediaType) VALUES (?, 'WarrantyCard', ?, ?)";
                        try (PreparedStatement ps2 = connection.prepareStatement(insertMediaQuery)) {
                            for (String mediaPath : mediaPaths) {
                                ps2.setInt(1, warrantyCardId);
                                ps2.setString(2, mediaPath);
                                ps2.setString(3, mediaPath.endsWith(".mp4") || mediaPath.endsWith(".mov") || mediaPath.endsWith(".avi") || mediaPath.endsWith(".mkv") ? "video" : "image");
                                ps2.addBatch();
                            }
                            ps2.executeBatch();
                        }
                    }
                    return warrantyCardId;
                }
                return warrantyCardId;
            }
        } catch (SQLException e) {

            e.printStackTrace();
            return -1;
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
            System.out.println(ex);
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

    public ProductDetail getUnknownProductDetailByCode(String code) {
        String sql = "SELECT up.UnknownProductID, up.ProductCode, up.ProductName, up.Description, "
                + "up.PurchaseDate, c.UsernameC, c.Name AS CustomerName, c.Email, c.Phone, c.Address "
                + "FROM UnknownProduct up "
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
            System.out.println(ex);
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
            System.out.println(e);
        }

        return false; // Không tìm thấy mã trùng
    }

    /**
     * Get warranty card by customer ID
     *
     * @param customerID
     * @param warrantyCard
     * @param productName
     * @param createDate
     * @param sortBy
     * @param sortOrder
     * @param offset
     * @param fetch
     * @return
     */
    public List<WarrantyCard> getWarrantyCardByCustomerID(int customerID, String warrantyCard, String productName, String status, String createDate, String sortBy, String sortOrder, int offset, int fetch) {

        String searchWarrantyCardCode = (warrantyCard != null) ? "%" + warrantyCard.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchProductName = (productName != null) ? "%" + productName.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchStatus = (status != null) ? "%" + status.trim().replaceAll("\\s+", "%") + "%" : "%";
        List<WarrantyCard> listWarrantyCard = new ArrayList<>();
        String query = SELECT_STRING + "WHERE c.CustomerID=?";
        if (searchWarrantyCardCode != null && !searchWarrantyCardCode.trim().isEmpty()) {
            query += " AND wc.WarrantyCardCode LIKE ?";
        }
        if (searchProductName != null && !searchProductName.trim().isEmpty()) {
            query += " AND (COALESCE(p.ProductName, up.ProductName) LIKE ?) ";

        }
        if (createDate != null && !createDate.trim().isEmpty()) {
            query += " AND  wc.CreatedDate = ?";
        }

        if (searchStatus != null && !searchStatus.trim().isEmpty()) {
            query += " AND  wc.WarrantyStatus LIKE ?";
        }

        // Fix lỗi OFFSET khi không có ORDER BY
        if (sortBy == null || sortBy.trim().isEmpty()) {
            query += " ORDER BY wc.WarrantyCardID";
        } else {
            query += " ORDER BY " + sortBy;
            if (sortOrder != null && (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
                query += " " + sortOrder;
            } else {
                query += " ASC";
            }
        }

        query += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";  // Phân trang an toàn với SQL Server
        System.out.println("Final Query: " + query);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            ps.setInt(index++, customerID);
            if (searchWarrantyCardCode != null && !searchWarrantyCardCode.trim().isEmpty()) {
                ps.setString(index++, searchWarrantyCardCode);
            }

            if (searchProductName != null && !searchProductName.trim().isEmpty()) {
                ps.setString(index++, searchProductName);
            }
            if (createDate != null && !createDate.trim().isEmpty()) {
                ps.setDate(index++, java.sql.Date.valueOf(createDate.trim()));
            }
            if (searchStatus != null && !searchStatus.trim().isEmpty()) {
                ps.setString(index++, searchStatus);
            }
            ps.setInt(index++, offset);
            ps.setInt(index++, fetch);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WarrantyCard warrantycard = new WarrantyCard();
                warrantycard.setWarrantyCardID(rs.getInt("WarrantyCardID"));

                warrantycard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                warrantycard.setIssueDescription(rs.getString("IssueDescription"));
                warrantycard.setWarrantyStatus(rs.getString("WarrantyStatus"));
                warrantycard.setCreatedDate(rs.getDate("CreatedDate"));
                warrantycard.setReturnDate(rs.getDate("ReturnDate"));
                warrantycard.setDonedDate(rs.getDate("DoneDate"));
                warrantycard.setHandlerID(rs.getInt("HandlerID"));
                warrantycard.setCompletedDate(rs.getDate("CompleteDate"));
                warrantycard.setCanceldDate(rs.getDate("CancelDate"));
                warrantycard.setProductDetailCode(rs.getString("ProductCode"));
                warrantycard.setCustomerID(rs.getInt("CustomerID"));

                warrantycard.setProductName(rs.getString("ProductName"));
                warrantycard.setCustomerName(rs.getString("CustomerName"));
                warrantycard.setCustomerPhone(rs.getString("CustomerPhone"));
                listWarrantyCard.add(warrantycard);
            }
        } catch (SQLException e) {
        }

        return listWarrantyCard;
    }

    public int getPageWarrantyCardByCustomerID(int customerID, String warrantyCard, String productName, String status, String createDate) {
        String searchWarrantyCardCode = (warrantyCard != null) ? "%" + warrantyCard.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchProductName = (productName != null) ? "%" + productName.trim().replaceAll("\\s+", "%") + "%" : "%";

        String searchStatus = (status != null) ? "%" + status.trim().replaceAll("\\s+", "%") + "%" : "%";
        String query = "SELECT COUNT(*) \n"
                + " FROM WarrantyCard wc \n"
                + " JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID \n"
                + " LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID \n"
                + " LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID \n"
                + " LEFT JOIN Product p ON pd.ProductID = p.ProductID \n"
                + " LEFT JOIN Customer c ON COALESCE(pd.CustomerID, up.CustomerID) = c.CustomerID \n"
                + " WHERE c.CustomerID=?";
        if (searchWarrantyCardCode != null && !searchWarrantyCardCode.trim().isEmpty()) {
            query += " AND wc.WarrantyCardCode LIKE ?";
        }
        if (searchProductName != null && !searchProductName.trim().isEmpty()) {
            query += " AND (COALESCE(p.ProductName, up.ProductName) LIKE ?) ";

        }
        if (createDate != null && !createDate.trim().isEmpty()) {
            query += " AND  wc.CreatedDate = ?";
        }

        if (searchStatus != null && !searchStatus.trim().isEmpty()) {
            query += " AND  wc.WarrantyStatus LIKE ?";
        }

        try {
            int index = 1;

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(index++, customerID);
            if (searchWarrantyCardCode != null && !searchWarrantyCardCode.trim().isEmpty()) {
                ps.setString(index++, searchWarrantyCardCode);
            }

            if (searchProductName != null && !searchProductName.trim().isEmpty()) {
                ps.setString(index++, searchProductName);
            }
            if (createDate != null && !createDate.trim().isEmpty()) {
                ps.setDate(index++, java.sql.Date.valueOf(createDate.trim()));
            }
            if (searchStatus != null && !searchStatus.trim().isEmpty()) {
                ps.setString(index++, searchStatus);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalCards(String paraSearch, String status, String type, Integer handlerId) {
        int total = 0;
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM WarrantyCard wc ");
        query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");

        // Trường hợp lấy từ ProductDetail (warranty)
        if ("warranty".equalsIgnoreCase(type)) {
            query.append("JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("JOIN Product p ON pd.ProductID = p.ProductID ");
        } // Trường hợp lấy từ UnknownProduct (repair)
        else if ("repair".equalsIgnoreCase(type)) {
            query.append("JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID ");
        } // Trường hợp lấy cả hai (null)
        else {
            query.append("LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("LEFT JOIN Product p ON pd.ProductID = p.ProductID ");
            query.append("LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID ");
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
        // xem theo cac card receive
        if (type != null && "myCard".equals(type)) {
            query.append(" AND wc.HandlerID = ?");
        }
        if (type != null && !"myCard".equals(type)) {
            query.append(" AND wc.HandlerID is null");
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
                ps.setString(paramIndex++, status);
            }
            //Neu loc theo receive card
            if (type != null && "myCard".equals(type)) {
                if (handlerId == null) {
                    ps.setNull(paramIndex, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(paramIndex, handlerId);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return total;
    }

    public List<WarrantyCard> getCards(int page, Integer pageSize, String paraSearch, String status, String sort, String order, String type, Integer handlerId) {
        List<WarrantyCard> cards = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT wc.WarrantyCardID, wc.WarrantyCardCode, wc.IssueDescription, wc.WarrantyProductID, ");
        query.append("wc.WarrantyStatus, wc.CreatedDate, wc.ReturnDate, wc.DoneDate, wc.CompleteDate, wc.CancelDate, wc.HandlerID, c.CustomerID, ");

        // Trường hợp lấy từ ProductDetail (warranty)
        if ("warranty".equalsIgnoreCase(type)) {
            query.append("p.Code , pd.ProductCode, p.ProductName, c.Name AS CustomerName, c.Phone AS CustomerPhone ");
            query.append("FROM WarrantyCard wc ");
            query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");
            query.append("JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID ");
            query.append("JOIN Product p ON pd.ProductID = p.ProductID ");
            query.append("JOIN Customer c ON pd.CustomerID = c.CustomerID ");
        } // Trường hợp lấy từ UnknownProduct (repair)
        else if ("repair".equalsIgnoreCase(type)) {
            query.append("up.ProductCode, up.ProductName, c.Name AS CustomerName, c.Phone AS CustomerPhone ");
            query.append("FROM WarrantyCard wc ");
            query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");
            query.append("JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID ");
            query.append("JOIN Customer c ON up.CustomerID = c.CustomerID ");

        } // Trường hợp lấy cả hai (null)
        else {
            query.append("p.Code , COALESCE(pd.ProductCode, up.ProductCode) AS ProductCode, ");
            query.append("COALESCE(p.ProductName, up.ProductName) AS ProductName, ");
            query.append("c.Name AS CustomerName, c.Phone AS CustomerPhone ");
            query.append("FROM WarrantyCard wc ");
            query.append("JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID ");
            query.append("LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID ");
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
        // xem theo cac card receive
        if (type != null && "myCard".equals(type)) {
            query.append(" AND wc.HandlerID = ?");
        }
        if (type != null && !"myCard".equals(type) && !"all".equals(type)) {
            query.append(" AND wc.HandlerID is null");
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
            //Neu loc theo receive card
            if (type != null && "myCard".equals(type)) {
                if (handlerId == null) {
                    ps.setNull(paramIndex++, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(paramIndex++, handlerId);
                }
            }

            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cards.add(mapWarrantyCard(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
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
    String sql = "SELECT \n"
            + "    wc.WarrantyCardID,\n"
            + "    wc.WarrantyCardCode,\n"
            + "    pd.ProductDetailID,\n"
            + "    up.UnknownProductID,\n"
            + "    p.Code,\n"
            + "    wc.IssueDescription,\n"
            + "    wc.WarrantyStatus,\n"
            + "    wc.CreatedDate,\n"
            + "    wc.ReturnDate,\n"
            + "    wc.DoneDate,\n"
            + "    wc.CompleteDate,\n"
            + "    wc.CancelDate,\n"
            + "    p.ProductName AS KnownProductName,\n"
            + "    up.ProductName AS UnknownProductName,\n"
            + "    COALESCE(c1.CustomerID, c2.CustomerID) AS CustomerID,\n"
            + "    COALESCE(c1.Name, c2.Name) AS CustomerName,\n"
            + "    COALESCE(c1.Phone, c2.Phone) AS CustomerPhone\n"
            + "FROM WarrantyCard wc\n"
            + "LEFT JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID\n"
            + "LEFT JOIN ProductDetail pd ON pd.ProductDetailID = wp.ProductDetailID\n"
            + "LEFT JOIN Product p ON p.ProductID = pd.ProductID\n"
            + "LEFT JOIN Customer c1 ON pd.CustomerID = c1.CustomerID\n"
            + "LEFT JOIN UnknownProduct up ON up.UnknownProductID = wp.UnknownProductID\n"
            + "LEFT JOIN Customer c2 ON c2.CustomerID = up.CustomerID\n"
            + "WHERE COALESCE(c1.Phone, c2.Phone) = ? AND wc.WarrantyCardCode = ?";

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

            // Xử lý UnknownProductID nếu NULL
            int unknownProductID = rs.getInt("UnknownProductID");
            if (rs.wasNull()) unknownProductID = -1;
            warrantyCard.setUnknownProductID(unknownProductID);

            warrantyCard.setProductCode(rs.getString("Code"));
            warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
            warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
            warrantyCard.setCreatedDate(rs.getDate("CreatedDate"));
            warrantyCard.setReturnDate(rs.getDate("ReturnDate"));
            warrantyCard.setDonedDate(rs.getDate("DoneDate"));
            warrantyCard.setCompletedDate(rs.getDate("CompleteDate"));
            warrantyCard.setCanceldDate(rs.getDate("CancelDate"));

            // Lấy danh sách hình ảnh & video
            warrantyCard.setImages(getMediaURLs(warrantyCard.getWarrantyCardID(), "image"));
            warrantyCard.setVideos(getMediaURLs(warrantyCard.getWarrantyCardID(), "video"));

            // Sửa alias lấy ProductName
            warrantyCard.setProductName(rs.getString("KnownProductName") != null ? 
                                        rs.getString("KnownProductName") : rs.getString("UnknownProductName"));

            // Lấy thông tin Customer
            int customerID = rs.getInt("CustomerID");
            if (rs.wasNull()) customerID = -1;
            warrantyCard.setCustomerID(customerID);

            warrantyCard.setCustomerName(rs.getString("CustomerName"));
            warrantyCard.setCustomerPhone(rs.getString("CustomerPhone"));

            return warrantyCard;
        }
    } catch (SQLException e) {
        e.printStackTrace(); // In lỗi chi tiết
    }
    return null;
}

    public WarrantyCard getWarrantyCardById(int id) {
        return getWarrantyCardByField("WarrantyCardID", id + "");
    }

    public WarrantyCard getWarrantyCardByCode(String code) {

        String sql = """
                     SELECT [WarrantyCardID]
                           ,[WarrantyCardCode]
                           ,[IssueDescription]
                           ,[WarrantyStatus]
                           ,[ReturnDate]
                           ,[DoneDate]
                           ,[CompleteDate]
                           ,[CancelDate]
                           ,[CreatedDate]
                       FROM [dbo].[WarrantyCard]
                       WHERE WarrantyCardCode =?""";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
                wr.setImages(getMediaURLs(wr.getWarrantyCardID(), "image"));
                wr.setVideos(getMediaURLs(wr.getWarrantyCardID(), "video"));
                return wr;

            }
        } catch (SQLException e) {

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
            e.printStackTrace();
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
            System.out.println(e);
        }
        return false;
    }

    private WarrantyCard mapWarrantyCard(ResultSet rs) throws SQLException {
        WarrantyCard warrantyCard = new WarrantyCard();
        warrantyCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
        warrantyCard.setWarrantyProductID(rs.getInt("WarrantyProductID"));
        warrantyCard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
        warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
        warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
        warrantyCard.setCreatedDate(rs.getTimestamp("CreatedDate"));
        warrantyCard.setReturnDate(rs.getTimestamp("ReturnDate"));
        warrantyCard.setDonedDate(rs.getTimestamp("DoneDate"));
        warrantyCard.setHandlerID(rs.getInt("HandlerID"));
        warrantyCard.setCompletedDate(rs.getTimestamp("CompleteDate"));
        warrantyCard.setCanceldDate(rs.getTimestamp("CancelDate"));
        warrantyCard.setProductDetailCode(rs.getString("ProductCode"));
        warrantyCard.setCustomerID(rs.getInt("CustomerID"));
        try {
            warrantyCard.setProductCode(rs.getString("Code"));
        } catch (SQLException e) {
            System.out.println("Cột 'Code' không tồn tại, bỏ qua gán giá trị.");
        }

        warrantyCard.setProductName(rs.getString("ProductName"));
        warrantyCard.setCustomerName(rs.getString("CustomerName"));
        warrantyCard.setCustomerPhone(rs.getString("CustomerPhone"));

        // Lấy danh sách ảnh từ bảng Media
        warrantyCard.setImages(getMediaURLs(warrantyCard.getWarrantyCardID(), "image"));
        warrantyCard.setVideos(getMediaURLs(warrantyCard.getWarrantyCardID(), "video"));

        return warrantyCard;
    }

    private List<String> getMediaURLs(int warrantyCardID, String mediaType) {
        List<String> mediaList = new ArrayList<>();
        String sql = "SELECT MediaURL FROM Media WHERE ObjectID = ? AND ObjectType = 'WarrantyCard' AND MediaType = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, warrantyCardID);
            ps.setString(2, mediaType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mediaList.add(rs.getString("MediaURL"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return mediaList;
    }

    private Timestamp parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            if (dateStr.contains("T")) {
                dateStr = dateStr.replace("T", " ") + ":00";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
            return Timestamp.valueOf(dateTime);
        } catch (Exception e) {
            System.out.println("Error in parseDate: " + e.getMessage());
            return null;
        }
    }

    public WarrantyCard createWarrantyCard(
            int warrantyProductID,
            String issueDescription,
            String warrantyStatus,
            String returnDate,
            String doneDate,
            String completeDate,
            String cancelDate) {
        // SQL chèn dữ liệu
        String insertSql = "INSERT INTO WarrantyCard (WarrantyCardCode, WarrantyProductID, "
                + "IssueDescription, WarrantyStatus, ReturnDate, DoneDate, CompleteDate, CancelDate, CreatedDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
        // Sinh mã cho WarrantyCard
        String warrantyCardCode = generateWarrantyCardCode();

        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
            insertStmt.setString(1, warrantyCardCode);
            insertStmt.setInt(2, warrantyProductID);
            insertStmt.setString(3, issueDescription);
            insertStmt.setString(4, warrantyStatus);
            insertStmt.setTimestamp(5, parseDate(returnDate) != null ? parseDate(returnDate) : null);
            insertStmt.setTimestamp(6, parseDate(doneDate) != null ? parseDate(doneDate) : null);
            insertStmt.setTimestamp(7, parseDate(completeDate) != null ? parseDate(completeDate) : null);
            insertStmt.setTimestamp(8, parseDate(cancelDate) != null ? parseDate(cancelDate) : null);

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                // Nếu chèn thành công, thực hiện truy vấn để lấy lại WarrantyCard vừa tạo
                String selectSql = "SELECT * FROM WarrantyCard WHERE WarrantyCardCode = ?";
                try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                    selectStmt.setString(1, warrantyCardCode);
                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            WarrantyCard warrantyCard = new WarrantyCard();
                            warrantyCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                            warrantyCard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                            warrantyCard.setWarrantyProductID(rs.getInt("WarrantyProductID"));
                            warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
                            warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
                            warrantyCard.setReturnDate(rs.getTimestamp("ReturnDate"));
                            warrantyCard.setDonedDate(rs.getTimestamp("DoneDate"));
                            warrantyCard.setCompletedDate(rs.getTimestamp("CompleteDate"));
                            warrantyCard.setCanceldDate(rs.getTimestamp("CancelDate"));
                            warrantyCard.setCreatedDate(rs.getTimestamp("CreatedDate"));
                            return warrantyCard;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        // Nếu chèn không thành công hoặc không tìm thấy record thì trả về null
        return null;
    }

    public Integer getWarrantyCardID(int warrantyProductID) {
        Integer warrantyCardID = null;
        String sql = "SELECT TOP 1 WarrantyCardID FROM WarrantyCard WHERE WarrantyProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, warrantyProductID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    warrantyCardID = rs.getInt("WarrantyCardID");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return warrantyCardID;
    }

    public boolean addMedia(int objectId, String objectType, String mediaUrl, String mediaType) {
        PreparedStatement ps = null;
        String sql = "INSERT INTO Media (ObjectID, ObjectType, MediaURL, MediaType) VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, objectId);
            ps.setString(2, objectType);
            ps.setString(3, mediaUrl);
            ps.setString(4, mediaType);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public WarrantyCard searchWarrantyCardByCode(String warrantyCardCode) {
        String sql = "SELECT * FROM WarrantyCard WHERE WarrantyCardCode = ?";
        WarrantyCard warrantyCard = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, warrantyCardCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    warrantyCard = new WarrantyCard();
                    warrantyCard.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                    warrantyCard.setWarrantyProductID(rs.getInt("WarrantyProductID"));
                    warrantyCard.setHandlerID(rs.getInt("HandlerID"));
                    warrantyCard.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                    warrantyCard.setIssueDescription(rs.getString("IssueDescription"));
                    warrantyCard.setWarrantyStatus(rs.getString("WarrantyStatus"));
                    warrantyCard.setCreatedDate(rs.getTimestamp("CreatedDate"));
                    warrantyCard.setReturnDate(rs.getTimestamp("ReturnDate"));
                    warrantyCard.setDonedDate(rs.getTimestamp("DoneDate"));
                    warrantyCard.setCompletedDate(rs.getTimestamp("CompleteDate"));
                    warrantyCard.setCanceldDate(rs.getTimestamp("CancelDate"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return warrantyCard;
    }

    public Object getProductByWarrantyProductId(int warrantyProductId) {
        Object product = null;
        String query = """
                       SELECT 
                         p.*,
                         b.BrandName, 
                         pt.TypeName,
                           pd.PurchaseDate, 
                           pd.ProductCode AS pdCode, 
                       	up.ProductCode as uCode,
                           up.UnknownProductID,
                       	up.CustomerID,
                       	up.Description,
                       	up.ReceivedDate,
                       	up.ProductName as uName
                       FROM WarrantyProduct wp 
                       LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID 
                       LEFT JOIN Product p ON pd.ProductID = p.ProductID 
                       LEFT JOIN Brand b ON p.BrandID = b.BrandID 
                       LEFT JOIN ProductType pt ON p.ProductTypeID = pt.ProductTypeID 
                       LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID 
                       WHERE wp.WarrantyProductID = ?;""";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, warrantyProductId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("ProductID") > 0) {
                    product = new Product(
                            rs.getInt("ProductID"),
                            rs.getString("pdCode"),
                            rs.getString("ProductName"),
                            rs.getInt("Quantity"),
                            rs.getInt("WarrantyPeriod"),
                            rs.getString("Status"),
                            rs.getString("BrandName"),
                            rs.getString("TypeName"),
                            rs.getString("PurchaseDate")
                    );
                } else if (rs.getInt("UnknownProductID") > 0) {
                    String receivedDate = "";
                    Timestamp timestamp = rs.getTimestamp("ReceivedDate");
                    if (timestamp != null) {
                        receivedDate = sdf.format(timestamp);
                    }
                    product = new UnknownProduct(
                            rs.getInt("UnknownProductID"),
                            rs.getInt("CustomerID"), // Nếu không cần CustomerID, để giá trị mặc định
                            rs.getString("uName"),
                            rs.getString("uCode"),
                            rs.getString("Description"),
                            receivedDate
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return product;
    }

    public Customer getCustomerByWarrantyProductID(int warrantyProductID) {
        String query = """
            SELECT c.CustomerID, c.UsernameC, c.Name, c.Gender, c.DateOfBirth, 
                   c.Email, c.Phone, c.Address, c.Image, c.PasswordC 
            FROM WarrantyProduct wp
            LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID
            LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID
            LEFT JOIN Customer c ON (pd.CustomerID = c.CustomerID OR up.CustomerID = c.CustomerID)
            WHERE wp.WarrantyProductID = ?;
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, warrantyProductID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("UsernameC"),
                        rs.getString("PasswordC"),
                        rs.getString("Name"),
                        rs.getString("Gender"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Address"),
                        rs.getString("Image")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public int getPriceOfWarrantyCard(int id) {
        List<WarrantyCardDetail> list = wcdDao.getWarrantyCardDetailOfCard(id);
        int total = 0;
        for (WarrantyCardDetail warrantyCardDetail : list) {
            total += warrantyCardDetail.getPrice();
        }
        return total;
    }

    public List<Map<String, Object>> getWarrantyCardDetails(int warrantyCardID) {
        List<Map<String, Object>> details = new ArrayList<>();
        String sql = "SELECT c.ComponentID, c.ComponentCode, c.ComponentName, b.BrandName, ct.TypeName, "
                + "c.Price AS pricePerPiece, wcd.Quantity AS numberOfUses, wcd.Note, wcd.Price AS totalPrice "
                + "FROM WarrantyCardDetail AS wcd "
                + "INNER JOIN Component AS c ON wcd.ComponentID = c.ComponentID "
                + "JOIN Brand b ON b.BrandID = c.BrandID "
                + "JOIN ComponentType ct ON ct.TypeID = c.TypeID "
                + "WHERE wcd.WarrantyCardID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, warrantyCardID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> detail = new HashMap<>();
                    detail.put("ComponentID", rs.getInt("ComponentID"));
                    detail.put("ComponentCode", rs.getString("ComponentCode"));
                    detail.put("ComponentName", rs.getString("ComponentName"));
                    detail.put("BrandName", rs.getString("BrandName"));
                    detail.put("TypeName", rs.getString("TypeName"));
                    detail.put("pricePerPiece", rs.getDouble("pricePerPiece"));
                    detail.put("numberOfUses", rs.getInt("numberOfUses"));
                    detail.put("Note", rs.getString("Note"));
                    detail.put("totalPrice", rs.getDouble("totalPrice"));
                    details.add(detail);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return details;
    }

    public Map<String, Object> getWarrantyCardDetailsMap(int warrantyCardId) {
        Map<String, Object> details = new HashMap<>();

        String sql = "SELECT "
                + "  wc.WarrantyCardID, "
                + "  wc.WarrantyCardCode, "
                + "  wc.IssueDescription, "
                + "  s.StaffID, "
                + "  s.Name AS StaffName, "
                + "  s.Phone AS StaffPhone, "
                + "  s.Email AS StaffEmail, "
                + "  pd.ProductCode, "
                + "  p.ProductName, "
                + "  b.BrandName, "
                + "  pt.TypeName, "
                + "  up.ProductName AS UnknownProductName, "
                + "  up.ProductCode AS UnknownProductCode, "
                + "  up.Description AS UnknownProductDescription, "
                + "  m.MediaURL, "
                + "  cc.ContractorCardID, "
                + "  cc.Status AS ContractorStatus, "
                + "  wcp.lastProcessStatus "
                + "FROM WarrantyCard wc "
                + "LEFT JOIN Staff s ON wc.HandlerID = s.StaffID "
                + "LEFT JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID "
                + "LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID "
                + "LEFT JOIN Product p ON pd.ProductID = p.ProductID "
                + "LEFT JOIN Brand b ON p.BrandID = b.BrandID "
                + "LEFT JOIN ProductType pt ON p.ProductTypeID = pt.ProductTypeID "
                + "LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID "
                + "LEFT JOIN Media m ON m.ObjectID = wc.WarrantyCardID AND m.ObjectType = 'WarrantyCard' "
                + "LEFT JOIN ContractorCard cc ON cc.WarrantyCardID = wc.WarrantyCardID "
                + "LEFT JOIN ( "
                + "    SELECT WarrantyCardID, HandlerID, [Action] as lastProcessStatus "
                + "    FROM warrantyCardProcess wcp1 "
                + "    WHERE [Action] IN ('send_outsource', 'receive_outsource') AND ActionDate = ( "
                + "         SELECT MAX(ActionDate) FROM warrantyCardProcess wcp2 "
                + "         WHERE wcp2.WarrantyCardID = wcp1.WarrantyCardID "
                + "           AND wcp2.HandlerID = wcp1.HandlerID "
                + "           AND wcp2.[Action] IN ('send_outsource', 'receive_outsource') "
                + "    ) "
                + ") wcp ON wc.WarrantyCardID = wcp.WarrantyCardID AND s.StaffID = wcp.HandlerID "
                + "WHERE wc.WarrantyCardID = ? "
                + "ORDER BY m.MediaID, cc.ContractorCardID;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, warrantyCardId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (details.isEmpty()) {
                        details.put("warrantyCardID", rs.getInt("WarrantyCardID"));
                        details.put("warrantyCardCode", rs.getString("WarrantyCardCode"));
                        details.put("issueDescription", rs.getString("IssueDescription"));
                        details.put("staffID", rs.getInt("StaffID"));
                        details.put("staffName", rs.getString("StaffName"));
                        details.put("staffPhone", rs.getString("StaffPhone"));
                        details.put("staffEmail", rs.getString("StaffEmail"));
                        details.put("productCode", rs.getString("ProductCode"));
                        details.put("productName", rs.getString("ProductName"));
                        details.put("brandName", rs.getString("BrandName"));
                        details.put("typeName", rs.getString("TypeName"));
                        details.put("unknownProductName", rs.getString("UnknownProductName"));
                        details.put("unknownProductCode", rs.getString("UnknownProductCode"));
                        details.put("unknownProductDescription", rs.getString("UnknownProductDescription"));
                        details.put("contractorCardID", rs.getInt("ContractorCardID"));
                        details.put("lastProcessStatus", rs.getString("lastProcessStatus"));

                        details.put("mediaUrls", new ArrayList<String>());
                        details.put("contractorStatuses", new ArrayList<String>());
                    }

                    String mediaUrl = rs.getString("MediaURL");
                    if (mediaUrl != null && !mediaUrl.trim().isEmpty()) {
                        List<String> mediaUrls = (List<String>) details.get("mediaUrls");
                        if (!mediaUrls.contains(mediaUrl)) {
                            mediaUrls.add(mediaUrl);
                        }
                    }

                    String contractorStatus = rs.getString("ContractorStatus");
                    if (contractorStatus != null && !contractorStatus.trim().isEmpty()) {
                        List<String> contractorStatuses = (List<String>) details.get("contractorStatuses");
                        if (!contractorStatuses.contains(contractorStatus)) {
                            contractorStatuses.add(contractorStatus);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Lấy danh sách các bản ghi quy trình (process) liên quan đến warrantyCardId
        String processSql = "SELECT wcp.WarrantyCardProcessID, wcp.WarrantyCardID, wcp.HandlerID, wcp.[Action], "
                + "wcp.ActionDate, wcp.Note "
                + "FROM warrantyCardProcess wcp "
                + "WHERE wcp.WarrantyCardID = ? "
                + "ORDER BY wcp.ActionDate ASC;";
        List<Map<String, Object>> processList = new ArrayList<>();
        try (PreparedStatement ps2 = connection.prepareStatement(processSql)) {
            ps2.setInt(1, warrantyCardId);
            try (ResultSet rs2 = ps2.executeQuery()) {
                while (rs2.next()) {
                    Map<String, Object> process = new HashMap<>();
                    process.put("WarrantyCardProcessID", rs2.getInt("WarrantyCardProcessID"));
                    process.put("WarrantyCardID", rs2.getInt("WarrantyCardID"));
                    process.put("HandlerID", rs2.getInt("HandlerID"));
                    process.put("Action", rs2.getString("Action"));
                    process.put("ActionDate", rs2.getTimestamp("ActionDate"));
                    process.put("Note", rs2.getString("Note"));
                    processList.add(process);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        details.put("processList", processList);

        return details;
    }

    public static void main(String[] args) {

    }

}

package DAO;

import Model.Brand;
import Model.Product;
import Model.ProductDetail;
import Model.ProductType;
import Model.UnknownProduct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.Statement;

/**
 *
 * @author sonNH
 */
public class ProductDAO extends DBContext {

    private void getProductImages(Map<Integer, Product> productMap) {
        // Tạo danh sách productId cho truy vấn SQL
        String idList = productMap.keySet().stream().map(String::valueOf).collect(Collectors.joining(","));
        // Nếu không có productId, thoát ngay
        if (idList.isEmpty()) {
            return;
        }
        String sql = "SELECT ObjectID, MediaURL FROM Media WHERE ObjectID IN (" + idList + ") and ObjectType = 'Product' ";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int productId = rs.getInt("ObjectID");  // Đọc từ cột ObjectID
                String imagePath = rs.getString("MediaURL");  // Đọc từ cột MediaURL

                // Thêm ảnh vào danh sách ảnh của sản phẩm tương ứng
                productMap.get(productId).getImages().add(imagePath);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy ảnh sản phẩm: " + e.getMessage());
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Map<Integer, Product> productMap = new HashMap<>();

        // Truy vấn thông tin sản phẩm trước
        String sql = "SELECT p.ProductID, p.Code, p.ProductTypeID, pt.TypeName, p.ProductName, "
                + "cb.BrandName, p.Quantity, p.WarrantyPeriod, p.Status "
                + "FROM Product p "
                + "JOIN Brand cb ON p.BrandID = cb.BrandID "
                + "JOIN ProductType pt ON p.ProductTypeID = pt.ProductTypeID "
                + "WHERE p.Status != 'inactive'";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int productId = rs.getInt("ProductID");

                // Nếu chưa có sản phẩm trong Map, tạo mới và thêm vào danh sách
                if (!productMap.containsKey(productId)) {
                    Product product = new Product();
                    product.setProductId(productId);
                    product.setCode(rs.getString("Code"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setBrandName(rs.getString("BrandName"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    product.setStatus(rs.getString("Status"));
                    product.setProductTypeId(rs.getInt("ProductTypeID"));
                    product.setProductTypeName(rs.getString("TypeName"));
                    product.setImages(new ArrayList<>()); // Khởi tạo danh sách ảnh
                    productMap.put(productId, product);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
            return products;
        }

        // Nếu không có sản phẩm nào thì không cần truy vấn ảnh
        if (productMap.isEmpty()) {
            return products;
        }

        // Truy vấn ảnh sản phẩm từ Media
        getProductImages(productMap);

        return products;
    }

    public List<Product> getAllProducts(int page, int pageSize) {
        List<Product> products = new ArrayList<>();
        Map<Integer, Product> productMap = new HashMap<>();

        String sql = "SELECT p.ProductID, p.Code, p.ProductTypeID, pt.TypeName, p.ProductName, "
                + "cb.BrandName, p.Quantity, p.WarrantyPeriod, p.Status "
                + "FROM Product p "
                + "JOIN Brand cb ON p.BrandID = cb.BrandID "
                + "JOIN ProductType pt ON p.ProductTypeID = pt.ProductTypeID "
                + "WHERE p.Status != 'inactive' "
                + "ORDER BY p.ProductID "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("ProductID");

                    // Nếu chưa có sản phẩm trong Map, thêm mới vào
                    if (!productMap.containsKey(productId)) {
                        Product product = new Product();
                        product.setProductId(productId);
                        product.setCode(rs.getString("Code"));
                        product.setProductTypeId(rs.getInt("ProductTypeID"));
                        product.setProductTypeName(rs.getString("TypeName"));
                        product.setProductName(rs.getString("ProductName"));
                        product.setBrandName(rs.getString("BrandName"));
                        product.setQuantity(rs.getInt("Quantity"));
                        product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                        product.setStatus(rs.getString("Status"));
                        product.setImages(new ArrayList<>()); // Khởi tạo danh sách ảnh
                        productMap.put(productId, product);
                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
            return products;
        }

        // Nếu danh sách rỗng, không cần lấy ảnh
        if (productMap.isEmpty()) {
            return products;
        }

        // Lấy ảnh sản phẩm từ Media (tái sử dụng hàm getProductImages)
        getProductImages(productMap);

        return products;
    }

    public int totalProductByCustomerId(String customerID, String warrantyCardCode, String warrantyStatus) {
        String query = """
                       select count(*) from Customer c   
                       \t\t\t\t\t\tjoin ProductDetail pd on c.CustomerID = pd.CustomerID
                                              join WarrantyCard wc on pd.ProductDetailID = wc.ProductDetailID
                                              join Product p on pd.ProductID = p.ProductID
                                            where c.CustomerID = ?""";
        if (warrantyCardCode != null && !warrantyCardCode.trim().isEmpty()) {
            query += " and WarrantyCardCode like ?";
        }
        if (warrantyStatus != null && !warrantyStatus.trim().isEmpty()) {
            query += " and warrantyStatus like ?";
        }
        int total = 0;
        try (PreparedStatement ps = connection.prepareStatement(query);) {
            int count = 1;
            ps.setString(count++, customerID);
            if (warrantyCardCode != null && !warrantyCardCode.trim().isEmpty()) {
                ps.setString(count++, "%" + warrantyCardCode + "%");
            }
            if (warrantyStatus != null && !warrantyStatus.trim().isEmpty()) {
                ps.setString(count++, warrantyStatus);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return total;
    }

    public List<Brand> getAllBrands() {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM Brand";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setBrandId(rs.getInt("BrandID"));
                brand.setBrandName(rs.getString("BrandName"));
                brands.add(brand);
            }
        } catch (SQLException e) {
        }
        return brands;
    }

    public List<Product> searchProducts(String searchName, String searchCode, Integer brandId, Integer productTypeId, String sortQuantity, String sortWarranty, int offset, int limit) {
        List<Product> products = new ArrayList<>();
        Map<Integer, Product> productMap = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT p.ProductID, p.Code, p.ProductName, cb.BrandName, p.ProductTypeID, pt.TypeName, "
                + "p.Quantity, p.WarrantyPeriod, p.Status "
                + "FROM Product p "
                + "JOIN Brand cb ON p.BrandID = cb.BrandID "
                + "JOIN ProductType pt ON p.ProductTypeID = pt.ProductTypeID "
                + "WHERE p.Status != 'inactive'");

        // Thêm các điều kiện tìm kiếm
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND p.ProductName LIKE ?");
        }
        if (searchCode != null && !searchCode.trim().isEmpty()) {
            sql.append(" AND p.Code LIKE ?");
        }
        if (brandId != null) {
            sql.append(" AND p.BrandID = ?");
        }
        if (productTypeId != null) {
            sql.append(" AND p.ProductTypeID = ?");
        }

        // Thêm điều kiện sắp xếp
        sql.append(" ORDER BY ");
        if (sortQuantity != null && !sortQuantity.isEmpty()) {
            sql.append("p.Quantity ").append(sortQuantity.equals("asc") ? "ASC" : "DESC");
        }
        if (sortWarranty != null && !sortWarranty.isEmpty()) {
            if (sortQuantity == null || sortQuantity.isEmpty()) {
                sql.append("p.WarrantyPeriod ").append(sortWarranty.equals("asc") ? "ASC" : "DESC");
            } else {
                sql.append(", p.WarrantyPeriod ").append(sortWarranty.equals("asc") ? "ASC" : "DESC");
            }
        }
        // Nếu không có điều kiện sắp xếp, sắp xếp theo ProductID
        if ((sortQuantity == null || sortQuantity.isEmpty()) && (sortWarranty == null || sortWarranty.isEmpty())) {
            sql.append("p.ProductID ASC");
        }
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            // Thêm các tham số tìm kiếm vào PreparedStatement
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(index++, "%" + searchName + "%");
            }
            if (searchCode != null && !searchCode.trim().isEmpty()) {
                ps.setString(index++, "%" + searchCode + "%");
            }
            if (brandId != null) {
                ps.setInt(index++, brandId);
            }
            if (productTypeId != null) {
                ps.setInt(index++, productTypeId);
            }
            ps.setInt(index++, offset);
            ps.setInt(index++, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("ProductID");

                    // Nếu chưa có sản phẩm trong Map, thêm mới vào
                    if (!productMap.containsKey(productId)) {
                        Product product = new Product();
                        product.setProductId(productId);
                        product.setCode(rs.getString("Code"));
                        product.setProductName(rs.getString("ProductName"));
                        product.setBrandName(rs.getString("BrandName"));
                        product.setProductTypeId(rs.getInt("ProductTypeID"));
                        product.setQuantity(rs.getInt("Quantity"));
                        product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                        product.setStatus(rs.getString("Status"));
                        product.setProductTypeName(rs.getString("TypeName"));
                        product.setImages(new ArrayList<>()); // Khởi tạo danh sách ảnh
                        productMap.put(productId, product);
                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
            return products;
        }

        // Nếu danh sách rỗng, không cần lấy ảnh
        if (productMap.isEmpty()) {
            return products;
        }

        // Lấy ảnh sản phẩm từ Media (tái sử dụng hàm getProductImages)
        getProductImages(productMap);

        return products;
    }

    public int getTotalProducts(String searchName, String searchCode, Integer brandId, Integer productTypeId) {
        String sql = "SELECT COUNT(*) FROM Product p WHERE Status != 'inactive'";
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql += " AND p.ProductName LIKE ?";
        }
        if (searchCode != null && !searchCode.trim().isEmpty()) {
            sql += " AND p.Code LIKE ?";
        }
        if (brandId != null) {
            sql += " AND p.BrandID = ?";
        }
        if (productTypeId != null) {
            sql += " AND p.ProductTypeID = ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(index++, "%" + searchName + "%");
            }
            if (searchCode != null && !searchCode.trim().isEmpty()) {
                ps.setString(index++, "%" + searchCode + "%");
            }
            if (brandId != null) {
                ps.setInt(index++, brandId);
            }
            if (productTypeId != null) {
                ps.setInt(index++, productTypeId);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public boolean updateProduct(Product product) throws SQLException {
        String sql = "UPDATE Product SET Code = ?, ProductName = ?, BrandID = ?, ProductTypeID = ?, Quantity = ?, WarrantyPeriod = ?, [Status] = ? WHERE ProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getCode());
            ps.setString(2, product.getProductName());
            ps.setInt(3, product.getBrandId());
            ps.setInt(4, product.getProductTypeId());
            ps.setInt(5, product.getQuantity());
            ps.setInt(6, product.getWarrantyPeriod());
            ps.setString(7, product.getStatus());
            ps.setInt(8, product.getProductId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updateProductImages(int productId, List<String> imagePaths) throws SQLException {
        String deleteSql = "DELETE FROM Media WHERE ObjectID = ? AND ObjectType = 'Product'";
        try (PreparedStatement psDelete = connection.prepareStatement(deleteSql)) {
            psDelete.setInt(1, productId);
            psDelete.executeUpdate();
        }
        String insertSql = "INSERT INTO Media (ObjectID, ObjectType, MediaURL, MediaType) VALUES (?, 'Product', ?, 'image')";
        try (PreparedStatement psInsert = connection.prepareStatement(insertSql)) {
            for (String imagePath : imagePaths) {
                psInsert.setInt(1, productId);
                psInsert.setString(2, imagePath);
                psInsert.executeUpdate();
            }
        }
        return true;
    }

    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT p.ProductID, p.Code, p.ProductName, p.BrandID, b.BrandName, "
                + "p.ProductTypeID, pt.TypeName, p.Quantity, p.WarrantyPeriod, p.Status, "
                + "m.MediaURL "
                + "FROM Product p "
                + "JOIN Brand b ON p.BrandID = b.BrandID "
                + "JOIN ProductType pt ON p.ProductTypeID = pt.ProductTypeID "
                + "LEFT JOIN Media m ON p.ProductID = m.ObjectID AND m.ObjectType = 'Product' "
                + "WHERE p.ProductID = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                List<String> mediaUrls = new ArrayList<>();
                while (rs.next()) {
                    if (product == null) {
                        product = new Product();
                        product.setProductId(rs.getInt("ProductID"));
                        product.setCode(rs.getString("Code"));
                        product.setProductName(rs.getString("ProductName"));
                        product.setBrandId(rs.getInt("BrandID"));
                        product.setBrandName(rs.getString("BrandName"));
                        product.setProductTypeId(rs.getInt("ProductTypeID"));
                        product.setProductTypeName(rs.getString("TypeName"));
                        product.setQuantity(rs.getInt("Quantity"));
                        product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                        product.setStatus(rs.getString("Status"));
                    }
                    String mediaUrl = rs.getString("MediaURL");
                    if (mediaUrl != null) {
                        mediaUrls.add(mediaUrl);
                    }
                }
                if (product != null) {
                    product.setImages(mediaUrls);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return product;
    }

    public ArrayList<ProductDetail> getProductDetailByCustomerID(int customerID) {
        ArrayList<ProductDetail> listProductDetail = new ArrayList<>();
        String sql = """
                     SELECT c.CustomerID,
                            c.UsernameC,
                     \t   c.Name,
                     \t   c.Email,
                     \t   c.Phone,
                     \t   c.Address,
                     \t   pd.ProductCode,
                     \t   p.ProductName,
                     \t   pd.PurchaseDate,
                     \t   p.WarrantyPeriod
                     \t   
                     FROM [Product] p LEFT JOIN ProductDetail pd ON p.ProductID = pd.ProductID
                     LEFT JOIN Customer c ON pd.CustomerID = c.CustomerID
                     WHERE c.CustomerID =?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetail pd = new ProductDetail();
                pd.setCustomerID(rs.getInt("CustomerID"));
                pd.setUsernameC(rs.getString("UsernameC"));
                pd.setName(rs.getString("Name"));
                pd.setEmail(rs.getString("Email"));
                pd.setPhone(rs.getString("Phone"));
                pd.setAddress(rs.getString("Address"));
                pd.setProductCode(rs.getString("ProductCode"));
                pd.setProductName(rs.getString("ProductName"));
                pd.setPurchaseDate(rs.getDate("PurchaseDate"));
                pd.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                listProductDetail.add(pd);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listProductDetail;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO Product (Code, ProductName, BrandID, ProductTypeID, Quantity, WarrantyPeriod, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getCode());
            ps.setString(2, product.getProductName());
            ps.setInt(3, product.getBrandId());
            ps.setInt(4, product.getProductTypeId());
            ps.setInt(5, product.getQuantity());
            ps.setInt(6, product.getWarrantyPeriod());
            ps.setString(7, product.getStatus());
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
        }
        return false;
    }

    public Integer getProductIdByCode(String productCode) {
        String sql = "SELECT ProductID FROM Product WHERE Code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ProductID");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null; // Return null if not found
    }

    public void insertMedia(int objectID, String objectType, String mediaURL, String mediaType) {
        String sql = "INSERT INTO Media(ObjectID, ObjectType, MediaURL, MediaType, UploadedDate) VALUES(?,?,?,?,GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, objectID);
            ps.setString(2, objectType);
            ps.setString(3, mediaURL);
            ps.setString(4, mediaType);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean deactivateProduct(int productId) {
        String sql = "UPDATE Product SET Status = 'inactive' WHERE ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean isProductCodeExists(String code) {
        String query = "SELECT COUNT(*) FROM Product WHERE Code = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean isProductCodeExists(String productCode, int productId) {
        String sql = "SELECT COUNT(*) FROM Product WHERE Code = ? AND ProductID != ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productCode);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public void insertListProducts(List<Product> productList) {
        String sql = "INSERT INTO Product (Code, ProductName, BrandID, ProductTypeID, Quantity, WarrantyPeriod, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Chèn từng sản phẩm vào bảng Product
            for (Product product : productList) {
                ps.setString(1, product.getCode());
                ps.setString(2, product.getProductName());
                ps.setInt(3, product.getBrandId());
                ps.setInt(4, product.getProductTypeId());
                ps.setInt(5, product.getQuantity());
                ps.setInt(6, product.getWarrantyPeriod());
                ps.setString(7, product.getStatus());
                ps.addBatch();
            }
            ps.executeBatch(); // Thực thi batch chèn sản phẩm
            connection.commit(); // Xác nhận transaction sau khi hoàn tất
        } catch (SQLException e) {
            try {
                connection.rollback(); // Hoàn tác nếu có lỗi
            } catch (SQLException rollbackEx) {
                System.out.println("Rollback failed: " + rollbackEx);
            }
            System.out.println(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    public List<ProductType> getAllProductTypes() {
        List<ProductType> types = new ArrayList<>();
        String sql = "SELECT ProductTypeID, TypeName FROM ProductType";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("ProductTypeID");
                String name = rs.getString("TypeName");
                types.add(new ProductType(id, name));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return types;
    }

    public boolean isUnknownProductCodeExists(String code) {
        String sql = "select * from UnknownProduct WHERE ProductCode = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public List<UnknownProduct> getAllUnknownProducts() {
        List<UnknownProduct> unknowProducts = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM UnknownProduct up "
                + "JOIN Customer c ON up.CustomerID = c.CustomerID";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                String receivedDate = "";
                Timestamp timestamp = rs.getTimestamp("ReceivedDate");
                if (timestamp != null) {
                    receivedDate = sdf.format(timestamp);
                }
                unknowProducts.add(new UnknownProduct(
                        rs.getInt("UnknownProductID"),
                        rs.getInt("CustomerID"),
                        rs.getString("ProductName"),
                        rs.getString("ProductCode"),
                        rs.getString("Description"),
                        receivedDate,
                        rs.getString("Name"),
                        rs.getString("Phone")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return unknowProducts;
    }

    public boolean addUnknownProduct(int customerId, String productName, String productCode, String description, String receivedDate) {
        String sql = "INSERT INTO UnknownProduct (CustomerID, ProductName, ProductCode, Description, ReceivedDate) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.setString(2, productName);
            ps.setString(3, productCode);
            ps.setString(4, description);

            // Kiểm tra nếu receivedDate chứa 'T' thì thay thế bằng dấu cách
            if (receivedDate.contains("T")) {
                receivedDate = receivedDate.replace("T", " ");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(receivedDate, formatter);
            ps.setTimestamp(5, Timestamp.valueOf(dateTime));

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public Integer getWarrantyProductIdByUnknownProductId(int unknownProductId) {
        String query = "SELECT WarrantyProductID FROM WarrantyProduct WHERE UnknownProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, unknownProductId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("WarrantyProductID");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public UnknownProduct getUnknownProductById(int unknownProductId) {
        String query = "SELECT * FROM UnknownProduct WHERE UnknownProductID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, unknownProductId);
            try (ResultSet rs = ps.executeQuery()) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (rs.next()) {
                    String receivedDate = "";
                    Timestamp timestamp = rs.getTimestamp("ReceivedDate");
                    if (timestamp != null) {
                        receivedDate = sdf.format(timestamp);
                    }
                    return new UnknownProduct(
                            rs.getInt("UnknownProductID"),
                            rs.getInt("CustomerID"),
                            rs.getString("ProductName"),
                            rs.getString("ProductCode"),
                            rs.getString("Description"),
                            receivedDate
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null; // Trả về null nếu không tìm thấy
    }

    public List<UnknownProduct> searchUnknownProducts(String productCode, String productName, String description, String receivedDate, String customerName, String customerPhone, int page, int pageSize) {
        List<UnknownProduct> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT up.UnknownProductID, up.ProductCode, up.ProductName, up.Description, up.ReceivedDate, "
                + "c.Name AS CustomerName, c.Phone AS CustomerPhone, c.CustomerID "
                + "FROM UnknownProduct up "
                + "JOIN Customer c ON up.CustomerID = c.CustomerID "
                + "WHERE 1=1 ";

        if (productCode != null) {
            sql += "AND up.ProductCode LIKE ? ";
        }
        if (productName != null) {
            sql += "AND up.ProductName LIKE ? ";
        }
        if (description != null) {
            sql += "AND up.Description LIKE ? ";
        }
        if (receivedDate != null) {
            sql += "AND CONVERT(VARCHAR, up.ReceivedDate, 23) = ? ";
        }
        if (customerName != null) {
            sql += "AND c.Name LIKE ? ";
        }
        if (customerPhone != null) {
            sql += "AND c.Phone LIKE ? ";
        }

        sql += "ORDER BY up.ReceivedDate DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            if (productCode != null) {
                ps.setString(index++, "%" + productCode + "%");
            }
            if (productName != null) {
                ps.setString(index++, "%" + productName + "%");
            }
            if (description != null) {
                ps.setString(index++, "%" + description + "%");
            }
            if (receivedDate != null) {
                ps.setString(index++, receivedDate);
            }
            if (customerName != null) {
                ps.setString(index++, "%" + customerName + "%");
            }
            if (customerPhone != null) {
                ps.setString(index++, "%" + customerPhone + "%");
            }

            ps.setInt(index++, offset);
            ps.setInt(index++, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UnknownProduct product = new UnknownProduct(
                        rs.getInt("UnknownProductID"),
                        rs.getInt("CustomerID"),
                        rs.getString("ProductName"),
                        rs.getString("ProductCode"),
                        rs.getString("Description"),
                        rs.getString("ReceivedDate"),
                        rs.getString("CustomerName"),
                        rs.getString("CustomerPhone")
                );
                list.add(product);
            }
        } catch (SQLException e) {

        }

        return list;
    }

    public int countUnknownProducts(String productCode, String productName, String description, String receivedDate, String customerName, String customerPhone) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM UnknownProduct up "
                + "JOIN Customer c ON up.CustomerID = c.CustomerID "
                + "WHERE 1=1 ";

        if (productCode != null) {
            sql += "AND up.ProductCode LIKE ? ";
        }
        if (productName != null) {
            sql += "AND up.ProductName LIKE ? ";
        }
        if (description != null) {
            sql += "AND up.Description LIKE ? ";
        }
        if (receivedDate != null) {
            sql += "AND CONVERT(VARCHAR, up.ReceivedDate, 23) = ? ";
        }
        if (customerName != null) {
            sql += "AND c.Name LIKE ? ";
        }
        if (customerPhone != null) {
            sql += "AND c.Phone LIKE ? ";
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            if (productCode != null) {
                ps.setString(index++, "%" + productCode + "%");
            }
            if (productName != null) {
                ps.setString(index++, "%" + productName + "%");
            }
            if (description != null) {
                ps.setString(index++, "%" + description + "%");
            }
            if (receivedDate != null) {
                ps.setString(index++, receivedDate);
            }
            if (customerName != null) {
                ps.setString(index++, "%" + customerName + "%");
            }
            if (customerPhone != null) {
                ps.setString(index++, "%" + customerPhone + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return count;
    }

    // Phương thức cập nhật UnknownProduct
    public boolean updateUnknownProduct(int unknownProductId, int customerId, String productName, String productCode, String description, String receivedDate) throws SQLException {
        String sql = "UPDATE UnknownProduct SET CustomerID = ?, ProductName = ?, ProductCode = ?, Description = ?, ReceivedDate = ? WHERE UnknownProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, productName);
            stmt.setString(3, productCode);
            stmt.setString(4, description);

            // Kiểm tra nếu receivedDate chứa 'T' thì thay thế bằng dấu cách
            if (receivedDate.contains("T")) {
                receivedDate = receivedDate.replace("T", " ");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(receivedDate, formatter);
            stmt.setTimestamp(5, Timestamp.valueOf(dateTime));
            stmt.setInt(6, unknownProductId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public boolean isDuplicateUnknownProductCode(int unknownProductID, String productCode) {
        String sql = "SELECT COUNT(*) FROM UnknownProduct WHERE ProductCode = ? AND UnknownProductID <> ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productCode);
            ps.setInt(2, unknownProductID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Bạn có thể xử lý ngoại lệ hoặc ném ra ngoại lệ tùy ý
        }
        return false;
    }

    public static void main(String[] args) {
        ProductDAO p = new ProductDAO();
        Product p1 = p.getProductById(1);
        List<String> s = p1.getImages();
        for (String s1 : s) {
            System.out.println(s1);
        }

    }

}

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
import java.util.List;

/**
 *
 * @author sonNH
 */
public class ProductDAO extends DBContext {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.Code,p.ProductTypeID,pt.TypeName ,p.ProductName, cb.BrandName, p.Quantity, p.WarrantyPeriod, p.[Status], p.Image "
                + "FROM Product p "
                + "JOIN Brand cb ON p.BrandID = cb.BrandID "
                + "join ProductType pt on p.ProductTypeID = pt.ProductTypeID"
                + "WHERE p.Status != 'inactive'";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setCode(rs.getString("Code"));
                product.setProductName(rs.getString("ProductName"));
                product.setBrandName(rs.getString("BrandName"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setImage(rs.getString("Image"));
                product.setProductTypeId(rs.getInt("ProductTypeID"));
                product.setProductTypeName(rs.getString("TypeName"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return products;
    }

    public List<Product> getAllProducts(int page, int pageSize) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.Code, p.ProductTypeID, pt.TypeName, p.ProductName, "
                + "cb.BrandName, p.Quantity, p.WarrantyPeriod, p.[Status], p.Image "
                + "FROM Product p "
                + "JOIN Brand cb ON p.BrandID = cb.BrandID "
                + "JOIN ProductType pt ON p.ProductTypeID = pt.ProductTypeID "
                + "WHERE p.Status != 'inactive' "
                + // Thêm dấu cách trước WHERE để tránh lỗi
                "ORDER BY p.ProductID "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize); // Tính toán OFFSET
            ps.setInt(2, pageSize); // FETCH NEXT số lượng bản ghi cần lấy

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setCode(rs.getString("Code"));
                    product.setProductTypeId(rs.getInt("ProductTypeID"));
                    product.setProductTypeName(rs.getString("TypeName"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setBrandName(rs.getString("BrandName"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    product.setImage(rs.getString("Image"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }
        return products;
    }

// feedback ===================================================================================================
    public ArrayList<ProductDetail> getListProductByCustomerID(String customerID, String warrantyCardCode, String warrantyStatus, int page, int pageSize) {
        ArrayList<ProductDetail> list = new ArrayList<>();
        String query = """
          select wc.WarrantyCardID, wc.WarrantyCardCode,wc.CreatedDate, p.ProductName, wc.IssueDescription, wc.WarrantyStatus 
                                      from Customer c 
                                      left join ProductDetail pd on c.CustomerID = pd.CustomerID
                                      join WarrantyCard wc on pd.ProductDetailID = wc.ProductDetailID
                                      join Product p on pd.ProductID = p.ProductID
                                      where c.CustomerID = ?""";
        if (warrantyCardCode != null && !warrantyCardCode.trim().isEmpty()) {
            query += " and WarrantyCardCode like ?";
        }
        if (warrantyStatus != null && !warrantyStatus.trim().isEmpty()) {
            query += " and warrantyStatus like ?";
        }
        query += " order by wc.CreatedDate desc ";
        query += " offset ? rows  fetch next ? rows only;";
        try (PreparedStatement ps = connection.prepareStatement(query);) {
            int count = 1;
            ps.setString(count++, customerID);
            if (warrantyCardCode != null && !warrantyCardCode.trim().isEmpty()) {
                ps.setString(count++, "%" + warrantyCardCode + "%");
            }
            if (warrantyStatus != null && !warrantyStatus.trim().isEmpty()) {
                ps.setString(count++, warrantyStatus);
            }
            int offset = (page - 1) * pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                productDetail.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                productDetail.setProductName(rs.getString("ProductName"));
                productDetail.setIssueDescription(rs.getString("IssueDescription"));
                productDetail.setWarrantyStatus(rs.getString("WarrantyStatus"));
                productDetail.setCreatedDate(rs.getDate("CreatedDate"));
                list.add(productDetail);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public ArrayList<ProductDetail> getListProductByCustomerID(String customerID) {
        ArrayList<ProductDetail> list = new ArrayList<>();
        String query = """
        select wc.WarrantyCardID, wc.WarrantyCardCode, p.ProductName, wc.IssueDescription, wc.WarrantyStatus 
                              from Customer c 
                              left join ProductDetail pd on c.CustomerID = pd.CustomerID
                              join WarrantyCard wc on pd.ProductDetailID = wc.ProductDetailID
                              join Product p on pd.ProductID = p.ProductID
                              where c.CustomerID = ?""";

        try (PreparedStatement ps = connection.prepareStatement(query);) {
            int count = 1;
            ps.setString(count++, customerID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                productDetail.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                productDetail.setProductName(rs.getString("ProductName"));
                productDetail.setIssueDescription(rs.getString("IssueDescription"));
                productDetail.setWarrantyStatus(rs.getString("WarrantyStatus"));
                list.add(productDetail);
            }
        } catch (SQLException e) {
        }
        return list;
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
        String sql = "SELECT  p.ProductID, p.Code, p.ProductName, cb.BrandName, p.ProductTypeID, pt.TypeName, p.Quantity, p.WarrantyPeriod, p.[Status], p.Image "
                + "FROM Product p JOIN Brand cb ON p.BrandID = cb.BrandID JOIN ProductType pt ON p.ProductTypeID = pt.ProductTypeID WHERE  p.Status != 'inactive'";

        // Thêm các điều kiện tìm kiếm
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

        // Thêm điều kiện sắp xếp
        if (sortQuantity != null && !sortQuantity.isEmpty()) {
            sql += " ORDER BY p.Quantity " + (sortQuantity.equals("asc") ? "ASC" : "DESC");
        }
        if (sortWarranty != null && !sortWarranty.isEmpty()) {
            if (sortQuantity == null || sortQuantity.isEmpty()) {
                sql += " ORDER BY p.WarrantyPeriod " + (sortWarranty.equals("asc") ? "ASC" : "DESC");
            } else {
                sql += ", p.WarrantyPeriod " + (sortWarranty.equals("asc") ? "ASC" : "DESC");
            }
        }
        // Nếu không có điều kiện sắp xếp, sắp xếp theo ProductID
        if (sortQuantity == null || sortQuantity.isEmpty()) {
            if (sortWarranty == null || sortWarranty.isEmpty()) {
                sql += " ORDER BY p.ProductID ASC";
            }
        }

        // Thêm phân trang
        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setCode(rs.getString("Code"));
                product.setProductName(rs.getString("ProductName"));
                product.setBrandName(rs.getString("BrandName"));
                product.setProductTypeId(rs.getInt("ProductTypeID"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setImage(rs.getString("Image"));
                product.setProductTypeName(rs.getString("TypeName"));
                products.add(product);
            }
        } catch (SQLException e) {
        }
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

    public boolean updateProduct(Product product) {
        String sql = "UPDATE product SET productName = ?,Code = ? ,brandId = ?, ProductTypeID = ?, quantity = ?, warrantyPeriod = ?, image = ? WHERE productId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getCode());
            stmt.setInt(3, product.getBrandId());
            stmt.setInt(4, product.getProductTypeId());
            stmt.setInt(5, product.getQuantity());
            stmt.setInt(6, product.getWarrantyPeriod());
            stmt.setString(7, product.getImage());
            stmt.setInt(8, product.getProductId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public Product getProductById(int productId) {
        Product product = new Product();
        String sql = "SELECT p.ProductID, p.Code, p.ProductName, p.BrandID, b.BrandName, p.ProductTypeID, pt.TypeName, p.Quantity, p.WarrantyPeriod, p.[Status], p.Image "
                + "FROM Product p "
                + "JOIN Brand b ON p.BrandID = b.BrandID "
                + "JOIN ProductType pt ON p.ProductTypeID = pt.ProductTypeID "
                + "WHERE p.ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                product.setProductId(rs.getInt("ProductID"));
                product.setCode(rs.getString("Code"));
                product.setProductName(rs.getString("ProductName"));
                product.setBrandId(rs.getInt("BrandId"));
                product.setBrandName(rs.getString("BrandName"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setImage(rs.getString("Image"));
                product.setStatus(rs.getString("Status"));
                product.setProductTypeId(rs.getInt("ProductTypeID"));
                product.setProductTypeName(rs.getString("TypeName"));
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
        String sql = "INSERT INTO Product (Code, ProductName, BrandID, ProductTypeID, Quantity, WarrantyPeriod, Status, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getCode());
            ps.setString(2, product.getProductName());
            ps.setInt(3, product.getBrandId());
            ps.setInt(4, product.getProductTypeId());
            ps.setInt(5, product.getQuantity());
            ps.setInt(6, product.getWarrantyPeriod());
            ps.setString(7, product.getStatus());
            ps.setString(8, product.getImage());
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
        }
        return false;
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
        String sql = "INSERT INTO Product (Code, ProductName, BrandID, p.ProductTypeID, Quantity, WarrantyPeriod, Status, Image) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            for (Product product : productList) {
                ps.setString(1, product.getCode());
                ps.setString(2, product.getProductName());
                ps.setInt(3, product.getBrandId());
                ps.setInt(4, product.getProductTypeId());
                ps.setInt(5, product.getQuantity());
                ps.setInt(6, product.getWarrantyPeriod());
                ps.setString(7, product.getStatus());
                ps.setString(8, product.getImage());

                ps.addBatch(); // Thêm vào batch để tối ưu hóa hiệu suất
            }
            ps.executeBatch(); // Thực thi batch để chèn nhiều bản ghi cùng lúc
        } catch (SQLException e) {
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

    public static void main(String[] args) {
        ProductDAO p = new ProductDAO();

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Brand;
import Model.Product;
import Model.ProductDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sonNH
 */
public class ProductDAO extends DBContext {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.Code, p.ProductName, cb.BrandName, p.[Type], p.Quantity, p.WarrantyPeriod, p.[Status], p.Image "
                + "FROM Product p "
                + "JOIN Brand cb ON p.BrandID = cb.BrandID "
                + "WHERE p.Status != 'inactive'";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setCode(rs.getString("Code"));
                product.setProductName(rs.getString("ProductName"));
                product.setBrandName(rs.getString("BrandName"));
                product.setType(rs.getString("Type"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setImage(rs.getString("Image"));
                products.add(product);
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public List<Product> getAllProducts(int page, int pageSize) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.Code, p.ProductName, cb.BrandName, p.[Type], p.Quantity, p.WarrantyPeriod, p.[Status], p.Image "
                + "FROM Product p "
                + "JOIN Brand cb ON p.BrandID = cb.BrandID "
                + "WHERE p.Status != 'inactive' "
                + "ORDER BY p.ProductID " // Sắp xếp theo ProductID để đảm bảo thứ tự nhất quán
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"; // Áp dụng phân trang

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize); // Số hàng bỏ qua
            ps.setInt(2, pageSize); // Số hàng cần lấy

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setCode(rs.getString("Code"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setBrandName(rs.getString("BrandName"));
                    product.setType(rs.getString("Type"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    product.setImage(rs.getString("Image"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi để dễ debug
        }
        return products;
    }
// feedback ===================================================================================================

    public ArrayList<ProductDetail> getListProductByCustomerID(String customerID, int page, int pageSize) {
        ArrayList<ProductDetail> list = new ArrayList<>();
        String query = """
          select wc.WarrantyCardID, wc.WarrantyCardCode,wc.CreatedDate, p.ProductName, wc.IssueDescription, wc.WarrantyStatus 
                                      from Customer c 
                                      left join ProductDetail pd on c.CustomerID = pd.CustomerID
                                      join WarrantyCard wc on pd.ProductDetailID = wc.ProductDetailID
                                      join Product p on pd.ProductID = p.ProductID
                                      where c.CustomerID = ?""";
        query += " order by wc.CreatedDate desc ";
        query += " offset ? rows  fetch next ? rows only;";
        try (PreparedStatement ps = connection.prepareStatement(query);) {
            int count = 1;
            ps.setString(count++, customerID);
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
            ps.setString(1, customerID);
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

    public int totalProductByCustomerId(String customerID) {
        String query = """
                       select count(*) from Customer c   
                       \t\t\t\t\t\tjoin ProductDetail pd on c.CustomerID = pd.CustomerID
                                              join WarrantyCard wc on pd.ProductDetailID = wc.ProductDetailID
                                              join Product p on pd.ProductID = p.ProductID
                                            where c.CustomerID = ?""";
        int total = 0;
        try (PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return total;
    }
// end feedback ==========================================================================================

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

    public List<Product> searchProducts(String searchName, String searchCode, Integer brandId, String type, String sortQuantity, String sortWarranty, int offset, int limit) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT  p.ProductID, p.Code, p.ProductName, cb.BrandName, p.[Type], p.Quantity, p.WarrantyPeriod, p.[Status], p.Image "
                + "FROM Product p JOIN Brand cb ON p.BrandID = cb.BrandID WHERE  p.Status != 'inactive'";

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
        if (type != null && !type.equals("all")) {
            sql += " AND p.[Type] LIKE ?";
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
            if (type != null && !type.equals("all")) {
                ps.setString(index++, type);
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
                product.setType(rs.getString("Type"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setImage(rs.getString("Image"));
                products.add(product);
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public List<String> getDistinctProductTypes() {
        List<String> productTypes = new ArrayList<>();
        String sql = "SELECT DISTINCT [Type] FROM Product ORDER BY [Type] ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                productTypes.add(rs.getString("Type"));
            }
        } catch (SQLException e) {
        }
        return productTypes;
    }

    public int getTotalProducts(String searchName, String searchCode, Integer brandId, String type) {
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
        if (type != null && !type.equals("all")) {
            sql += " AND p.[Type] LIKE ?";
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
            if (type != null && !type.equals("all")) {
                ps.setString(index++, type);
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
        String sql = "UPDATE product SET productName = ?,Code = ? ,brandId = ?, type = ?, quantity = ?, warrantyPeriod = ?, image = ? WHERE productId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getCode());
            stmt.setInt(3, product.getBrandId());
            stmt.setString(4, product.getType());
            stmt.setInt(5, product.getQuantity());
            stmt.setInt(6, product.getWarrantyPeriod());
            stmt.setString(7, product.getImage());
            stmt.setInt(8, product.getProductId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
        }
        return false;
    }

    public Product getProductById(int productId) {
        Product product = new Product();
        String sql = "SELECT p.ProductID, p.Code, p.ProductName, p.BrandID, b.BrandName, p.[Type], p.Quantity, p.WarrantyPeriod, p.[Status], p.Image "
                + "FROM Product p "
                + "JOIN Brand b ON p.BrandID = b.BrandID "
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
                product.setType(rs.getString("Type"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setImage(rs.getString("Image"));
                product.setStatus(rs.getString("Status"));
            }
        } catch (SQLException e) {

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
        } catch (Exception e) {
        }
        return listProductDetail;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO Product (Code, ProductName, BrandID, Type, Quantity, WarrantyPeriod, Status, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getCode());
            ps.setString(2, product.getProductName());
            ps.setInt(3, product.getBrandId());
            ps.setString(4, product.getType());
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

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        /*
        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            System.out.println(p.getBrandName());
        }
         */
//        ArrayList<ProductDetail> d = productDAO.getListProductByCustomerID("1");
//        for (ProductDetail p : d) {
//            System.out.println(p);
//        }
        System.out.println(productDAO.totalProductByCustomerId("1"));
    }

}

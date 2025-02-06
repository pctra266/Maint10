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

    public ArrayList<ProductDetail> getListProductByCustomerID(String customerID) {
        ArrayList<ProductDetail> list = new ArrayList<>();
        String query = "select p.ProductName, wc.WarrantyCardID \n"
                + "from Customer c \n"
                + "left join ProductDetail pd on c.CustomerID = pd.CustomerID\n"
                + "join WarrantyCard wc on pd.ProductDetailID = wc.ProductDetailID\n"
                + "join Product p on pd.ProductID = p.ProductID\n"
                + "where c.CustomerID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductName(rs.getString(1));
                productDetail.setWarrantyCardID(rs.getInt(2));
                list.add(productDetail);
            }

        } catch (SQLException e) {
        }
        return list;
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
        String sql = "SELECT COUNT(*) FROM Product p WHERE 1=1";

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


    /**
     * Get Product detail by customer ID
     *
     * @param customerID
     * @return
     */
    public ArrayList<ProductDetail> getProductDetailByCustomerID(int customerID) {
        ArrayList<ProductDetail> listProductDetail = new ArrayList<>();
        String sql = "SELECT c.CustomerID,\n"
                + "       c.UsernameC,\n"
                + "	   c.Name,\n"
                + "	   c.Email,\n"
                + "	   c.Phone,\n"
                + "	   c.Address,\n"
                + "	   pd.ProductCode,\n"
                + "	   p.ProductName,\n"
                + "	   pd.PurchaseDate,\n"
                + "	   p.WarrantyPeriod\n"
                + "	   \n"
                + "FROM [Product] p LEFT JOIN ProductDetail pd ON p.ProductID = pd.ProductID\n"
                + "LEFT JOIN Customer c ON pd.CustomerID = c.CustomerID\n"
                + "WHERE c.CustomerID =?";
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


    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        /*
        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            System.out.println(p.getBrandName());
        }
         */
        ArrayList<ProductDetail> d = productDAO.getProductDetailByCustomerID(2);
        for (ProductDetail p : d) {
            System.out.println(p.getPurchaseDate());
        }
    }

}

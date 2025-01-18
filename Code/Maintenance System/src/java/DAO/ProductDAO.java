/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Product;
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
        String sql = "SELECT ProductID, ProductName, Quantity, WarrantyPeriod, Image FROM Product";
        try (
                PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setImage(rs.getString("Image"));
                products.add(product);
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public List<Product> searchProducts(String keyword, Integer quantity, Integer warrantyPeriod) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Product WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND productName LIKE ?");
        }
        if (quantity != null) {
            sql.append(" AND quantity = ?");
        }
        if (warrantyPeriod != null) {
            sql.append(" AND warrantyPeriod = ?");
        }
        try (
                PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
            }
            if (quantity != null) {
                ps.setInt(paramIndex++, quantity);
            }
            if (warrantyPeriod != null) {
                ps.setInt(paramIndex++, warrantyPeriod);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    product.setImage(rs.getString("Image"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public List<Product> getProductsByPage(int pageIndex, int pageSize) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT ProductID, ProductName, Quantity, WarrantyPeriod, Image FROM Product ORDER BY ProductID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int startIndex = (pageIndex - 1) * pageSize;
            ps.setInt(1, startIndex);
            ps.setInt(2, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    product.setImage(rs.getString("Image"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public int getTotalProductCount() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Product";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return total;
    }

    public List<Product> searchProductsWithPagination(String keyword, Integer quantity, Integer warrantyPeriod, int pageIndex, int pageSize) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Product WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND productName LIKE ?");
        }
        if (quantity != null) {
            sql.append(" AND quantity = ?");
        }
        if (warrantyPeriod != null) {
            sql.append(" AND warrantyPeriod = ?");
        }

        sql.append(" ORDER BY ProductID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword.trim() + "%");
            }
            if (quantity != null) {
                ps.setInt(paramIndex++, quantity);
            }
            if (warrantyPeriod != null) {
                ps.setInt(paramIndex++, warrantyPeriod);
            }

            int startIndex = (pageIndex - 1) * pageSize;
            ps.setInt(paramIndex++, startIndex);
            ps.setInt(paramIndex++, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setQuantity(rs.getInt("Quantity"));
                    product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                    product.setImage(rs.getString("Image"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
        }
        return products;
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getProductsByPage(1, 10);
        for (Product p : products) {
            System.out.println(p);
        }

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Brand;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class BrandDAO extends DBContext {

    public List<Brand> getBrands(int page, int pageSize, String search, String sort, String order) {
        List<Brand> brandList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Brand WHERE 1=1");
        if (search != null && !search.isEmpty()) {
            sql.append(" AND BrandName LIKE ?");
        }
       if (sort == null || sort.isEmpty()) {
            sort = "BrandID";  
        }
        if (order == null || !(order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC"))) {
            order = "DESC"; 
        }
                sql.append(" ORDER BY ").append(sort).append(" ").append(order).append(" ");

        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (search != null && !search.isEmpty()) {
                ps.setString(paramIndex++, "%" + search + "%");
            }
            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);
        
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setBrandId(rs.getInt("BrandID"));
                brand.setBrandName(rs.getString("BrandName"));
                brandList.add(brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    public int getTotalBrands(String search) {
        String sql = "SELECT COUNT(*) FROM Brand WHERE 1=1";
        if (search != null && !search.isEmpty()) {
            sql += " AND BrandName LIKE ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (search != null && !search.isEmpty()) {
                ps.setString(1, "%" + search + "%");
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

    public boolean addBrand(Brand brand) {
        String sql = "INSERT INTO Brand (BrandName) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, brand.getBrandName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBrand(int brandID) {
        String sql = "DELETE FROM Brand WHERE BrandID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, brandID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void main(String[] args) {
        BrandDAO b = new BrandDAO();
        System.out.println(b.getBrands(1, 5, "", "", ""));
    }
}

package DAO;

import Model.ProductType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductTypeDAO extends  DBContext{
 

    public List<ProductType> getProductTypes(int page, int pageSize, String search, String sort, String order) {
        List<ProductType> typeList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ProductType WHERE 1=1");
        
        if (search != null && !search.isEmpty()) {
            sql.append(" AND TypeName LIKE ?");
        }
        if (sort == null || sort.isEmpty()) {
            sort = "ProductTypeID";  
        }
        if (order == null || !(order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC"))) {
            order = "DESC"; 
        }
            sql.append(" ORDER BY ").append(sort).append(" ").append(order);
       
        int offset = (page - 1) * pageSize;
        sql.append(" OFFSET ").append(offset).append(" ROWS FETCH NEXT ").append(pageSize).append(" ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (search != null && !search.isEmpty()) {
                ps.setString(paramIndex++, "%" + search + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductType type = new ProductType();
                type.setProductTypeId(rs.getInt("ProductTypeID"));
                type.setTypeName(rs.getString("TypeName"));
                typeList.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeList;
    }

    public int getTotalProductTypes(String search) {
        String sql = "SELECT COUNT(*) FROM ProductType WHERE 1=1";
        if (search != null && !search.isEmpty()) {
            sql += " AND TypeName LIKE ?";
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

    public boolean addProductType(ProductType type) {
        String sql = "INSERT INTO ProductType (TypeName) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, type.getTypeName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProductType(int productTypeID) {
        String sql = "DELETE FROM ProductType WHERE ProductTypeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productTypeID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void main(String[] args) {
        ProductTypeDAO dao = new ProductTypeDAO();
        System.out.println(dao.getProductTypes(1, 10, "", "", ""));
    }
}
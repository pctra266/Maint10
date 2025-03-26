/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ADMIN
 */
import Model.ComponentType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComponentTypeDAO extends DBContext {

    public List<ComponentType> getComponentTypes(int page, int pageSize, String search, String sort, String order) {
        List<ComponentType> typeList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ComponentType WHERE 1=1");

        if (search != null && !search.isEmpty()) {
            sql.append(" AND TypeName LIKE ?");
        }
        
        if (sort == null || sort.isEmpty()) {
            sort = "TypeID";
        }
        if (order == null || !(order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC"))) {
            order = "DESC";
        }
        
        sql.append(" ORDER BY ").append(sort).append(" ").append(order).append(" ");

        int offset = (page - 1) * pageSize;
        sql.append(" OFFSET ").append(offset).append(" ROWS FETCH NEXT ").append(pageSize).append(" ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (search != null && !search.isEmpty()) {
                ps.setString(paramIndex++, "%" + search + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ComponentType type = new ComponentType();
                type.setTypeID(rs.getInt("TypeID"));
                type.setTypeName(rs.getString("TypeName"));
                typeList.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeList;
    }

    public int getTotalComponentTypes(String search) {
        String sql = "SELECT COUNT(*) FROM ComponentType WHERE 1=1";
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

    public boolean addComponentType(ComponentType type) {
        String sql = "INSERT INTO ComponentType (TypeName) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, type.getTypeName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteComponentType(int typeID) {
        String sql = "DELETE FROM ComponentType WHERE TypeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, typeID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

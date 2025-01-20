/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Component;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class ComponentDAO extends DBContext {

    public List<Component> getAllComponents() {
        List<Component> components = new ArrayList<>();
        String query = "SELECT ComponentID, ComponentName, Quantity, Price, Image FROM Component";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Component component = new Component();
                component.setComponentID(resultSet.getInt("ComponentID"));
                component.setComponentName(resultSet.getString("ComponentName"));
                component.setQuantity(resultSet.getInt("Quantity"));
                component.setPrice(resultSet.getDouble("Price"));
                component.setImage(resultSet.getString("Image"));

                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return components;
    }

    public List<Component> getComponentsByPage(int page, int pageSize) {
        List<Component> components = new ArrayList<>();
        String query = "SELECT ComponentID, ComponentName, Quantity, Price, Image "
                + "FROM Component "
                + "ORDER BY ComponentID "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            int offset = (page - 1) * pageSize;
            statement.setInt(1, offset);
            statement.setInt(2, pageSize);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Component component = new Component();
                    component.setComponentID(resultSet.getInt("ComponentID"));
                    component.setComponentName(resultSet.getString("ComponentName"));
                    component.setQuantity(resultSet.getInt("Quantity"));
                    component.setPrice(resultSet.getDouble("Price"));
                    component.setImage(resultSet.getString("Image"));

                    components.add(component);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return components;
    }

    // Phương thức đếm tổng số Component
    public int getTotalComponents() {
        String query = "SELECT COUNT(*) FROM Component";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Component getComponentByID(int componentID) {
        String sql = "SELECT * FROM [dbo].[Component] WHERE componentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, componentID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Component component = new Component();
                    component.setComponentID(rs.getInt("ComponentID"));
                    component.setComponentName(rs.getString("ComponentName"));
                    component.setQuantity(rs.getInt("Quantity"));
                    component.setPrice(rs.getDouble("Price"));
                    component.setImage(rs.getString("Image"));
                    return component;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy component    }
    }

    public void delete(int componentID) {
        String deleteProductComponentsSQL = "DELETE FROM ProductComponents WHERE ComponentID = ?";
        String deleteComponentSQL = "DELETE FROM Component WHERE ComponentID = ?";

        try (
                PreparedStatement deleteProductComponentsStmt = connection.prepareStatement(deleteProductComponentsSQL); PreparedStatement deleteComponentStmt = connection.prepareStatement(deleteComponentSQL)) {
            // Xóa các bản ghi trong ProductComponents liên quan đến ComponentID
            deleteProductComponentsStmt.setInt(1, componentID);
            deleteProductComponentsStmt.executeUpdate();

            // Xóa bản ghi trong Component
            deleteComponentStmt.setInt(1, componentID);
            deleteComponentStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Component component) {
        String query = "INSERT INTO Component (ComponentName, Quantity, Price, Image) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, component.getComponentName());
            statement.setInt(2, component.getQuantity());
            statement.setDouble(3, component.getPrice());
            statement.setString(4, component.getImage());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Component component) {
        String query;
        if (component.getImage() != null) {
            query = "UPDATE Component SET ComponentName = ?, Quantity = ?, Price = ?, Image = ? WHERE ComponentID = ?";
        } else {
            query = "UPDATE Component SET ComponentName = ?, Quantity = ?, Price = ? WHERE ComponentID = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, component.getComponentName());
            statement.setInt(2, component.getQuantity());
            statement.setDouble(3, component.getPrice());

            if (component.getImage() != null) {
                statement.setString(4, component.getImage());
                statement.setInt(5, component.getComponentID());
            } else {
                statement.setInt(4, component.getComponentID());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Component getLast() {
        String query = "SELECT TOP 1 * FROM Component ORDER BY ComponentID DESC ";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                Component component = new Component();
                component.setComponentID(resultSet.getInt("ComponentID"));
                component.setComponentName(resultSet.getString("ComponentName"));
                component.setQuantity(resultSet.getInt("Quantity"));
                component.setPrice(resultSet.getDouble("Price"));
                component.setImage(resultSet.getString("Image"));
                return component;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Component> searchComponentsByPage(String keyword, int page, int pageSize) {
        List<Component> components = new ArrayList<>();
        String sql = "SELECT * FROM Component WHERE "
                + "ComponentName LIKE ? OR "
                + "CAST(Quantity AS NVARCHAR) LIKE ? OR "
                + "CAST(Price AS NVARCHAR) LIKE ? "
                + "ORDER BY ComponentID "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 3; i++) {
                statement.setString(i, searchKeyword);
            }

            int offset = (page - 1) * pageSize;
            statement.setInt(4, offset);
            statement.setInt(5, pageSize);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ComponentID");
                String name = resultSet.getString("ComponentName");
                int quantity = resultSet.getInt("Quantity");
                float price = resultSet.getFloat("Price");
                String image = resultSet.getString("Image");

                Component component = new Component(id, name, quantity, price, image);
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public int getTotalSearchComponents(String keyword) {
        String query = "SELECT COUNT(*) FROM Component WHERE "
                + "ComponentName LIKE ? OR "
                + "CAST(Quantity AS NVARCHAR) LIKE ? OR "
                + "CAST(Price AS NVARCHAR) LIKE ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String searchKeyword = "%" + keyword + "%";

            // Thiết lập các tham số cho PreparedStatement
            for (int i = 1; i <= 3; i++) {
                preparedStatement.setString(i, searchKeyword);
            }

            // Thực hiện truy vấn
            ResultSet resultSet = preparedStatement.executeQuery();

            // Lấy kết quả
            if (resultSet.next()) {
                return resultSet.getInt(1); // Lấy giá trị đếm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi hoặc không tìm thấy
    }

    public List<Component> getComponentsByPageSorted(int page, int pageSize, String sort, String order) {
        String query = "SELECT * FROM Component ORDER BY " + sort + " " + order + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        List<Component> components = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("componentID"),
                        rs.getString("componentName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public List<Component> searchComponentsByPageSorted(String search, int page, int pageSize, String sort, String order) {
        String query = "SELECT * FROM Component WHERE "
                + "ComponentName LIKE ? OR "
                + "CAST(Quantity AS NVARCHAR) LIKE ? OR "
                + "CAST(Price AS NVARCHAR) LIKE ? ORDER BY " + sort + " " + order + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        List<Component> components = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {
            String searchKeyword = "%" + search + "%";
              for (int i = 1; i <= 3; i++) {
                ps.setString(i, searchKeyword);
            }
            ps.setInt(4, (page - 1) * pageSize);
            ps.setInt(5, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("componentID"),
                        rs.getString("componentName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }
    public int getTotalSearchComponentsByFields(String searchName, String searchQuantity, String searchPrice) {
        String query = "SELECT COUNT (*) FROM Component WHERE "
                + "ComponentName LIKE ? AND "
                + "CAST(Quantity AS NVARCHAR) LIKE ? AND "
                + "CAST(Price AS NVARCHAR) LIKE ? ";
        List<Component> components = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {
           
                ps.setString(1, "%"+searchName+"%");
                ps.setString(2, "%"+searchQuantity+"%");
                ps.setString(3, "%"+searchPrice+"%");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Lấy giá trị đếm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public List<Component> searchComponentsByFieldsPage(String searchName, String searchQuantity, String searchPrice, int page, int pageSize) {
        String query = "SELECT * FROM Component WHERE "
                + "ComponentName LIKE ? AND "
                + "CAST(Quantity AS NVARCHAR) LIKE ? AND "
                + "CAST(Price AS NVARCHAR) LIKE ? " 
                + "ORDER BY ComponentID"
                + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        List<Component> components = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {
           
                ps.setString(1, "%"+searchName+"%");
                ps.setString(2, "%"+searchQuantity+"%");
                ps.setString(3, "%"+searchPrice+"%");
            
            ps.setInt(4, (page - 1) * pageSize);
            ps.setInt(5, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("componentID"),
                        rs.getString("componentName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }
    public List<Component> searchComponentsByFieldsPageSorted(String searchName, String searchQuantity, String searchPrice, int page, int pageSize, String sort, String order) {
        String query = "SELECT * FROM Component WHERE "
                + "ComponentName LIKE ? AND "
                + "CAST(Quantity AS NVARCHAR) LIKE ? AND "
                + "CAST(Price AS NVARCHAR) LIKE ? ORDER BY " + sort + " " + order + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        List<Component> components = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(query)) {
           
                ps.setString(1, "%"+searchName+"%");
                ps.setString(2, "%"+searchQuantity+"%");
                ps.setString(3, "%"+searchPrice+"%");
            
            ps.setInt(4, (page - 1) * pageSize);
            ps.setInt(5, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                components.add(new Component(
                        rs.getInt("componentID"),
                        rs.getString("componentName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public static void main(String arg[]) {
        ComponentDAO d = new ComponentDAO();
        System.out.println(d.searchComponentsByFieldsPageSorted("MA", "ss", "", 1, 5,"Quantity","asc"));
    }
}

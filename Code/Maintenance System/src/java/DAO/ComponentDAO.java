/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Component;
import Model.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class ComponentDAO extends DBContext {

    public List<String> getListType() {
        List<String> typeList = new ArrayList<>();
        String query = "SELECT TypeName FROM ComponentType";

        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String typeName = resultSet.getString("TypeName");
                typeList.add(typeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeList; // Chuyển đổi List thành Array
    }

    public List<String> getListBrand() {
        List<String> brandList = new ArrayList<>();
        String query = "SELECT BrandName FROM Brand";

        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String brandName = resultSet.getString("BrandName");
                brandList.add(brandName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList; // Chuyển đổi List thành Array
    }

    public int getQuantityMin() {
        return getQuantityMinMax("min");
    }

    public int getQuantityMax() {
        return getQuantityMinMax("max");
    }

    private int getQuantityMinMax(String order) {
        int price = 0;
        String orderBy = order.equalsIgnoreCase("min") ? "ASC" : "DESC";

        String query = "SELECT TOP 1 Quantity FROM [dbo].[Component] ORDER BY Quantity " + orderBy;

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                price = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi để dễ dàng kiểm tra
        }
        return price;
    }

    public double getPriceMin() {
        return getPriceMinMax("min");
    }

    public double getPriceMax() {
        return getPriceMinMax("max");
    }

    private double getPriceMinMax(String order) {
        double price = 0;
        String orderBy = order.equalsIgnoreCase("min") ? "ASC" : "DESC";

        String query = "SELECT TOP 1 Price FROM [dbo].[Component] ORDER BY Price " + orderBy;

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                price = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi để dễ dàng kiểm tra
        }
        return price;
    }

    public List<Component> getAllComponents() {
        List<Component> components = new ArrayList<>();
        String query = "SELECT c.ComponentID, c.ComponentCode, c.ComponentName, cb.BrandName, ct.TypeName, c.Quantity, c.Price, c.Image "
                + "FROM Component c "
                + "JOIN Brand cb ON c.BrandID = cb.BrandID "
                + "JOIN ComponentType ct ON c.TypeID = ct.TypeID "
                + "WHERE c.Status = 1";

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Component component = new Component();
                component.setComponentID(rs.getInt("ComponentID"));
                component.setComponentCode(rs.getString("ComponentCode"));
                component.setComponentName(rs.getString("ComponentName"));
                component.setBrand(rs.getString("BrandName"));
                component.setType(rs.getString("TypeName"));
                component.setQuantity(rs.getInt("Quantity"));
                component.setPrice(rs.getDouble("Price"));
                component.setImage(rs.getString("Image"));

                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return components;
    }

    public List<Component> getComponentsByPage(int page, int pageSize) {
        List<Component> components = new ArrayList<>();
        String query = "SELECT c.ComponentID, c.ComponentCode, c.ComponentName, c.Quantity, c.Price, c.Image, "
                + "b.BrandName, t.TypeName "
                + "FROM Component c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 "
                + "ORDER BY c.ComponentID "
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
                    component.setComponentCode(resultSet.getString("ComponentCode"));
                    component.setQuantity(resultSet.getInt("Quantity"));
                    component.setPrice(resultSet.getDouble("Price"));
                    component.setImage(resultSet.getString("Image"));
                    component.setBrand(resultSet.getString("BrandName")); // Set the brand name
                    component.setType(resultSet.getString("TypeName"));   // Set the type name

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
        String query = "SELECT COUNT(*) FROM Component where Status=1";
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
        String sql = "SELECT c.ComponentID, c.ComponentCode, c.ComponentName, c.Quantity, c.Price, c.Image, "
                + "b.BrandName, t.TypeName "
                + "FROM [dbo].[Component] c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 AND c.ComponentID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, componentID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Component component = new Component();
                    component.setComponentID(rs.getInt("ComponentID"));
                    component.setComponentName(rs.getString("ComponentName"));
                    component.setComponentCode(rs.getString("ComponentCode"));
                    component.setQuantity(rs.getInt("Quantity"));
                    component.setPrice(rs.getDouble("Price"));
                    component.setImage(rs.getString("Image"));
                    component.setBrand(rs.getString("BrandName")); // Set the brand name
                    component.setType(rs.getString("TypeName"));   // Set the type name
                    return component;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the component is not found
    }

    public boolean delete(int componentID) {
        String sql = "UPDATE Component SET Status = 0 WHERE ComponentID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, componentID); // Thiết lập giá trị cho tham số trong câu lệnh SQL

            int rowsAffected = stmt.executeUpdate(); // Thực thi truy vấn
            return rowsAffected > 0; // Trả về true nếu có dòng bị ảnh hưởng

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi
    }

    public boolean add(Component component) {
        String query = "INSERT INTO Component (ComponentName, ComponentCode, Quantity, Price, Image, BrandID, TypeID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, component.getComponentName());
            statement.setString(2, component.getComponentCode());
            statement.setInt(3, component.getQuantity());
            statement.setDouble(4, component.getPrice());

            if (component.getImage() != null && !component.getImage().isEmpty()) {
                statement.setString(5, component.getImage());
            } else {
                statement.setNull(5, java.sql.Types.NVARCHAR);
            }

            // Set the BrandID and TypeID
            statement.setInt(6, getBrandID(component.getBrand())); // Assuming you have a method to get BrandID
            statement.setInt(7, getTypeID(component.getType()));   // Assuming you have a method to get TypeID

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Return true if the insert was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurs
        }
    }

    public boolean update(Component component) {
        String query;
        if (component.getImage() != null) {
            query = "UPDATE Component SET ComponentCode = ?, ComponentName = ?, Quantity = ?, Price = ?, BrandID = ?, TypeID = ?, Image = ? WHERE ComponentID = ?";
        } else {
            query = "UPDATE Component SET ComponentCode = ?, ComponentName = ?, Quantity = ?, Price = ?, BrandID = ?, TypeID = ? WHERE ComponentID = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set common values
            statement.setString(1, component.getComponentCode());
            statement.setString(2, component.getComponentName());
            statement.setInt(3, component.getQuantity());
            statement.setDouble(4, component.getPrice());

            // Set BrandID and TypeID
            statement.setInt(5, getBrandID(component.getBrand())); // Assuming you have a method to get BrandID
            statement.setInt(6, getTypeID(component.getType()));   // Assuming you have a method to get TypeID

            if (component.getImage() != null) {
                statement.setString(7, component.getImage()); // Set value for Image
                statement.setInt(8, component.getComponentID()); // Set value for ComponentID
            } else {
                statement.setInt(7, component.getComponentID()); // Set value for ComponentID
            }

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurs
        }
    }

    public Component getLast() {
        String query = "SELECT TOP 1 c.ComponentID, c.ComponentCode, c.ComponentName, c.Quantity, c.Price, c.Image, "
                + "b.BrandName, t.TypeName "
                + "FROM Component c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 "
                + "ORDER BY c.ComponentID DESC";

        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                Component component = new Component();
                component.setComponentID(resultSet.getInt("ComponentID"));
                component.setComponentCode(resultSet.getString("ComponentCode"));
                component.setComponentName(resultSet.getString("ComponentName"));
                component.setQuantity(resultSet.getInt("Quantity"));
                component.setPrice(resultSet.getDouble("Price"));
                component.setImage(resultSet.getString("Image"));
                component.setBrand(resultSet.getString("BrandName")); // Set the brand name
                component.setType(resultSet.getString("TypeName"));   // Set the type name
                return component;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no component is found
    }

    public List<Component> searchComponentsByPage(String keyword, int page, int pageSize) {
        List<Component> components = new ArrayList<>();
        String sql = "SELECT c.ComponentID, c.ComponentCode, c.ComponentName, c.Quantity, c.Price, c.Image, "
                + "b.BrandName, t.TypeName "
                + "FROM Component c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 AND "
                + "(c.ComponentCode LIKE ? OR "
                + "c.ComponentName LIKE ? OR "
                + "CAST(c.Quantity AS NVARCHAR) LIKE ? OR "
                + "CAST(c.Price AS NVARCHAR) LIKE ?) "
                + "ORDER BY c.ComponentID "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) {
                statement.setString(i, searchKeyword);
            }

            int offset = (page - 1) * pageSize;
            statement.setInt(5, offset);
            statement.setInt(6, pageSize);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ComponentID");
                String name = resultSet.getString("ComponentName");
                String code = resultSet.getString("ComponentCode");
                int quantity = resultSet.getInt("Quantity");
                double price = resultSet.getDouble("Price");
                String image = resultSet.getString("Image");
                String brand = resultSet.getString("BrandName"); // Get the brand name
                String type = resultSet.getString("TypeName");   // Get the type name

                Component component = new Component(id, code, name, quantity, true, type, brand, price, image);
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public int getTotalSearchComponents(String keyword) {
        String query = "SELECT COUNT(*) FROM Component c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 AND "
                + "(c.ComponentCode LIKE ? OR "
                + "c.ComponentName LIKE ? OR "
                + "CAST(c.Quantity AS NVARCHAR) LIKE ? OR "
                + "CAST(c.Price AS NVARCHAR) LIKE ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String searchKeyword = "%" + keyword + "%";

            // Set parameters for PreparedStatement
            for (int i = 1; i <= 4; i++) {
                preparedStatement.setString(i, searchKeyword);
            }

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get the result
            if (resultSet.next()) {
                return resultSet.getInt(1); // Get the count value
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if there is an error or no results found
    }

    public List<Component> getComponentsByPageSorted(int page, int pageSize, String sort, String order) {
        String query = "SELECT c.ComponentID, c.ComponentCode, c.ComponentName, c.Quantity, c.Price, c.Image, "
                + "b.BrandName, t.TypeName "
                + "FROM Component c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 "
                + "ORDER BY " + sort + " " + order + " "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        List<Component> components = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Component component = new Component(
                        rs.getInt("ComponentID"),
                        rs.getString("ComponentCode"),
                        rs.getString("ComponentName"),
                        rs.getInt("Quantity"),
                        true, // Assuming status is true since we are filtering by Status = 1
                        rs.getString("TypeName"), // Set the type name
                        rs.getString("BrandName"), // Set the brand name
                        rs.getDouble("Price"),
                        rs.getString("Image")
                );
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public List<Component> searchComponentsByPageSorted(String search, int page, int pageSize, String sort, String order) {
        String query = "SELECT c.ComponentID, c.ComponentCode, c.ComponentName, c.Quantity, c.Price, c.Image, "
                + "b.BrandName, t.TypeName "
                + "FROM Component c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 AND "
                + "(c.ComponentName LIKE ? OR "
                + "c.ComponentCode LIKE ? OR "
                + "CAST(c.Quantity AS NVARCHAR) LIKE ? OR "
                + "CAST(c.Price AS NVARCHAR) LIKE ?) "
                + "ORDER BY " + sort + " " + order + " "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        List<Component> components = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            String searchKeyword = "%" + search + "%";
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, searchKeyword);
            }
            ps.setInt(5, (page - 1) * pageSize);
            ps.setInt(6, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Component component = new Component(
                        rs.getInt("ComponentID"),
                        rs.getString("ComponentCode"),
                        rs.getString("ComponentName"),
                        rs.getInt("Quantity"),
                        true, // Assuming status is true since we are filtering by Status = 1
                        rs.getString("TypeName"), // Set the type name
                        rs.getString("BrandName"), // Set the brand name
                        rs.getDouble("Price"),
                        rs.getString("Image")
                );
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public int getTotalSearchComponentsByFields(String searchCode, String searchName, Integer typeId, Integer brandId, Integer minQuantity, Integer maxQuantity, Double minPrice, Double maxPrice) {
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM Component c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 AND "
                + "c.ComponentCode LIKE ? AND "
                + "c.ComponentName LIKE ? ");

        List<Object> parameters = new ArrayList<>();
        parameters.add("%" + searchCode + "%");
        parameters.add("%" + searchName + "%");

        // Điều kiện cho Quantity
        if (minQuantity != null && maxQuantity != null) {
            query.append(" AND c.Quantity BETWEEN ? AND ?");
            parameters.add(minQuantity);
            parameters.add(maxQuantity);
        } else if (minQuantity != null) {
            query.append(" AND c.Quantity >= ?");
            parameters.add(minQuantity);
        } else if (maxQuantity != null) {
            query.append(" AND c.Quantity <= ?");
            parameters.add(maxQuantity);
        }

        // Điều kiện cho Price
        if (minPrice != null && maxPrice != null) {
            query.append(" AND c.Price BETWEEN ? AND ?");
            parameters.add(minPrice);
            parameters.add(maxPrice);
        } else if (minPrice != null) {
            query.append(" AND c.Price >= ?");
            parameters.add(minPrice);
        } else if (maxPrice != null) {
            query.append(" AND c.Price <= ?");
            parameters.add(maxPrice);
        }

        // Điều kiện cho TypeID và BrandID
        if (typeId != null) {
            query.append(" AND c.TypeID = ?");
            parameters.add(typeId);
        }
        if (brandId != null) {
            query.append(" AND c.BrandID = ?");
            parameters.add(brandId);
        }

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Lấy giá trị count
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Component> searchComponentsByFieldsPage(String searchCode, String searchName, int page, int pageSize, Integer typeId, Integer brandId, Integer minQuantity, Integer maxQuantity, Double minPrice, Double maxPrice) {
        StringBuilder query = new StringBuilder("SELECT c.ComponentID, c.ComponentCode, c.ComponentName, c.Quantity, c.Price, c.Image, "
                + "b.BrandName, t.TypeName "
                + "FROM Component c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 AND "
                + "c.ComponentCode LIKE ? AND "
                + "c.ComponentName LIKE ? ");

        List<Object> parameters = new ArrayList<>();
        parameters.add("%" + searchCode + "%");
        parameters.add("%" + searchName + "%");

        // Điều kiện cho Quantity
        if (minQuantity != null && maxQuantity != null) {
            query.append(" AND c.Quantity BETWEEN ? AND ?");
            parameters.add(minQuantity);
            parameters.add(maxQuantity);
        } else if (minQuantity != null) {
            query.append(" AND c.Quantity >= ?");
            parameters.add(minQuantity);
        } else if (maxQuantity != null) {
            query.append(" AND c.Quantity <= ?");
            parameters.add(maxQuantity);
        }

        // Điều kiện cho Price
        if (minPrice != null && maxPrice != null) {
            query.append(" AND c.Price BETWEEN ? AND ?");
            parameters.add(minPrice);
            parameters.add(maxPrice);
        } else if (minPrice != null) {
            query.append(" AND c.Price >= ?");
            parameters.add(minPrice);
        } else if (maxPrice != null) {
            query.append(" AND c.Price <= ?");
            parameters.add(maxPrice);
        }

        // Điều kiện cho TypeID và BrandID
        if (typeId != null) {
            query.append(" AND c.TypeID = ?");
            parameters.add(typeId);
        }
        if (brandId != null) {
            query.append(" AND c.BrandID = ?");
            parameters.add(brandId);
        }

        query.append(" ORDER BY c.ComponentID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        parameters.add((page - 1) * pageSize);
        parameters.add(pageSize);

        List<Component> components = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Component component = new Component(
                        rs.getInt("ComponentID"),
                        rs.getString("ComponentCode"),
                        rs.getString("ComponentName"),
                        rs.getInt("Quantity"),
                        true, // Assuming status is true since we are filtering by Status = 1
                        rs.getString("TypeName"), // Set the type name
                        rs.getString("BrandName"), // Set the brand name
                        rs.getDouble("Price"),
                        rs.getString("Image")
                );
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public List<Component> searchComponentsByFieldsPageSorted(String searchCode, String searchName, int page, int pageSize, String sort, String order, Integer typeId, Integer brandId, Integer minQuantity, Integer maxQuantity, Double minPrice, Double maxPrice) {
        StringBuilder query = new StringBuilder("SELECT c.ComponentID, c.ComponentCode, c.ComponentName, c.Quantity, c.Price, c.Image, "
                + "b.BrandName, t.TypeName "
                + "FROM Component c "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE c.Status = 1 AND "
                + "c.ComponentCode LIKE ? AND "
                + "c.ComponentName LIKE ? ");

        List<Object> parameters = new ArrayList<>();
        parameters.add("%" + searchCode + "%");
        parameters.add("%" + searchName + "%");

        // Điều kiện cho Quantity
        if (minQuantity != null && maxQuantity != null) {
            query.append(" AND c.Quantity BETWEEN ? AND ?");
            parameters.add(minQuantity);
            parameters.add(maxQuantity);
        } else if (minQuantity != null) {
            query.append(" AND c.Quantity >= ?");
            parameters.add(minQuantity);
        } else if (maxQuantity != null) {
            query.append(" AND c.Quantity <= ?");
            parameters.add(maxQuantity);
        }

        // Điều kiện cho Price
        if (minPrice != null && maxPrice != null) {
            query.append(" AND c.Price BETWEEN ? AND ?");
            parameters.add(minPrice);
            parameters.add(maxPrice);
        } else if (minPrice != null) {
            query.append(" AND c.Price >= ?");
            parameters.add(minPrice);
        } else if (maxPrice != null) {
            query.append(" AND c.Price <= ?");
            parameters.add(maxPrice);
        }

        // Điều kiện cho TypeID và BrandID
        if (typeId != null) {
            query.append(" AND c.TypeID = ?");
            parameters.add(typeId);
        }
        if (brandId != null) {
            query.append(" AND c.BrandID = ?");
            parameters.add(brandId);
        }

        // Thêm điều kiện sắp xếp
        query.append(" ORDER BY ").append(sort).append(" ").append(order).append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        parameters.add((page - 1) * pageSize);
        parameters.add(pageSize);

        List<Component> components = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Component component = new Component(
                        rs.getInt("ComponentID"),
                        rs.getString("ComponentCode"),
                        rs.getString("ComponentName"),
                        rs.getInt("Quantity"),
                        true, // Assuming status is true since we are filtering by Status = 1
                        rs.getString("TypeName"), // Set the type name
                        rs.getString("BrandName"), // Set the brand name
                        rs.getDouble("Price"),
                        rs.getString("Image")
                );
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public List<Product> getProductsByComponentId(int componentId) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT p.*, c.ComponentCode, c.ComponentName, b.BrandName, t.TypeName "
                + "FROM Product p "
                + "JOIN ProductComponents pc ON p.ProductID = pc.ProductID "
                + "JOIN Component c ON pc.ComponentID = c.ComponentID "
                + "JOIN Brand b ON c.BrandID = b.BrandID "
                + "JOIN ComponentType t ON c.TypeID = t.TypeID "
                + "WHERE pc.ComponentID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, componentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setCode(rs.getString("Code"));
                product.setProductName(rs.getString("ProductName"));
                product.setBrandId(rs.getInt("BrandID"));
                product.setType(rs.getString("Type"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setStatus(rs.getString("Status"));
                product.setImage(rs.getString("Image"));

                productList.add(product);
            }
        } catch (SQLException e) {
        }
        return productList;
    }
    
public boolean removeProductComponent(int componentId, int productId) {
    String sql = "DELETE FROM ProductComponents WHERE ComponentID = ? AND ProductID = ?";
    
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

        pstmt.setInt(1, componentId);
        pstmt.setInt(2, productId);

        int affectedRows = pstmt.executeUpdate(); // Thực thi câu lệnh xóa
        
        return affectedRows > 0; // Nếu có dòng bị xóa, trả về true
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    public Integer getBrandID(String brandName) {
        String query = "SELECT BrandID FROM Brand WHERE BrandName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, brandName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("BrandID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getTypeID(String typeName) {
        String query = "SELECT TypeID FROM ComponentType WHERE TypeName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, typeName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("TypeID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
  public boolean isComponentCodeExist(String code) {
    String sql = "SELECT ComponentCode FROM Component WHERE ComponentCode = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, code.trim());
        try (ResultSet rs = ps.executeQuery()) {  // Dùng executeQuery()
            return rs.next();  // Nếu có dữ liệu thì trả về true
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;  // Trả về false nếu có lỗi hoặc không tìm thấy
}

    public static void main(String arg[]) throws SQLException {
        ComponentDAO d = new ComponentDAO();
        String searchCode = "";
        String searchName = "MA";
        String searchQuantity = "";
        String searchPrice = "";
        int page = 1;
        int pageSize = 5;
        String sort = "ComponentCode";
        String order = "asc";
        String search = "MA";
        Double minPrice = d.getPriceMin();
        Double maxPrice = d.getPriceMax();
        Integer maxQuantity = d.getQuantityMax();
        Integer minQuantity = d.getQuantityMin();
        System.out.println(d.isComponentCodeExist("MB-LEN-X12"));
        System.out.println(d.getListType());
        System.out.println(d.getBrandID("Apple"));
        System.out.println(d.searchComponentsByFieldsPage("", "", page, pageSize, null, null, minQuantity, maxQuantity, minPrice, maxPrice));
        System.out.println(d.searchComponentsByFieldsPageSorted(searchCode, searchName, 1, 5, sort, order, 1, 1, minQuantity, maxQuantity, minPrice, maxPrice));
        System.out.println(d.getTotalSearchComponentsByFields(searchCode, searchName, 1, 1, minQuantity, maxQuantity, minPrice, maxPrice));
        System.out.println("-----");
        System.out.println(d.searchComponentsByPageSorted(search, page, pageSize, sort, order));
        System.out.println("-------------");
        System.out.println(d.getComponentsByPageSorted(page, pageSize, sort, order));
        System.out.println(d.getTotalSearchComponents(search));
        System.out.println(d.searchComponentsByPage(search, page, pageSize));
        System.out.println(d.getLast());
        System.out.println(d.getComponentByID(2));
        System.out.println(d.getTotalComponents());
        System.out.println(d.getComponentsByPage(page, pageSize));
        System.out.println(d.getAllComponents());
        System.out.println(d.getProductsByComponentId(3));
        Component component = new Component(1, "", "", 11, true, "Display", "Apple", 2222, null);
        System.out.println(d.update(component));
        System.out.println(d.getPriceMin());
        System.out.println(d.getPriceMax());
        System.out.println(d.getQuantityMax());
        System.out.println(d.getQuantityMin());
    }

}

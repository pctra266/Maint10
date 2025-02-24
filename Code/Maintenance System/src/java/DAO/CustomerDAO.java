/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Customer;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class CustomerDAO extends DBContext {

    /**
     * Get customer by username and password
     *
     * @param username
     * @param password
     * @return
     */
    public Customer getCustomerByUsenamePassword(String username, String password) {
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[DateOfBirth]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE UsernameC =? AND PasswordC =?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));

                return customer;

            }
        } catch (SQLException e) {

        }
        return null;
    }

    /**
     * Change password
     *
     * @param c
     */
    public void changePassword(Customer c) {
        String sql = "UPDATE [dbo].[Customer]\n"
                + "   SET [PasswordC] = ?\n"
                + " WHERE UsernameC = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getPasswordC());
            ps.setString(2, c.getUsernameC());
            ps.executeUpdate();

        } catch (SQLException e) {

        }

    }

    /**
     * Get customer by email
     *
     * @param email
     * @return
     */
    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[DateOfBirth]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE Email=?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));
                return customer;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    /**
     * Get all customer
     *
     * @return
     */
    public ArrayList<Customer> getAllCustomer() {
        ArrayList<Customer> listCustomer = new ArrayList<>();
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[DateOfBirth]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();

                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));
                listCustomer.add(customer);
            }
        } catch (Exception e) {

        }
        return listCustomer;

    }

    /**
     * Get customer by ID
     *
     * @param id
     * @return
     */
    public Customer getCustomerByID(int id) {
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[DateOfBirth]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE CustomerID =? ;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));
                return customer;
            }

        } catch (SQLException e) {

        }
        return null;
    }

    /**
     *
     * @param name
     * @param gender
     * @param email
     * @param phone
     * @param address
     * @param day
     * @param month
     * @param year
     * @param sortBy
     * @param sortOrder
     * @param offset
     * @param fetch
     * @return
     */
    public ArrayList<Customer> advancedSearch(String name, String gender, String email, String phone, String address, Integer day, Integer month, Integer year, String sortBy, String sortOrder, int offset, int fetch) {
        ArrayList<Customer> listCustomer = new ArrayList<>();
        String searchName = "%" + name.trim().replaceAll("\\s+", "%") + "%";
        String searchEmail = "%" + email.trim().replaceAll("\\s+", "%") + "%";
        String searchPhone = "%" + phone.trim().replaceAll("\\s+", "%") + "%";
        String searchAddress = "%" + address.trim().replaceAll("\\s+", "%") + "%";
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[DateOfBirth]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE 1 = 1";
        if (searchName != null && !searchName.isEmpty()) {
            sql += " AND Name LIKE ?";
        }
        if (gender != null && !gender.isEmpty()) {
            sql += " AND Gender = ?";
        }
        if (searchEmail != null && !searchEmail.isEmpty()) {
            sql += " AND Email LIKE ?";
        }
        if (searchPhone != null && !searchPhone.isEmpty()) {
            sql += " AND Phone LIKE ?";
        }
        if (searchAddress != null && !searchAddress.isEmpty()) {
            sql += " AND Address LIKE ?";
        }
        if (day != null) {
            sql += " AND DATEPART(DAY, DateOfBirth) = ?";
        }
        if (month != null) {
            sql += " AND MONTH(DateOfBirth) = ?";
        }

        if (year != null) {
            sql += " AND YEAR(DateOfBirth) = ?";
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            sql += " ORDER BY " + sortBy;

            if (sortOrder != null && (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
                sql += " " + sortOrder;  // Thêm khoảng trắng để tránh lỗi cú pháp
            } else {
                sql += " ASC";
            }
        } else {
            sql += " ORDER BY CustomerID ASC";
        }

        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            int index = 1;
            PreparedStatement ps = connection.prepareStatement(sql);
            if (searchName != null && !searchName.isEmpty()) {
                ps.setString(index++, searchName);
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(index++, gender);
            }
            if (searchEmail != null && !searchEmail.isEmpty()) {
                ps.setString(index++, searchEmail);
            }
            if (searchPhone != null && !searchPhone.isEmpty()) {
                ps.setString(index++, searchPhone);
            }
            if (searchAddress != null && !searchAddress.isEmpty()) {
                ps.setString(index++, searchAddress);
            }
            if (day != null) {
                ps.setInt(index++, day);
            }

            if (month != null) {
                ps.setInt(index++, month);
            }

            if (year != null) {
                ps.setInt(index++, year);
            }

            ps.setInt(index++, offset);
            ps.setInt(index++, fetch);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();

                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));
                listCustomer.add(customer);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listCustomer;
    }

    // PHAN TRANG
    /**
     * Get total customer bu page
     *
     * @return
     */
    public int getTotalCustomersPage() {
        String sql = "SELECT COUNT(*) FROM Customer";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

        }
        return 0;
    }

    /**
     * Get customer each page
     *
     * @param offset
     * @param fetch
     * @return
     */
    public ArrayList<Customer> getCustomerPage(int offset, int fetch) {
        ArrayList<Customer> listCustomer = new ArrayList<>();
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[DateOfBirth]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  ORDER BY CustomerID\n"
                + " OFFSET ? ROWS\n"
                + " FETCH NEXT ? ROWS ONLY";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, offset);
            ps.setInt(2, fetch);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();

                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));
                listCustomer.add(customer);
            }
        } catch (SQLException e) {

        }
        return listCustomer;
    }

    /**
     * Get customer by advanced search
     *
     * @param name
     * @param gender
     * @param email
     * @param phone
     * @param address
     * @return
     */
    public int getCustomerAdvancedSearchPage(String name, String gender, String email, String phone, String address, Integer day, Integer month, Integer year) {

        String searchName = "%" + name.trim().replaceAll("\\s+", "%") + "%";
        String searchEmail = "%" + email.trim().replaceAll("\\s+", "%") + "%";
        String searchPhone = "%" + phone.trim().replaceAll("\\s+", "%") + "%";
        String searchAddress = "%" + address.trim().replaceAll("\\s+", "%") + "%";
        String sql = "SELECT COUNT(*) FROM Customer WHERE 1 = 1";
        if (searchName != null && !searchName.isEmpty()) {
            sql += " AND Name LIKE ?";
        }
        if (gender != null && !gender.isEmpty()) {
            sql += " AND Gender = ?";
        }
        if (searchEmail != null && !searchEmail.isEmpty()) {
            sql += " AND Email LIKE ?";
        }
        if (searchPhone != null && !searchPhone.isEmpty()) {
            sql += " AND Phone LIKE ?";
        }
        if (searchAddress != null && !searchAddress.isEmpty()) {
            sql += " AND Address LIKE ?";
        }
        if (day != null) {
            sql += " AND DAY(DateOfBirth) = ?";
        }
        if (month != null) {
            sql += " AND MONTH(DateOfBirth) = ?";
        }

        if (year != null) {
            sql += " AND YEAR(DateOfBirth) = ?";
        }

        try {
            int index = 1;
            PreparedStatement ps = connection.prepareStatement(sql);
            if (searchName != null && !searchName.isEmpty()) {
                ps.setString(index++, searchName);
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(index++, gender);
            }
            if (searchEmail != null && !searchEmail.isEmpty()) {
                ps.setString(index++, searchEmail);
            }
            if (searchPhone != null && !searchPhone.isEmpty()) {
                ps.setString(index++, searchPhone);
            }
            if (searchAddress != null && !searchAddress.isEmpty()) {
                ps.setString(index++, searchAddress);
            }
            if (day != null) {
                ps.setInt(index++, day);
            }

            if (month != null) {
                ps.setInt(index++, month);
            }
            if (year != null) {
                ps.setInt(index++, year);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;

    }

    /**
     * Update Customer
     *
     * @param c
     */
    public void updateCustomer(Customer c) {
        String sql = "UPDATE [dbo].[Customer]\n"
                + "   SET \n"
                + "      [Name] = ?,\n"
                + "      [Gender] = ?,\n"
                + "      [DateOfBirth] = ?,\n"
                + "      [Email] = ?,\n"
                + "      [Phone] = ?,\n"
                + "      [Address] = ?,\n"
                + "      [Image] = ?\n"
                + " WHERE CustomerID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getGender());

            // Chuyển đổi DateOfBirth (nếu không null)
            if (c.getDateOfBirth() != null) {
                ps.setDate(3, new java.sql.Date(c.getDateOfBirth().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            ps.setString(4, c.getEmail());
            ps.setString(5, c.getPhone());
            ps.setString(6, c.getAddress());
            ps.setString(7, c.getImage());
            ps.setInt(8, c.getCustomerID()); // Đúng thứ tự của CustomerID
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có
        }
    }

    /**
     * Add customer
     *
     * @param c
     */
    public void addCustomer(Customer c) {
        String sql = "INSERT INTO [dbo].[Customer]\n"
                + "           ([UsernameC]\n"
                + "           ,[PasswordC]\n"
                + "           ,[Name]\n"
                + "           ,[Gender]\n"
                + "           ,[DateOfBirth]\n"
                + "           ,[Email]\n"
                + "           ,[Phone]\n"
                + "           ,[Address]\n"
                + "           ,[Image])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getUsernameC());
            ps.setString(2, c.getPasswordC());
            ps.setString(3, c.getName());
            ps.setString(4, c.getGender());
            if (c.getDateOfBirth() != null) {
                ps.setDate(5, new java.sql.Date(c.getDateOfBirth().getTime()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }
            ps.setString(6, c.getEmail());
            ps.setString(7, c.getPhone());
            ps.setString(8, c.getAddress());
            ps.setString(9, c.getImage());
            ps.executeUpdate();
        } catch (SQLException e) {

        }

    }

    /**
     * Get customer bu phone
     *
     * @param phone
     * @return
     */
    public Customer getCustomerByPhone(String phone) {
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[DateOfBirth]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE Phone =?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));
                return customer;
            }

        } catch (SQLException e) {

        }
        return null;
    }

    /**
     * Get customer by username
     *
     * @param username
     * @return
     */
    public Customer getCustomerByUsername(String username) {
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[DateOfBirth]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE UsernameC =?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));
                return customer;
            }

        } catch (SQLException e) {

        }
        return null;
    }

    /**
     * Import excel
     *
     * @param customers
     */
    public void importExcelCustomer(ArrayList<Customer> customers) {
        String sql = "INSERT INTO [dbo].[Customer]\n"
                + "           ([UsernameC]\n"
                + "           ,[PasswordC]\n"
                + "           ,[Name]\n"
                + "           ,[Gender]\n"
                + "           ,[DateOfBirth]\n"
                + "           ,[Email]\n"
                + "           ,[Phone]\n"
                + "           ,[Address]\n"
                + "           ,[Image])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (Customer c : customers) {
                ps.setString(1, c.getUsernameC());
                ps.setString(2, c.getPasswordC());
                ps.setString(3, c.getName());
                ps.setString(4, c.getGender());
                if (c.getDateOfBirth() != null) {
                    ps.setDate(5, new java.sql.Date(c.getDateOfBirth().getTime()));
                } else {
                    ps.setNull(5, java.sql.Types.DATE);
                }
                ps.setString(6, c.getEmail());
                ps.setString(7, c.getPhone());
                ps.setString(8, c.getAddress());
                ps.setString(9, c.getImage());
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public Customer getCustomer(String mail, String phone) {
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[DateOfBirth]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE Email = ?\n"
                + "  AND Phone =?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, mail);
            ps.setString(2, phone);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setDateOfBirth(rs.getDate("DateOfBirth"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));
                return customer;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean updateCustomerWithNoImage(Customer customer) {
        String sql = "UPDATE Customer SET Name = ?, Gender = ?, Email = ?, Phone = ?, Address = ?, DateOfBirth = ? WHERE CustomerID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getGender());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());

            // Chuyển đổi DateOfBirth (nếu không null)
            if (customer.getDateOfBirth() != null) {
                ps.setDate(6, new java.sql.Date(customer.getDateOfBirth().getTime()));
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }

            ps.setInt(7, customer.getCustomerID()); // Đặt CustomerID đúng vị trí

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có dòng được cập nhật
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updateCustomerImage(int customerId, String imageUrl) {
        String sql = "UPDATE Customer SET Image = ? WHERE CustomerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, imageUrl);
            stmt.setInt(2, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();

        boolean u;
        u = dao.updateCustomerWithNoImage(new Customer(1, "hello", "male", Date.valueOf("1999-09-09"), "llll", "kkkk", "llll"));

        System.out.println(u);

    }

}

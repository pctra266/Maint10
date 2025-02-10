/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Customer;
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
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE UsernameC =? AND PasswordC =? ;";

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
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));

                return customer;

            }
        } catch (Exception e) {

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

    /// SEARCH
    /**
     * Search customer by name
     *
     * @param txt
     * @return
     */
    public ArrayList<Customer> searchCustomerByName(String txt) {
        ArrayList<Customer> listCustomer = new ArrayList<>();
        String keyword = "%" + txt.trim().replaceAll("\\s+", "%") + "%";
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE Name LIKE ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, keyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();

                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
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
     * Advanced search
     *
     * @param name
     * @param gender
     * @param email
     * @param phone
     * @param address
     * @param page
     * @return
     */
    public ArrayList<Customer> advancedSearch(String name, String gender, String email, String phone, String address, int page) {
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

        sql += " ORDER BY CustomerID\n"
                + " OFFSET ? ROWS\n"
                + " FETCH FIRST 5 ROWS ONLY";

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
            ps.setInt(index++, (page - 1) * 5);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();

                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
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
     * @param index
     * @return
     */
    public ArrayList<Customer> getCustomerPage(int index) {
        ArrayList<Customer> listCustomer = new ArrayList<>();
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Gender]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  ORDER BY CustomerID\n"
                + " OFFSET ? ROWS\n"
                + " FETCH NEXT 5 ROWS ONLY";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, (index - 1) * 5);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();

                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
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
    public int getCustomerAdvancedSearchPage(String name, String gender, String email, String phone, String address) {

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
                + "SET \n"
                + "    [Name] = ?, \n"
                + "    [Gender] = ?, \n"
                + "    [Email] = ?, \n"
                + "    [Phone] = ?, \n"
                + "    [Address] = ?, \n"
                + "    [Image] = ?\n"
                + "WHERE [CustomerID] = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getGender());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getAddress());
            ps.setString(6, c.getImage());
            ps.setInt(7, c.getCustomerID());
            ps.executeUpdate();
        } catch (SQLException e) {

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
                + "      ,[Gender]\n"
                + "           ,[Email]\n"
                + "           ,[Phone]\n"
                + "           ,[Address]\n"
                + "           ,[Image])\n"
                + "     VALUES\n"
                + "          (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getUsernameC());
            ps.setString(2, c.getPasswordC());
            ps.setString(3, c.getName());
            ps.setString(4, c.getGender());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getPhone());
            ps.setString(7, c.getAddress());
            ps.setString(8, c.getImage());
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

    // Sort 
    /**
     * Sort
     *
     * @param sortBy
     * @param sortOrder
     * @param index
     *
     * @return
     */
    public ArrayList<Customer> sort(String sortBy, String sortOrder, int index) {
        ArrayList<Customer> listCustomer = new ArrayList<>();
        String sql = "SELECT [CustomerID], [UsernameC], [PasswordC], [Name], [Gender], [Email], [Phone], [Address], [Image] "
                + "FROM [dbo].[Customer] WHERE 1=1";

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

        sql += " OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, (index - 1) * 5);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
                customer.setGender(rs.getString("Gender"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setImage(rs.getString("Image"));
                listCustomer.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Error while sorting customers: " + e.getMessage());
        }

        return listCustomer;
    }

    

    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        ArrayList<Customer> customer = dao.sort("CustomerID", "DESC", 2);
        for(Customer c: customer) {
            System.out.println(c.getCustomerID());
        }
        
    
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.DBContext;
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
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE Email =?;";
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
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer] ;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
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
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE CustomerID =?;";
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
     * Search customer by name
     *
     * @param txt
     * @return
     */
    public ArrayList<Customer> searchCustomerByName(String txt) {
        ArrayList<Customer> listCustomer = new ArrayList<>();
        String sql = "SELECT [CustomerID]\n"
                + "      ,[UsernameC]\n"
                + "      ,[PasswordC]\n"
                + "      ,[Name]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Customer]\n"
                + "  WHERE Name like ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txt + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setUsernameC(rs.getString("UsernameC"));
                customer.setPasswordC(rs.getString("PasswordC"));
                customer.setName(rs.getString("Name"));
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
     *
     * @return
     */
    public int getNumberPage() {
        String sql = "SELECT count(*) from Customer";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int total = rs.getInt(1);
            int countPage = 0;
            countPage = total / 10;
            if (total % 10 != 0) {
                countPage++;
            } else {
                return countPage;
            }

        } catch (Exception e) {

        }
        return 0;
    }
    /**
     * Update Customer 
     * @param c 
     */
    public void updateCustomer(Customer c) {
        String sql = "UPDATE [dbo].[Customer]\n"
                + "   SET \n"
                + "      [Name] = ?\n"
                + "      ,[Email] = ?\n"
                + "      ,[Phone] = ?\n"
                + "      ,[Address] = ?\n"
                + "      ,[Image] = ?\n"
                + " WHERE CustomerID =?"; 
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2,c.getEmail());
            ps.setString(3, c.getPhone());
            ps.setString(4,c.getAddress());
            ps.setString(5, c.getImage());
            ps.setInt(6, c.getCustomerID());
            ps.executeUpdate();
        } catch(SQLException e) {
            
        }
    }
    
    
   
}

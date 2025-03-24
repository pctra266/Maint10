/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Invoice;
import Model.Admin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class AdminDAO extends DBContext{
    public List<Admin> getAllInvoicesByCus(String search, int pageIndex, int pageSize) {
        List<Admin> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT Customer.Name, Customer.Gender, Customer.Address, Customer.Image,Invoice.CustomerID \n" +
                    "FROM Invoice\n" +
                    "JOIN Customer ON Customer.CustomerID = Invoice.CustomerID";
        if (search != null && !search.trim().isEmpty()) {    
            sql += " WHERE Customer.Name LIKE ?";
        }
        
        sql += " order by Customer.Name offset ? rows  fetch next ? rows only;";
        try{
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (search != null && !search.trim().isEmpty()) {
                stm.setString(count++, "%" + search.trim() + "%");
            }
            int startIndex = (pageIndex - 1) * pageSize;
            stm.setInt(count++, startIndex);
            stm.setInt(count++, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String gender = rs.getString("Gender");
                String image = rs.getString("Image");
                String address = rs.getString("Address");
                String createdBy = rs.getString("CustomerID");
                list.add(new Admin(name, gender, address, image,createdBy));          
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Admin> getAllInvoicesByCus(String search) {
        List<Admin> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT Customer.Name, Customer.Gender, Customer.Address, Customer.Image \n" +
                    "FROM Invoice\n" +
                    "JOIN Customer ON Customer.CustomerID = Invoice.CustomerID";
        if (search != null && !search.trim().isEmpty()) {    
            sql += " WHERE Customer.Name LIKE ?";
        }
        
        try{
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (search != null && !search.trim().isEmpty()) {
                stm.setString(count++, "%" + search.trim() + "%");
            }
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String gender = rs.getString("Gender");
                String image = rs.getString("Image");
                String address = rs.getString("Address");
                list.add(new Admin(name, gender, address, image));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Admin> getAllInvoicesByID(String id) {
        List<Admin> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT DISTINCT Staff.Name, Staff.Gender, Staff.Image,Staff.Address,Staff.StaffID\n" +
"FROM Invoice\n" +
"JOIN Customer ON Customer.CustomerID = Invoice.CustomerID\n" +
"JOIN WarrantyCard ON WarrantyCard.WarrantyCardID = Invoice.WarrantyCardID\n" +
"JOIN Staff ON Staff.StaffID = Invoice.CreatedBy\n" +
"WHERE Invoice.CustomerID=?";
        
        
        try{
            stm = connection.prepareStatement(sql);           
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String gender = rs.getString("Gender");
                String image = rs.getString("Image");
                String address = rs.getString("Address");
                String createdBy = rs.getString("StaffID");
                list.add(new Admin(name, gender, address, image,createdBy));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Admin> getAllInvoicesInformationByID(String staffID, String cusID) {
        List<Admin> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "Select*FROM Invoice\n" +
"JOIN Customer ON Customer.CustomerID = Invoice.CustomerID\n" +
"JOIN WarrantyCard ON WarrantyCard.WarrantyCardID = Invoice.WarrantyCardID\n" +
"JOIN Staff ON Staff.StaffID = Invoice.CreatedBy\n" +
"WHERE Invoice.CreatedBy=? AND Invoice.CustomerID = ?";
        
        
        try{
            stm = connection.prepareStatement(sql);           
            stm.setString(1, staffID);
            stm.setString(2, cusID);
            rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("InvoiceNumber");
                String gender = rs.getString("Amount");
                String image = rs.getString("InvoiceType");
                String address = rs.getString("Status");
                String createdBy = rs.getString("InvoiceID");
                String ID = rs.getString("HandlerID");
                list.add(new Admin(name, gender, address, image,createdBy,ID));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Admin> getAllInformationByPayment(String staffID, String cusID, String invoiceID) {
        List<Admin> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT \n" +
                    "    Invoice.InvoiceNumber AS InvoiceNumber,\n" +
                    "    Invoice.Amount AS Amount,\n" +
                    "    Customer.Name AS CustomerName,\n" +
                    "    Customer.Gender AS CustomerGender,\n" +
                    "    Customer.Phone AS CustomerPhone,\n" +
                    "    WarrantyCard.IssueDescription AS IssueDescription,\n" +
                    "    Staff.Name AS StaffName,\n" +
                    "    Staff.Email AS StaffEmail,\n" +
                    "    Staff.Phone AS StaffPhone,\n" +
                    "    Payment.PaymentDate AS PaymentDate,\n" +
                    "    Payment.PaymentMethod AS PaymentMethod\n" +
                    "FROM Invoice\n" +
                    "JOIN Customer ON Customer.CustomerID = Invoice.CustomerID\n" +
                    "JOIN WarrantyCard ON WarrantyCard.WarrantyCardID = Invoice.WarrantyCardID\n" +
                    "JOIN Staff ON Staff.StaffID = Invoice.CreatedBy\n" +
                    "JOIN Payment ON Payment.InvoiceID = Invoice.InvoiceID\n" +
                    "WHERE Invoice.CreatedBy = ? \n" +
                    "  AND Invoice.CustomerID = ? \n" +
                    "  AND Invoice.InvoiceID = ?;";
        
        
        try{
            stm = connection.prepareStatement(sql);           
            stm.setString(1, staffID);
            stm.setString(2, cusID);
            stm.setString(3, invoiceID);
            rs = stm.executeQuery();
            while (rs.next()) {
                String invoice = rs.getString("InvoiceNumber");
                String amount  = rs.getString("Amount");
                String cusName = rs.getString("CustomerName");
                String cusGender = rs.getString("CustomerGender");
                String cusPhone = rs.getString("CustomerPhone");
                String issue = rs.getString("IssueDescription");
                String staffName  = rs.getString("StaffName");
                String staffEmail = rs.getString("StaffEmail");
                String staffPhone = rs.getString("StaffPhone");
                String paymentDate = rs.getString("PaymentDate");
                String paymentMethod = rs.getString("PaymentMethod");

                list.add(new Admin(invoice, amount, invoice, staffID, invoiceID, issue, staffName, staffEmail, staffPhone, paymentDate, paymentMethod));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
}

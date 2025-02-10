/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Staff;
/**
 *
 * @author ADMIN
 */
public class StaffDAO extends DBContext{
    public Staff getStaffByUsenamePassword(String username, String password) {
        String sql = "SELECT [StaffID]\n"
                + "      ,[UsernameS]\n"
                + "      ,[PasswordS]\n"
                + "      ,[Role]\n"
                + "      ,[Name]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Staff]\n"
                + "  WHERE UsernameS =? AND PasswordS =?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setUsernameS(rs.getString("UsernameS"));
                staff.setPasswordS(rs.getString("PasswordS"));
                staff.setRole(rs.getString("Role"));
                staff.setName(rs.getString("Name"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setAddress(rs.getString("Image"));
                return staff;

            }
        } catch (SQLException e) {

        }
        return null;
    }

    public void changePassword(Staff s) {
        String sql = "UPDATE [dbo].[Staff]\n"
                + "   SET [PasswordS] = ?\n"
                + " WHERE UsernameS = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, s.getPasswordS());
            ps.setString(2, s.getUsernameS());
            ps.executeUpdate();

        } catch (SQLException e) {
        }
    }

    public Staff getStaffByEmail(String email) {
        String sql = "SELECT [StaffID]\n"
                + "      ,[UsernameS]\n"
                + "      ,[PasswordS]\n"
                + "      ,[Role]\n"
                + "      ,[Name]\n"
                + "      ,[Email]\n"
                + "      ,[Phone]\n"
                + "      ,[Address]\n"
                + "      ,[Image]\n"
                + "  FROM [dbo].[Staff]\n"
                + "  WHERE Email = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setUsernameS(rs.getString("UsernameS"));
                staff.setPasswordS(rs.getString("PasswordS"));
                staff.setRole(rs.getString("Role"));
                staff.setName(rs.getString("Name"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));
                staff.setAddress(rs.getString("Address"));
                staff.setAddress(rs.getString("Image"));
                return staff;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public List<Staff> getAllOrder(){
        List<Staff> list = new ArrayList();
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "SELECT * FROM [Staff]";
        try {
            stm = connection.prepareStatement(sql);            
            rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String userNameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                if (image == null || image.isEmpty()) {
                    image = "default-image.png"; // Đặt ảnh mặc định nếu không có ảnh trong DB
                }
                list.add(new Staff(staffID, userNameS, passwordS, role, name, email, phone, address,image));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    public boolean addStaff(String useNameS, String passworldS, String role, String name, String email, String phone, String address,String image){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "INSERT INTO Staff (UsernameS, PasswordS, [Role], [Name], Email, Phone, [Address],Image) VALUES (?,?,?,?,?,?,?,?)";
        try {
            stm = connection.prepareStatement(sql);   
            stm.setString(1, useNameS);
            stm.setString(2, passworldS);
            stm.setString(3, role);
            stm.setString(4, name);
            stm.setString(5, email);
            stm.setString(6, phone);
            stm.setString(7, address);
            stm.setString(8, image);
            
            rs = stm.executeQuery();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
    public boolean updateStaff(String staffID, String useNameS, String passworldS, String role, String name, String email, String phone, String address, String image){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "UPDATE Staff SET UsernameS = ?, PasswordS = ?, Role = ?, Name = ?, Email = ?, Phone = ?, Address = ? ,Image = ? WHERE StaffID = ?;";
        try {
            stm = connection.prepareStatement(sql);   
            stm.setString(1, useNameS);
            stm.setString(2, passworldS);
            stm.setString(3, role);
            stm.setString(4, name);
            stm.setString(5, email);
            stm.setString(6, phone);
            stm.setString(7, address);
            stm.setString(8, image);
            stm.setString(9, staffID);
            
            rs = stm.executeQuery();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
    
    public ArrayList<Staff> getAllStaff(String searchname, String search, int pageIndex, int pageSize, String column, String sortOrder) {
        ArrayList<Staff> list = new ArrayList<>();
        String sql = "select *from Staff";
        PreparedStatement stm =null ;
        ResultSet rs = null;        
        if (searchname != null && !searchname.trim().isEmpty()) {
            if(search.equals("Name")){
                sql +=" WHERE Name LIKE ?";
            }else{
                sql+=" WHERE Role LIKE ?";
            }
        }
        if(column != null && !column.trim().isEmpty() ){
            sql += " order by "+ column+" ";
            if(sortOrder != null && !sortOrder.trim().isEmpty()){
                sql += sortOrder;
            }
        }else{
            sql += " order by StaffID " ;  
            if(sortOrder != null && !sortOrder.trim().isEmpty()){
                sql += sortOrder;
            }
        }
            sql += " offset ? rows  fetch next ? rows only;";
        try {           
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }
            int startIndex = (pageIndex - 1) * pageSize;
            stm.setInt(count++, startIndex);
            stm.setInt(count++, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");               
                list.add(new Staff( staffID, usernameS, passwordS, role, name, email, phone, address, image));
                
            }
        } catch (Exception e) {

        }

        return list;
    }
    public ArrayList<Staff> getAllStaff(String searchname, String search, String column, String sortOrder) {
        ArrayList<Staff> list = new ArrayList<>();
        String sql = "select *from Staff ";
        PreparedStatement stm =null ;
        ResultSet rs = null;        
        if (searchname != null && !searchname.trim().isEmpty()) {
            if(search.equals("Name")){
                sql +=" WHERE Name LIKE ? ";
            }else{
                sql+=" WHERE Role LIKE ? ";
            }
        }
        if(column != null && !column.trim().isEmpty() ){
            sql += " order by "+ column+" ";
            if(sortOrder != null && !sortOrder.trim().isEmpty()){
                sql += sortOrder;
            }
        }else{
            sql += " order by StaffID " ;           
        }
            
        try {           
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }
            
            rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");               
                list.add(new Staff( staffID, usernameS, passwordS, role, name, email, phone, address, image));
                
            }
        } catch (Exception e) {

        }

        return list;
    }
    public Staff getInformationByID(String id){
        Staff staff = new Staff();
        PreparedStatement stm;
        ResultSet rs;
        String sql  = "SELECT * FROM Staff WHERE StaffID = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()){
                int staffID = rs.getInt("staffID");
                String userNameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                if (image == null || image.isEmpty()) {
                    image = "default-image.jpg"; // Đặt ảnh mặc định nếu không có ảnh trong DB
                }
                staff = new Staff(staffID, userNameS, passwordS, role, name, email, phone, address,image);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return staff;
    }
    public boolean deleteStaff(String staffID){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "DELETE FROM Staff WHERE StaffID=?;";
        try {
            stm = connection.prepareStatement(sql);   
            stm.setString(1, staffID);          
            rs = stm.executeQuery();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
    public boolean isPhoneExists(String phone) {
        String query = "SELECT Phone FROM Staff WHERE Phone = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, phone);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isUpdatePhoneExists(String phone, String staffID) {
        String query = "SELECT Phone FROM Staff WHERE Phone = ? AND StaffID <> ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, phone);
            pstmt.setString(2, staffID);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
        List<Staff> list = dao.getAllOrder();
        System.out.println(list);
    }
}

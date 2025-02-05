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
    public List<Staff> searchStaff(String search,String searchname){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        List<Staff> list = new ArrayList();
        StringBuilder sql = new StringBuilder("SELECT * FROM Staff ");
        if(search.equals("Name")){
            sql.append("WHERE Role LIKE ?");
        }else{
            sql.append("WHERE Role LIKE ?");
        }
        

        try {
            stm = connection.prepareStatement(sql.toString());   
            stm.setString(1, "%"+searchname.trim()+"%");     
            
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
                list.add(new Staff(staffID, userNameS, passwordS, role, name, email, phone, address,image));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }
        return list;
    }
    public List<Staff> searchStaffWithPagination(String search,String searchname,int pageIndex, int pageSize){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        List<Staff> list = new ArrayList();
        StringBuilder sql = new StringBuilder("SELECT * FROM Staff ");
        if(search.equals("Name")){
            sql.append("WHERE Name LIKE ?");
        }else{
            sql.append("WHERE Role LIKE ?");
        }
        sql.append(" ORDER BY StaffID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try {
            stm = connection.prepareStatement(sql.toString());   
            stm.setString(1, "%"+searchname.trim()+"%");     
            int startIndex = (pageIndex - 1) * pageSize;
            stm.setInt(2, startIndex);
            stm.setInt(3, pageSize);
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
                list.add(new Staff(staffID, userNameS, passwordS, role, name, email, phone, address,image));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }
        return list;
    }
    public List<Staff> getStaffByPage(int pageIndex, int pageSize) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM StaffORDER BY StaffID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int startIndex = (pageIndex - 1) * pageSize;
            ps.setInt(1, startIndex);
            ps.setInt(2, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
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
                list.add(new Staff(staffID, userNameS, passwordS, role, name, email, phone, address,image));
                }
            }
        } catch (SQLException e) {
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
    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
        List<Staff> list = dao.getAllOrder();
        System.out.println(list);
    }
}

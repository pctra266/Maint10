/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.StaffLog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class StaffLogDAO extends DBContext{
    public ArrayList<StaffLog> getAllStaff(String searchname, String search, int pageIndex, int pageSize, String column, String status, String sortOrder) {
        ArrayList<StaffLog> list = new ArrayList<>();
        String sql = "SELECT * FROM StaffLog";
        PreparedStatement stm = null;
        ResultSet rs = null;

        // Danh sách điều kiện WHERE
        List<String> conditions = new ArrayList<>();

        // Nếu có searchname thì lọc theo Name hoặc Email
        if (searchname != null && !searchname.trim().isEmpty()) {
            if ("Name".equalsIgnoreCase(search)) {
                conditions.add("Name LIKE ?");
            } else {
                conditions.add("Email LIKE ?");
            }
        }

        // Nếu có Status hợp lệ, thêm vào điều kiện WHERE
        if (status != null && (status.equals("Update") || status.equals("Create"))) {
            conditions.add("Status = ?");
        }

        // Nếu có điều kiện, thêm vào câu SQL
        if (!conditions.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", conditions);
        }

        // Thêm sắp xếp
        if (column != null && !column.trim().isEmpty()) {
            sql += " ORDER BY " + column + " " + (sortOrder != null && !sortOrder.trim().isEmpty() ? sortOrder : "");
        } else {
            sql += " ORDER BY StaffID " + (sortOrder != null && !sortOrder.trim().isEmpty() ? sortOrder : "");
        }

        // Thêm phân trang
        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try {
            stm = connection.prepareStatement(sql);
            int count = 1;

            // Gán tham số tìm kiếm
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }

            // Gán tham số Status nếu có
            if (status != null && (status.equals("Update") || status.equals("Create"))) {
                stm.setString(count++, status);
            }

            // Gán phân trang
            int startIndex = (pageIndex - 1) * pageSize;
            stm.setInt(count++, startIndex);
            stm.setInt(count++, pageSize);

            rs = stm.executeQuery();
            while (rs.next()) {
                int stafflogID = rs.getInt("stafflogID");
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("roleid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String date = rs.getString("dateofbirth");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String time = rs.getString("time");
                String image = rs.getString("image");
                String statusResult = rs.getString("status");

                list.add(new StaffLog(stafflogID, staffID, usernameS, passwordS, role, name, gender, date, email, phone, address, image, time, statusResult));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public ArrayList<StaffLog> getAllStaff(String searchname, String search, String column, String sortOrder) {
        ArrayList<StaffLog> list = new ArrayList<>();
        String sql = "select * from StaffLog ";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (searchname != null && !searchname.trim().isEmpty()) {
            if (search.equals("Name")) {
                sql += " WHERE Name LIKE ? ";
            } else {
                sql += " WHERE Email LIKE ? ";
            }
        }
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by StaffID ";
        }

        try {
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                int stafflogID = rs.getInt("stafflogID");
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("roleid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String date = rs.getString("dateofbirth");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String time = rs.getString("time");
                String image = rs.getString("image");
                String status = rs.getString("status");

                list.add(new StaffLog(stafflogID, staffID, usernameS, passwordS, role, name, gender, date, email, phone, address, image, time, status));

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    public String SearchID(String useNameS){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "SELECT StaffID FROM Staff WHERE UsernameS = ? ";
        String staffID = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, useNameS);
            
            rs = stm.executeQuery();
            if (rs.next()) { // Nếu có dữ liệu
                staffID = rs.getString("StaffID"); // Lấy StaffID
            }           
        } catch (SQLException e) {
            System.out.println(e);
        }
        return staffID;
    }
    public boolean createStaff(String staffId, String useNameS, String passworldS,String name,String gender, String date, String role,  String email, String phone, String address,String image){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "INSERT INTO StaffLog (StaffID,UsernameS, PasswordS, Name, Gender, DateOfBirth,[RoleID], Email, Phone, [Address],Image, Time, Status) VALUES (?,?,?,?,?,?,?,?,?,?,?,GETDATE(),'Create')";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, staffId);
            stm.setString(2, useNameS);
            stm.setString(3, passworldS);
            stm.setString(4, name);
            stm.setString(5, gender);
            stm.setString(6, date);
            stm.setString(7, role);
            stm.setString(8, email);
            stm.setString(9, phone);
            stm.setString(10, address);
            stm.setString(11, image);
            
            stm.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
    public boolean addStaff(String staffId, String useNameS, String passworldS,String name,String gender, String date, String role,  String email, String phone, String address,String image){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "INSERT INTO StaffLog (StaffID,UsernameS, PasswordS, Name, Gender, DateOfBirth,[RoleID], Email, Phone, [Address],Image, Time, Status) VALUES (?,?,?,?,?,?,?,?,?,?,?,GETDATE(),'Update')";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, staffId);
            stm.setString(2, useNameS);
            stm.setString(3, passworldS);
            stm.setString(4, name);
            stm.setString(5, gender);
            stm.setString(6, date);
            stm.setString(7, role);
            stm.setString(8, email);
            stm.setString(9, phone);
            stm.setString(10, address);
            stm.setString(11, image);
            
            stm.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
}

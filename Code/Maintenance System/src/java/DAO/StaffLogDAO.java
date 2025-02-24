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
    public ArrayList<StaffLog> getAllStaff(String searchname, String search, int pageIndex, int pageSize, String column, String sortOrder) {
        ArrayList<StaffLog> list = new ArrayList<>();
        String sql = "select * from StaffLog";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (searchname != null && !searchname.trim().isEmpty()) {
            if (search.equals("Name")) {
                sql += " WHERE Name LIKE ?";
            } else {
                sql += " WHERE Email LIKE ?";
            }
        }
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by StaffID ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
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
                int stafflogID = rs.getInt("stafflogID");
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
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
                String role = rs.getString("role");
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
    public boolean addStaff(String staffId, String useNameS, String passworldS,String name,String gender, String date, String role,  String email, String phone, String address,String image){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "INSERT INTO StaffLog (StaffID,UsernameS, PasswordS, Name, Gender, DateOfBirth,[Role], Email, Phone, [Address],Image, Time, Status) VALUES (?,?,?,?,?,?,?,?,?,?,?,GETDATE(),'Update')";
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
            
            rs = stm.executeQuery();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
}

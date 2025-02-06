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
    public List<StaffLog> getAllOrder(){
        List<StaffLog> list = new ArrayList();
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "SELECT * FROM [StaffLog]";
        try {
            stm = connection.prepareStatement(sql);            
            rs = stm.executeQuery();
            while (rs.next()) {
                int stafflogId = rs.getInt("stafflogId");
                int staffId = rs.getInt("staffId");
                String userNameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                String time = rs.getString("time");
                String status = rs.getString("status");
                list.add(new StaffLog(stafflogId, staffId, userNameS, passwordS, role, name, email, phone, address, image, time, status));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    public ArrayList<StaffLog> getAllFeedback(String searchname, String search, int pageIndex, int pageSize, String column, String sortOrder) {
        ArrayList<StaffLog> list = new ArrayList<>();
        String sql = "select *from StaffLog";
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
                int stafflogId = rs.getInt("stafflogId");
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                String time = rs.getString("time");
                String status = rs.getString("status");
                list.add(new StaffLog(stafflogId, staffID, usernameS, passwordS, role, name, email, phone, address, image, time, status));
                
            }
        } catch (Exception e) {

        }

        return list;
    }
    public ArrayList<StaffLog> getAllFeedback(String searchname, String search, String column, String sortOrder) {
        ArrayList<StaffLog> list = new ArrayList<>();
        String sql = "select *from StaffLog ";
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
                int stafflogId = rs.getInt("stafflogId");
                int staffID = rs.getInt("staffID");
                String usernameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String image = rs.getString("image");
                String time = rs.getString("time");
                String status = rs.getString("status");
                list.add(new StaffLog(stafflogId, staffID, usernameS, passwordS, role, name, email, phone, address, image, time, status));
                
            }
        } catch (Exception e) {

        }

        return list;
    }
    public boolean addStaff(String staffId, String useNameS, String passworldS, String role, String name, String email, String phone, String address,String image){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "INSERT INTO StaffLog (StaffID,UsernameS, PasswordS, [Role], [Name], Email, Phone, [Address],Image,Time,Status) VALUES (?,?,?,?,?,?,?,?,?,GETDATE(),'Active')";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, staffId);
            stm.setString(2, useNameS);
            stm.setString(3, passworldS);
            stm.setString(4, role);
            stm.setString(5, name);
            stm.setString(6, email);
            stm.setString(7, phone);
            stm.setString(8, address);
            stm.setString(9, image);
            
            rs = stm.executeQuery();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
}

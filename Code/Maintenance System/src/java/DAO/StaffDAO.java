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
    public List<Staff> getAllOrder(){
        List<Staff> list = new ArrayList();
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "SELECT * FROM [Staff]";
        try {
            stm = connection.prepareStatement(sql);            
            rs = stm.executeQuery();
            while (rs.next()) {
                int staffId = rs.getInt("staffId");
                String userNameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                list.add(new Staff(staffId, userNameS, passwordS, role, name, email, phone, address));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    public boolean addStaff(String useNameS, String passworldS, String role, String name, String email, String phone, String address){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "INSERT INTO Staff (UsernameS, PasswordS, [Role], [Name], Email, Phone, [Address]) VALUES (?,?,?,?,?,?,?)";
        try {
            stm = connection.prepareStatement(sql);   
            stm.setString(1, useNameS);
            stm.setString(2, passworldS);
            stm.setString(3, role);
            stm.setString(4, name);
            stm.setString(5, email);
            stm.setString(6, phone);
            stm.setString(7, address);
            
            rs = stm.executeQuery();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
    public boolean updateStaff(String staffId, String useNameS, String passworldS, String role, String name, String email, String phone, String address){
        PreparedStatement stm =null ;
        ResultSet rs = null;
        String sql = "UPDATE Staff SET UsernameS = ?, PasswordS = ?, Role = ?, Name = ?, Email = ?, Phone = ?, Address = ? WHERE StaffID = ?;";
        try {
            stm = connection.prepareStatement(sql);   
            stm.setString(1, useNameS);
            stm.setString(2, passworldS);
            stm.setString(3, role);
            stm.setString(4, name);
            stm.setString(5, email);
            stm.setString(6, phone);
            stm.setString(7, address);
            stm.setString(8, staffId);
            
            rs = stm.executeQuery();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
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
                int staffId = rs.getInt("staffId");
                String userNameS = rs.getString("usernameS");
                String passwordS = rs.getString("passwordS");
                String role = rs.getString("role");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                staff = new Staff(staffId, userNameS, passwordS, role, name, email, phone, address);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return staff;
    }
    
    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
        List<Staff> list = dao.getAllOrder();
        System.out.println(list);
    }
}

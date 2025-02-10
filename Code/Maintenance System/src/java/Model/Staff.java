/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author PC
 */
public class Staff {


    private int staffID;
    private String usernameS;
    private String passwordS;
    private String role;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String imgage;

    public Staff() {
    }


    public Staff(int staffID, String usernameS, String passwordS, String role, String name, String email, String phone, String address, String imgage) {
        this.staffID = staffID;
        this.usernameS = usernameS;
        this.passwordS = passwordS;
        this.role = role;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.imgage = imgage;
    }

    public int getStaffID() {
        this.imgage = imgage;
        return staffID;
    }   

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getUsernameS() {
        return usernameS;
    }

    public void setUsernameS(String usernameS) {
        this.usernameS = usernameS;
    }

    public String getPasswordS() {
        return passwordS;
    }

    public void setPasswordS(String passwordS) {
        this.passwordS = passwordS;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgage() {
        return imgage;
    }

    public void setImgage(String imgage) {
        this.imgage = imgage;
    }

    
    
    
}

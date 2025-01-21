/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class Staff {
    int staffId;
    String useNameS;
    String passwordS;
    String role;
    String name;
    String email;
    String phone;
    String address;
    String imgage;

    public Staff() {
    }

    public Staff(int staffId, String useNameS, String passwordS, String role, String name, String email, String phone, String address, String imgage) {
        this.staffId = staffId;
        this.useNameS = useNameS;
        this.passwordS = passwordS;
        this.role = role;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.imgage = imgage;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getUseNameS() {
        return useNameS;
    }

    public void setUseNameS(String useNameS) {
        this.useNameS = useNameS;
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

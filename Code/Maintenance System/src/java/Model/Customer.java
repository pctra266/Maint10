/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author PC
 */
public class Customer {

    private int customerID;
    private String usernameC;
    private String passwordC;
    private String name;
    private String Gender;
    private String email;
    private String phone;
    private String address;
    private String image;

    public Customer() {
    }

    public Customer(int customerID, String usernameC, String passwordC, String name, String Gender, String email, String phone, String address, String image) {
        this.customerID = customerID;
        this.usernameC = usernameC;
        this.passwordC = passwordC;
        this.name = name;
        this.Gender = Gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public Customer(String usernameC, String passwordC, String name, String Gender, String email, String phone, String address, String image) {
        this.usernameC = usernameC;
        this.passwordC = passwordC;
        this.name = name;
        this.Gender = Gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getUsernameC() {
        return usernameC;
    }

    public void setUsernameC(String usernameC) {
        this.usernameC = usernameC;
    }

    public String getPasswordC() {
        return passwordC;
    }

    public void setPasswordC(String passwordC) {
        this.passwordC = passwordC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

 
}

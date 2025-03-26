/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class Admin {
    private String name;
    private String gender;
    private String address;
    private String image;
    private String createdBy;
    private String staffID;

    private String invoiceNumber;
    private String amount;
    private String invoiceType;
    private String status;
    private String invoiceID;
    private String issue;
    private String staffName;
    private String staffEmail;
    private String staffPhone;
    private String paymentDate;
    private String paymentMethod;

    public Admin(String invoiceNumber, String amount, String invoiceType, String status, String invoiceID, String issue, String staffName, String staffEmail, String staffPhone, String paymentDate, String paymentMethod) {
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.invoiceType = invoiceType;
        this.status = status;
        this.invoiceID = invoiceID;
        this.issue = issue;
        this.staffName = staffName;
        this.staffEmail = staffEmail;
        this.staffPhone = staffPhone;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }
    
    
    public Admin(String name, String gender, String address, String image, String createdBy, String staffID) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.image = image;
        this.createdBy = createdBy;
        this.staffID = staffID;
    }

    public Admin(String name, String gender, String address, String image, String createdBy) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.image = image;
        this.createdBy = createdBy;
    }

    
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    

    public Admin(String name, String gender, String address, String image) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.image = image;
    }

    public Admin() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
    
     public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    
}

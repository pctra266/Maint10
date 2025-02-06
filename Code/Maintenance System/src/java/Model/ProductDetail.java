/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
import java.util.Date;

public class ProductDetail {
    private int productDetailID;
    private String productCode;
    private Date purchaseDate;
     private int customerID;
    private String usernameC;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String productName;
    private int warrantyPeriod;
    private int WarrantyCardID;

    // Constructor
    public ProductDetail(int productDetailID, String productCode, Date purchaseDate, 
                         String usernameC, String name, String email, 
                         String phone, String address, String productName, 
                         int warrantyPeriod) {
        this.productDetailID = productDetailID;
        this.productCode = productCode;
        this.purchaseDate = purchaseDate;
        this.usernameC = usernameC;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.productName = productName;
        this.warrantyPeriod = warrantyPeriod;
    }

    public ProductDetail() {
    }
    
    public ProductDetail(int WarrantyCardID, String productName) {
        this.WarrantyCardID = WarrantyCardID;
        this.productName = productName;
    }

    public int getProductDetailID() {
        return productDetailID;
    }

    public void setProductDetailID(int productDetailID) {
        this.productDetailID = productDetailID;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getWarrantyCardID() {
        return WarrantyCardID;
    }

    public void setWarrantyCardID(int WarrantyCardID) {
        this.WarrantyCardID = WarrantyCardID;
    }
    
    

    
    

    @Override
    public String toString() {
        return "ProductDetail{" + "productDetailID=" + productDetailID + ", productCode=" + productCode + ", purchaseDate=" + purchaseDate + ", usernameC=" + usernameC + ", name=" + name + ", email=" + email + ", phone=" + phone + ", address=" + address + ", productName=" + productName + ", warrantyPeriod=" + warrantyPeriod + '}';
    }
    
    
}

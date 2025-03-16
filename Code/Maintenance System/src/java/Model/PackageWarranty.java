/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

public class PackageWarranty {
    private String productCode;
    private String customerName;
    private String email;
    private String productName;
    private String extendedWarrantyName;
    private int packageWarrantyID;
    private Date warrantyStartDate;
    private Date warrantyEndDate;
    private String note;
    private boolean isActive;
    
    
    private int extendedWarrantyDetailID;
    
    private Date startExtendedWarranty;
    private Date endExtendedWarranty;

    public PackageWarranty() {
    }

    public String getExtendedWarrantyName() {
        return extendedWarrantyName;
    }

    public void setExtendedWarrantyName(String extendedWarrantyName) {
        this.extendedWarrantyName = extendedWarrantyName;
    }
    
    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }


    public int getExtendedWarrantyDetailID() {
        return extendedWarrantyDetailID;
    }

    public void setExtendedWarrantyDetailID(int extendedWarrantyDetailID) {
        this.extendedWarrantyDetailID = extendedWarrantyDetailID;
    }

    
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPackageWarrantyID() {
        return packageWarrantyID;
    }

    public void setPackageWarrantyID(int packageWarrantyID) {
        this.packageWarrantyID = packageWarrantyID;
    }

    public Date getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(Date warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    public Date getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(Date warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getStartExtendedWarranty() {
        return startExtendedWarranty;
    }

    public void setStartExtendedWarranty(Date startExtendedWarranty) {
        this.startExtendedWarranty = startExtendedWarranty;
    }

    public Date getEndExtendedWarranty() {
        return endExtendedWarranty;
    }

    public void setEndExtendedWarranty(Date endExtendedWarranty) {
        this.endExtendedWarranty = endExtendedWarranty;
    }
}



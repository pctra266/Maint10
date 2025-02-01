package model;

import java.util.Date;

public class WarrantyCard {
    private int warrantyCardID;
    private String warrantyCardCode;
    private int productDetailID;
    private String issueDescription;
    private String warrantyStatus;
    private Date createdDate;
    private String productName;
    private String customerName;
    private String customerPhone;

    public int getWarrantyCardID() {
        return warrantyCardID;
    }

    public void setWarrantyCardID(int warrantyCardID) {
        this.warrantyCardID = warrantyCardID;
    }

    public String getWarrantyCardCode() {
        return warrantyCardCode;
    }

    public void setWarrantyCardCode(String warrantyCardCode) {
        this.warrantyCardCode = warrantyCardCode;
    }

    public int getProductDetailID() {
        return productDetailID;
    }

    public void setProductDetailID(int productDetailID) {
        this.productDetailID = productDetailID;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getWarrantyStatus() {
        return warrantyStatus;
    }

    public void setWarrantyStatus(String warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Override
    public String toString() {
        return "WarrantyCard{" + "warrantyCardID=" + warrantyCardID + ", warrantyCardCode=" + warrantyCardCode + ", productDetailID=" + productDetailID + ", issueDescription=" + issueDescription + ", warrantyStatus=" + warrantyStatus + ", createdDate=" + createdDate + ", productName=" + productName + ", customerName=" + customerName + ", customerPhone=" + customerPhone + '}';
    }
    
}

package Model;

import java.util.Date;

public class WarrantyCard {
    private int warrantyCardID;
    private String warrantyCardCode;
    private int productDetailID;
    private String productCode;
    private String issueDescription;
    private String warrantyStatus;
    private Date createdDate;
    private Date returnDate; //Ngay du kien
    private Date donedDate;     //Ngay sua xong
    private Date completedDate; //Ngay tra may
    private Date canceldDate;  //Ngay huy?
    private String image;
    private String productName;
    private int customerID;
    private String customerName;
    private String customerPhone;

    public int getWarrantyCardID() {
        return warrantyCardID;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returndDate) {
        this.returnDate = returndDate;
    }

    public Date getDonedDate() {
        return donedDate;
    }

    public void setDonedDate(Date donedDate) {
        this.donedDate = donedDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Date getCanceldDate() {
        return canceldDate;
    }

    public void setCanceldDate(Date canceldDate) {
        this.canceldDate = canceldDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
        return "WarrantyCard{" + "warrantyCardID=" + warrantyCardID + ", warrantyCardCode=" + warrantyCardCode + ", productDetailID=" + productDetailID + ", productCode=" + productCode + ", issueDescription=" + issueDescription + ", warrantyStatus=" + warrantyStatus + ", createdDate=" + createdDate + ", returndDate=" + returnDate + ", donedDate=" + donedDate + ", completedDate=" + completedDate + ", canceldDate=" + canceldDate + ", image=" + image + ", productName=" + productName + ", customerID=" + customerID + ", customerName=" + customerName + ", customerPhone=" + customerPhone + '}';
    }
    

    
   
    

}

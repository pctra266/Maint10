/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tra Pham
 */
public class Feedback {
    private final  SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final  SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
    private int FeedbackID, CustomerID, WarrantyCardID;
    private String Note, CustomerName,CustomerEmail, CustomerPhoneNumber ,ImageURL, VideoURL, 
            productName,unkownProductName, IssueDescription, WarrantyStatus;
    private Date DateCreated;
    private boolean IsDeleted;
    

    public Feedback() {
    }

    
    public Feedback(int FeedbackID, int CustomerID, int WarrantyCardID, String Note, String CustomerName, String ImageURL, String VideoURL, String ProductName, String IssueDescription, String WarrantyStatus, Date DateCreated, boolean IsDeleted) {
        this.FeedbackID = FeedbackID;
        this.CustomerID = CustomerID;
        this.WarrantyCardID = WarrantyCardID;
        this.Note = Note;
        this.CustomerName = CustomerName;
        this.ImageURL = ImageURL;
        this.VideoURL = VideoURL;
        this.productName = ProductName;
        this.IssueDescription = IssueDescription;
        this.WarrantyStatus = WarrantyStatus;
        this.DateCreated = DateCreated;
        this.IsDeleted = IsDeleted;
    }

    public Feedback(int FeedbackID, int CustomerID, int WarrantyCardID, String Note, String CustomerName, String CustomerEmail, String CustomerPhoneNumber, String ImageURL, String VideoURL, String ProductName, String IssueDescription, String WarrantyStatus, Date DateCreated, boolean IsDeleted) {
        this.FeedbackID = FeedbackID;
        this.CustomerID = CustomerID;
        this.WarrantyCardID = WarrantyCardID;
        this.Note = Note;
        this.CustomerName = CustomerName;
        this.CustomerEmail = CustomerEmail;
        this.CustomerPhoneNumber = CustomerPhoneNumber;
        this.ImageURL = ImageURL;
        this.VideoURL = VideoURL;
        this.productName = ProductName;
        this.IssueDescription = IssueDescription;
        this.WarrantyStatus = WarrantyStatus;
        this.DateCreated = DateCreated;
        this.IsDeleted = IsDeleted;
    }

    public String getUnkownProductName() {
        return unkownProductName;
    }

    public void setUnkownProductName(String unkownProductName) {
        this.unkownProductName = unkownProductName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String ImageURL) {
        this.ImageURL = ImageURL;
    }

    public String getVideoURL() {
        return VideoURL;
    }

    public void setVideoURL(String VideoURL) {
        this.VideoURL = VideoURL;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String ProductName) {
        this.productName = ProductName;
    }

    public String getIssueDescription() {
        return IssueDescription;
    }

    public void setIssueDescription(String IssueDescription) {
        this.IssueDescription = IssueDescription;
    }

    public String getWarrantyStatus() {
        return WarrantyStatus;
    }

    public void setWarrantyStatus(String WarrantyStatus) {
        this.WarrantyStatus = WarrantyStatus;
    }

    public String getDateCreated() {
        return  outputFormat.format(DateCreated);
//        
//        try {
//            return outputFormat.parse(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    public void setDateCreated(Date DateCreated) {
        this.DateCreated = DateCreated;
    }

    public boolean isIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }
    
    
    public int getFeedbackID() {
        return FeedbackID;
    }

    public void setFeedbackID(int FeedbackID) {
        this.FeedbackID = FeedbackID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public int getWarrantyCardID() {
        return WarrantyCardID;
    }

    public void setWarrantyCardID(int WarrantyCardID) {
        this.WarrantyCardID = WarrantyCardID;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String CustomerEmail) {
        this.CustomerEmail = CustomerEmail;
    }

    public String getCustomerPhoneNumber() {
        return CustomerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String CustomerPhoneNumber) {
        this.CustomerPhoneNumber = CustomerPhoneNumber;
    }

    @Override
    public String toString() {
        return "Feedback{" + "FeedbackID=" + FeedbackID + ", CustomerID=" + CustomerID + ", WarrantyCardID=" + WarrantyCardID + ", Note=" + Note + ", CustomerName=" + CustomerName + ", CustomerEmail=" + CustomerEmail + ", CustomerPhoneNumber=" + CustomerPhoneNumber + ", ImageURL=" + ImageURL + ", VideoURL=" + VideoURL + ", ProductName=" + productName + ", IssueDescription=" + IssueDescription + ", WarrantyStatus=" + WarrantyStatus + ", DateCreated=" + DateCreated + ", IsDeleted=" + IsDeleted + '}';
    }
    
    
}

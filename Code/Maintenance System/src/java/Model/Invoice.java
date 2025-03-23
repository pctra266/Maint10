/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

public class Invoice {
    private int invoiceID;
    private String invoiceNumber;
    private String invoiceType;
    private int warrantyCardID;
    private double amount;
    private Timestamp issuedDate;
    private Date dueDate;
    private String status;
    private int createdBy;
    private Integer receivedBy;
    private Integer customerID;
    
    // Constructors
    public Invoice() {}

    public Invoice(int invoiceID, String invoiceNumber, String invoiceType, int warrantyCardID, double amount,
                   Timestamp issuedDate, Timestamp dueDate, String status, int createdBy, Integer receivedBy, Integer customerID) {
        this.invoiceID = invoiceID;
        this.invoiceNumber = invoiceNumber;
        this.invoiceType = invoiceType;
        this.warrantyCardID = warrantyCardID;
        this.amount = amount;
        this.issuedDate = issuedDate;
        this.dueDate = dueDate;
        this.status = status;
        this.createdBy = createdBy;
        this.receivedBy = receivedBy;
        this.customerID = customerID;
    }

    // Getters and Setters
    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public int getWarrantyCardID() {
        return warrantyCardID;
    }

    public void setWarrantyCardID(int warrantyCardID) {
        this.warrantyCardID = warrantyCardID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Timestamp issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Integer receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Integer getCustomerID() {
        return customerID;
    }
    
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }
    
     public String getAmountFormat() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
    }
     
    @Override
    public String toString() {
        return "Invoice{" + "invoiceID=" + invoiceID + ", invoiceNumber=" + invoiceNumber + ", invoiceType=" + invoiceType + ", warrantyCardID=" + warrantyCardID + ", amount=" + amount + ", issuedDate=" + issuedDate + ", dueDate=" + dueDate + ", status=" + status + ", createdBy=" + createdBy + ", receivedBy=" + receivedBy + ", customerID=" + customerID + '}';
    }
    
    
}

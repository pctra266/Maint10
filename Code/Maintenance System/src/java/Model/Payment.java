/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Tra Pham
 */
public class Payment {
    private Integer PaymentID,InvoiceID ;
    private Date PaymentDate;
    private String PaymentMethod;
    private double Amount;
    private String Status;


    public Payment() {
    }

    public Payment(Integer PaymentID,  Date PaymentDate, String PaymentMethod, double Amount, String Status, Integer InvoiceID) {
        this.PaymentID = PaymentID;
        this.InvoiceID = InvoiceID;
        this.PaymentDate = PaymentDate;
        this.PaymentMethod = PaymentMethod;
        this.Amount = Amount;
        this.Status = Status;
    }
    
    

    public Integer getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(Integer PaymentID) {
        this.PaymentID = PaymentID;
    }


    public Date getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(Date PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public Integer getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(Integer InvoiceID) {
        this.InvoiceID = InvoiceID;
    }

    @Override
    public String toString() {
        return "Payment{" + "PaymentID=" + PaymentID + ", InvoiceID=" + InvoiceID + ", PaymentDate=" + PaymentDate + ", PaymentMethod=" + PaymentMethod + ", Amount=" + Amount + ", Status=" + Status + '}';
    }
    
    
    
}

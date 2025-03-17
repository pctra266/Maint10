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
    private int PaymentID,WarrantyCardID ;
    private Date PaymentDate;
    private String PaymentMethod;
    private double Amount;
    private String Status;

    public Payment(int PaymentID, int WarrantyCardID, Date PaymentDate, String PaymentMethod, double Amount, String Status) {
        this.PaymentID = PaymentID;
        this.WarrantyCardID = WarrantyCardID;
        this.PaymentDate = PaymentDate;
        this.PaymentMethod = PaymentMethod;
        this.Amount = Amount;
        this.Status = Status;
    }

    public Payment() {
    }

    public int getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(int PaymentID) {
        this.PaymentID = PaymentID;
    }

    public int getWarrantyCardID() {
        return WarrantyCardID;
    }

    public void setWarrantyCardID(int WarrantyCardID) {
        this.WarrantyCardID = WarrantyCardID;
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

    @Override
    public String toString() {
        return "Payment{" + "PaymentID=" + PaymentID + ", WarrantyCardID=" + WarrantyCardID + ", PaymentDate=" + PaymentDate + ", PaymentMethod=" + PaymentMethod + ", Amount=" + Amount + ", Status=" + Status + '}';
    }
    
    
}

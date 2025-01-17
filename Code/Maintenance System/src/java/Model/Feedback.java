/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Tra Pham
 */
public class Feedback {
    private int FeedbackID, CustomerID, WarrantyCardID;
    private String Note;

    public Feedback() {
    }

    public Feedback(int FeedbackID, int CustomerID, int WarrantyCardID, String Note) {
        this.FeedbackID = FeedbackID;
        this.CustomerID = CustomerID;
        this.WarrantyCardID = WarrantyCardID;
        this.Note = Note;
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

    @Override
    public String toString() {
        return "Feedback{" + "FeedbackID=" + FeedbackID + ", CustomerID=" + CustomerID + ", WarrantyCardID=" + WarrantyCardID + ", Note=" + Note + '}';
    }
    
}

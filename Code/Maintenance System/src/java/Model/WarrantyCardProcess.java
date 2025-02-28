package Model;

import java.sql.Timestamp;

public class WarrantyCardProcess {
    private int warrantyCardProcessID;
    private int warrantyCardID;
    private int handlerID;
    private String action;
    private Timestamp actionDate;
    private String note;

    // Default constructor
    public WarrantyCardProcess() {
    }

    // Parameterized constructor
    public WarrantyCardProcess(int warrantyCardProcessID, int warrantyCardID, int handlerID, String action, Timestamp actionDate, String note) {
        this.warrantyCardProcessID = warrantyCardProcessID;
        this.warrantyCardID = warrantyCardID;
        this.handlerID = handlerID;
        this.action = action;
        this.actionDate = actionDate;
        this.note = note;
    }

    // Getters and Setters
    public int getWarrantyCardProcessID() {
        return warrantyCardProcessID;
    }

    public void setWarrantyCardProcessID(int warrantyCardProcessID) {
        this.warrantyCardProcessID = warrantyCardProcessID;
    }

    public int getWarrantyCardID() {
        return warrantyCardID;
    }

    public void setWarrantyCardID(int warrantyCardID) {
        this.warrantyCardID = warrantyCardID;
    }

    public int getHandlerID() {
        return handlerID;
    }

    public void setHandlerID(int handlerID) {
        this.handlerID = handlerID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getActionDate() {
        return actionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "WarrantyCardProcess{" +
                "warrantyCardProcessID=" + warrantyCardProcessID +
                ", warrantyCardID=" + warrantyCardID +
                ", handlerID=" + handlerID +
                ", action='" + action + '\'' +
                ", actionDate=" + actionDate +
                ", note='" + note + '\'' +
                '}';
    }
}
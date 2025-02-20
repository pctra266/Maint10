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
public class ComponentRequest {
    private int componentRequestID, warrantyCardID;
    private Date date;
    private String status, note;

    public ComponentRequest() {
    }

    public int getComponentRequestID() {
        return componentRequestID;
    }

    public void setComponentRequestID(int componentRequestID) {
        this.componentRequestID = componentRequestID;
    }

    public int getWarrantyCardID() {
        return warrantyCardID;
    }

    public void setWarrantyCardID(int warrantyCardID) {
        this.warrantyCardID = warrantyCardID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ComponentRequest{" + "componentRequestID=" + componentRequestID + ", warrantyCardID=" + warrantyCardID + ", date=" + date + ", status=" + status + ", note=" + note + '}';
    }
}

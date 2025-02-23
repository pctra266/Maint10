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
    private Date date, warrantyCreateDate;
    private String status, note, warrantyCode;
    public ComponentRequest() {
    }

    public Date getWarrantyCreateDate() {
        return warrantyCreateDate;
    }

    public void setWarrantyCreateDate(Date warrantyCreateDate) {
        this.warrantyCreateDate = warrantyCreateDate;
    }

    public String getWarrantyCode() {
        return warrantyCode;
    }

    public void setWarrantyCode(String warrantyCode) {
        this.warrantyCode = warrantyCode;
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Tra Pham
 */
import java.text.SimpleDateFormat;
import java.util.Date;

public class SupplementRequest {
    private final  SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
    private int requestID;
    private String componentName;
    private String componentType;
    private Integer typeID;
    private Integer brandID;
    private int requestedBy;
    private Date requestDate;
    private String status;
    private String note;

    public SupplementRequest() {
    }

    public SupplementRequest(int requestID, String componentName, String componentType, Integer typeID, Integer brandID,
                             int requestedBy, Date requestDate, String status, String note) {
        this.requestID = requestID;
        this.componentName = componentName;
        this.componentType = componentType;
        this.typeID = typeID;
        this.brandID = brandID;
        this.requestedBy = requestedBy;
        this.requestDate = requestDate;
        this.status = status;
        this.note = note;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getBrandID() {
        return brandID;
    }

    public void setBrandID(Integer brandID) {
        this.brandID = brandID;
    }

    public int getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(int requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequestDate() {
        return  outputFormat.format(this.requestDate);
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
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
        return "SupplementRequest{" + "requestID=" + requestID + ", componentName=" + componentName + ", componentType=" + componentType + ", typeID=" + typeID + ", brandID=" + brandID + ", requestedBy=" + requestedBy + ", requestDate=" + requestDate + ", status=" + status + ", note=" + note + '}';
    }

    

    
    
}


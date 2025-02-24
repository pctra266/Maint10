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
public class ComponentRequestResponsible {
    private int componentRequestResponsibleID, staffID, componentRequestID;
    private String action, staffName, staffPhone, staffEmail,roleName;
    private Date createDate;

    public ComponentRequestResponsible() {
    }

    public int getComponentRequestResponsibleID() {
        return componentRequestResponsibleID;
    }

    public void setComponentRequestResponsibleID(int componentRequestResponsibleID) {
        this.componentRequestResponsibleID = componentRequestResponsibleID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getComponentRequestID() {
        return componentRequestID;
    }

    public void setComponentRequestID(int componentRequestID) {
        this.componentRequestID = componentRequestID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ComponentRequestResponsible{" + "componentRequestResponsibleID=" + componentRequestResponsibleID + ", staffID=" + staffID + ", componentRequestID=" + componentRequestID + ", action=" + action + ", staffName=" + staffName + ", staffPhone=" + staffPhone + ", staffEmail=" + staffEmail + ", roleName=" + roleName + ", createDate=" + createDate + '}';
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class ReportStaff {
    private int staffID;
    private String name;
    private String gender;
    private String image;
    private String roleName;
    private String action;
    private String actionDate;
    private String warrantyCardCode;

    public ReportStaff(int staffID, String name, String gender, String image, String roleName, String action, String actionDate, String warrantyCardCode) {
        this.staffID = staffID;
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.roleName = roleName;
        this.action = action;
        this.actionDate = actionDate;
        this.warrantyCardCode = warrantyCardCode;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public String getWarrantyCardCode() {
        return warrantyCardCode;
    }

    public void setWarrantyCardCode(String warrantyCardCode) {
        this.warrantyCardCode = warrantyCardCode;
    }
}

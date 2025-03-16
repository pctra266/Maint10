/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Tra Pham
 */
public class ExtendedWarranty {
    private int extendedWarrantyID;
    private String extendedWarrantyName;
    private int extendedPeriodInMonths;
    private double price;
    private String extendedWarrantyDescription;
    private boolean isDelete;
    public ExtendedWarranty() {
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public int getExtendedWarrantyID() {
        return extendedWarrantyID;
    }

    public void setExtendedWarrantyID(int extendedWarrantyID) {
        this.extendedWarrantyID = extendedWarrantyID;
    }

    public String getExtendedWarrantyName() {
        return extendedWarrantyName;
    }

    public void setExtendedWarrantyName(String extendedWarrantyName) {
        this.extendedWarrantyName = extendedWarrantyName;
    }

    public int getExtendedPeriodInMonths() {
        return extendedPeriodInMonths;
    }

    public void setExtendedPeriodInMonths(int extendedPeriodInMonths) {
        this.extendedPeriodInMonths = extendedPeriodInMonths;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExtendedWarrantyDescription() {
        return extendedWarrantyDescription;
    }

    public void setExtendedWarrantyDescription(String extendedWarrantyDescription) {
        this.extendedWarrantyDescription = extendedWarrantyDescription;
    }

    @Override
    public String toString() {
        return "ExtendedWarranty{" + "extendedWarrantyID=" + extendedWarrantyID + ", extendedWarrantyName=" + extendedWarrantyName + ", extendedPeriodInMonths=" + extendedPeriodInMonths + ", price=" + price + ", extendedWarrantyDescription=" + extendedWarrantyDescription + '}';
    }
    
}

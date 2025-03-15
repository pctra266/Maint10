/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class ReportComponent {
    private String componentid;
    private String componentname;
    private String quanity;
    private String price;
    private String inventoryValue;

    public ReportComponent(String componentid, String componentname, String quanity, String price, String inventoryValue) {
        this.componentid = componentid;
        this.componentname = componentname;
        this.quanity = quanity;
        this.price = price;
        this.inventoryValue = inventoryValue;
    }

    public ReportComponent() {
    }

    public String getComponentid() {
        return componentid;
    }

    public void setComponentid(String componentid) {
        this.componentid = componentid;
    }

    public String getComponentname() {
        return componentname;
    }

    public void setComponentname(String componentname) {
        this.componentname = componentname;
    }

    public String getQuanity() {
        return quanity;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInventoryValue() {
        return inventoryValue;
    }

    public void setInventoryValue(String inventoryValue) {
        this.inventoryValue = inventoryValue;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */

public class WarrantyCardDetail{
    private int warrantyCardDetailID;
    private int warrantyCardID;
    private Component component;
    private String status;
    private double price;
    private int quantity;

    // Constants for status validation
    public static final String WARRANTY_REPAIRED = "warranty_repaired";
    public static final String WARRANTY_REPLACED = "warranty_replaced";
    public static final String FIXING = "fixing";
    public static final String REPAIRED = "repaired";
    public static final String REPLACE = "replace";

    // Constructor không tham số
    public WarrantyCardDetail() {}

    // Constructor đầy đủ tham số
    public WarrantyCardDetail(int warrantyCardID, Component component, String status, double price, int quantity) {
        this.warrantyCardID = warrantyCardID;
        this.component = component;
        setStatus(status); // Kiểm tra hợp lệ khi set status
        setPrice(price);   // Kiểm tra hợp lệ khi set price
        setQuantity(quantity); // Kiểm tra hợp lệ khi set quantity
    }

    // Getter & Setter
    public int getWarrantyCardDetailID() {
        return warrantyCardDetailID;
    }

    public void setWarrantyCardDetailID(int warrantyCardDetailID) {
        this.warrantyCardDetailID = warrantyCardDetailID;
    }

    public int getWarrantyCardID() {
        return warrantyCardID;
    }

    public void setWarrantyCardID(int warrantyCardID) {
        this.warrantyCardID = warrantyCardID;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals(FIXING) ||status.equals(WARRANTY_REPLACED) ||status.equals(WARRANTY_REPAIRED) || status.equals(REPAIRED) || status.equals(REPLACE) ) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status value. Allowed values: 'warranty_repaired','warranty_replaced', 'repaired', 'replace','fixing'.");
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must be >= 0.");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0.");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "WarrantyCardDetail{" +
                "warrantyCardDetailID=" + warrantyCardDetailID +
                ", warrantyCardID=" + warrantyCardID +
                ", component=" + component +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

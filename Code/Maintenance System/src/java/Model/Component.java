/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Component {

    private int componentID;       // ID của thành phần
    private String componentCode;
    private String componentName;  // Tên thành phần
    private int quantity;          // Số lượng
    private boolean status;
    private String type;
    private String brand;
    private double price;          // Giá
    private String image;          // Thông tin hoặc đường dẫn hình ảnh

    public Component(int componentID, String componentCode, String componentName, int quantity, boolean status, String type, String brand, double price, String image) {
        this.componentID = componentID;
        this.componentCode = componentCode;
        this.componentName = componentName;
        this.quantity = quantity;
        this.status = status;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.image = image;
    }

    // Constructor mặc định
    public Component() {
    }

    // Getter và Setter cho từng thuộc tính
    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Component{" + "componentID=" + componentID + ", componentCode=" + componentCode + ", componentName=" + componentName + ", quantity=" + quantity + ", status=" + status + ", type=" + type + ", brand=" + brand + ", price=" + price + ", image=" + image + '}';
    }
    
}

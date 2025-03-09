/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
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
    private int brandId;
    private int typeId;
    private String type;
    private String brand;
    private List<String> images;
    private List<String> videos;
    private double price;          // Giá

    

    // Constructor mặc định
    public Component() {
        images = new ArrayList<>();
        videos = new ArrayList<>();
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    
    
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Component{" + "componentID=" + componentID + ", componentCode=" + componentCode + ", componentName=" + componentName + ", quantity=" + quantity + ", status=" + status + ", brandId=" + brandId + ", typeId=" + typeId + ", type=" + type + ", brand=" + brand + ", images=" + images + ", videos=" + videos + ", price=" + price + '}';
    }

    
}

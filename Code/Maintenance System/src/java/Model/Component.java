/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ADMIN
 */
public class Component {
        private int componentID;       // ID của thành phần
    private String componentName;  // Tên thành phần
    private int quantity;          // Số lượng
    private double price;          // Giá
    private String image;          // Thông tin hoặc đường dẫn hình ảnh

    // Constructor mặc định
    public Component() {
    }

    // Constructor đầy đủ
    public Component(int componentID, String componentName, int quantity, double price, String image) {
        this.componentID = componentID;
        this.componentName = componentName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
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

    // Override phương thức toString để hiển thị thông tin
    @Override
    public String toString() {
        return "Component{" +
                "componentID=" + componentID +
                ", componentName='" + componentName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}

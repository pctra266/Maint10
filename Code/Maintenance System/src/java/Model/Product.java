/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author sonNH
 */
public class Product {

    private int productId;
    private String productName;
    private int quantity;
    private int warrantyPeriod;
    private String image;

    public Product() {
    }

    public Product(int productId, String productName, int quantity, int warrantyPeriod, String image) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.image = image;
    }
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod= warrantyPeriod;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

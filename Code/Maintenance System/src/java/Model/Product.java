/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ADMIN
 *
 * package Model;
 *
 * /**
 *
 * @author sonNH
 */
package Model;

public class Product {

    private int productId;
    private String code;
    private String productName;
    private int brandId;
    private String type;
    private int quantity;
    private int warrantyPeriod;
    private String status;
    private String image;
    private String brandName;

    public Product() {
    }

    public Product(int productId, String code, String productName, int brandId, String type, int quantity, int warrantyPeriod, String status, String image) {
        this.productId = productId;
        this.code = code;
        this.productName = productName;
        this.brandId = brandId;
        this.type = type;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.status = status;
        this.image = image;
    }

    public Product(int productId, String code, String productName, int brandId, String type, int quantity, int warrantyPeriod, String status, String image, String brandName) {
        this.productId = productId;
        this.code = code;
        this.productName = productName;
        this.brandId = brandId;
        this.type = type;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.status = status;
        this.image = image;
        this.brandName = brandName;
    }
    
    public Product(String code, String productName, int brandId, String type, int quantity, int warrantyPeriod, String status, String image) {
        this.code = code;
        this.productName = productName;
        this.brandId = brandId;
        this.type = type;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.status = status;
        this.image = image;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}

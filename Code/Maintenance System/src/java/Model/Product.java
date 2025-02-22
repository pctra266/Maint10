/**
 *
 * @author sonNH
 */
package Model;

public class Product {

    private int productId;
    private String code;
    private String productName;
    private int quantity;
    private int warrantyPeriod;
    private String status;
    private String image;
    private int brandId;
    private String brandName;
    private int productTypeId;
    private String productTypeName;

    public Product() {
    }

    public Product(int productId, String code, String productName, int quantity, int warrantyPeriod, String status, String image, int brandId, String brandName, int productTypeId, String productTypeName) {
        this.productId = productId;
        this.code = code;
        this.productName = productName;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.status = status;
        this.image = image;
        this.brandId = brandId;
        this.brandName = brandName;
        this.productTypeId = productTypeId;
        this.productTypeName = productTypeName;
    }

    public Product(String code, String productName, int brandId, int quantity, int warrantyPeriod, String status, String image, int productTypeId) {
        this.code = code;
        this.productName = productName;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.status = status;
        this.image = image;
        this.brandId = brandId;
        this.productTypeId = productTypeId;
    }

    public Product(int productId, String code, String productName, int brandId, int quantity, int warrantyPeriod, String status, String image, int productTypeId) {
        this.productId = productId;
        this.code = code;
        this.productName = productName;
        this.quantity = quantity;
        this.warrantyPeriod = warrantyPeriod;
        this.status = status;
        this.image = image;
        this.brandId = brandId;
        this.productTypeId = productTypeId;
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

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }



}

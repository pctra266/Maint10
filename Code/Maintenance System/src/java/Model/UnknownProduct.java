
package Model;
import java.util.Date;

public class UnknownProduct {

    private int unknowProductId, customerId;
    private String productName, productCode, description;
    private Date receivedDate;
    private String customerName;

    public UnknownProduct(int customerId, String productName, String productCode, String description, Date receivedDate) {
        this.customerId = customerId;
        this.productName = productName;
        this.productCode = productCode;
        this.description = description;
        this.receivedDate = receivedDate;
    }

    public UnknownProduct() {
    }

    public UnknownProduct(int unknowProductId, int customerId, String productName, String productCode, String description, Date receivedDate) {
        this.unknowProductId = unknowProductId;
        this.customerId = customerId;
        this.productName = productName;
        this.productCode = productCode;
        this.description = description;
        this.receivedDate = receivedDate;
    }

    public UnknownProduct(int unknowProductId, int customerId, String productName, String productCode, String description, Date receivedDate, String customerName) {
        this.unknowProductId = unknowProductId;
        this.customerId = customerId;
        this.productName = productName;
        this.productCode = productCode;
        this.description = description;
        this.receivedDate = receivedDate;
        this.customerName = customerName;
    }
    
      public UnknownProduct(int unknowProductId, String customerName, String productName, String productCode, String description, Date receivedDate) {
        this.unknowProductId = unknowProductId;
        this.productName = productName;
        this.productCode = productCode;
        this.description = description;
        this.receivedDate = receivedDate;
        this.customerName = customerName;
    }


    public int getUnknowProductId() {
        return unknowProductId;
    }

    public void setUnknowProductId(int unknowProductId) {
        this.unknowProductId = unknowProductId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    
    
}



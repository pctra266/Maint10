package Model;

public class UnknownProduct {

    private int unknownProductId, customerId;
    private String productName, productCode, description;
    private String receivedDate;
    private String customerName;
    private String customerPhone;

    public UnknownProduct(int unknownProductId, int customerId, String productName, String productCode, String description, String receivedDate, String customerName, String customerPhone) {
        this.unknownProductId = unknownProductId;
        this.customerId = customerId;
        this.productName = productName;
        this.productCode = productCode;
        this.description = description;
        this.receivedDate = receivedDate;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }
    
    public UnknownProduct(int customerId, String productName, String productCode, String description, String receivedDate) {
        this.customerId = customerId;
        this.productName = productName;
        this.productCode = productCode;
        this.description = description;
        this.receivedDate = receivedDate;
    }

    public UnknownProduct() {
    }

    public UnknownProduct(int unknownProductId, int customerId, String productName, String productCode, String description, String receivedDate) {
        this.unknownProductId = unknownProductId;
        this.customerId = customerId;
        this.productName = productName;
        this.productCode = productCode;
        this.description = description;
        this.receivedDate = receivedDate;
    }
  
      public UnknownProduct(int unknownProductId, String customerName, String productName, String productCode, String description, String receivedDate, String customerPhone) {
        this.unknownProductId = unknownProductId;
        this.productName = productName;
        this.productCode = productCode;
        this.description = description;
        this.receivedDate = receivedDate;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }


    public int getUnknownProductId() {
        return unknownProductId;
    }

    public void setUnknownProductId(int unknowProductId) {
        this.unknownProductId = unknowProductId;
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

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }    

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}



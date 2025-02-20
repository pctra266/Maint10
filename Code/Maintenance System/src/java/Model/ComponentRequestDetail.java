/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Tra Pham
 */
public class ComponentRequestDetail {
    private int componentRequestDetailID, componentID, componentRequestID, quantity;

    public ComponentRequestDetail() {
    }

    public int getComponentRequestDetailID() {
        return componentRequestDetailID;
    }

    public void setComponentRequestDetailID(int componentRequestDetailID) {
        this.componentRequestDetailID = componentRequestDetailID;
    }

    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public int getComponentRequestID() {
        return componentRequestID;
    }

    public void setComponentRequestID(int componentRequestID) {
        this.componentRequestID = componentRequestID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ComponentRequestDetail{" + "componentRequestDetailID=" + componentRequestDetailID + ", componentID=" + componentID + ", componentRequestID=" + componentRequestID + ", quantity=" + quantity + '}';
    }
    
    
}

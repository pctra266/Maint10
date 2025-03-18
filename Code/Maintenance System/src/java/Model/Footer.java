/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Tra Pham
 */
public class Footer {
    private int footerId;
    private String slogan, address, hotline, email, copyrightYear;
    private Date lastUpdated;

    public Footer() {
    }

    public int getFooterId() {
        return footerId;
    }

    public void setFooterId(int footerId) {
        this.footerId = footerId;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Footer{" + "footerId=" + footerId + ", slogan=" + slogan + ", address=" + address + ", hotline=" + hotline + ", email=" + email + ", copyrightYear=" + copyrightYear + ", lastUpdated=" + lastUpdated + '}';
    }
            
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

public class MarketingServiceItem {
    private int serviceID;
    private int sectionID;
    private String title;
    private String description;
    private String imageURL;
    private int sortOrder;
    private Date createdDate;
    private Date updatedDate;

    public MarketingServiceItem() {
    }

    public MarketingServiceItem(int serviceID, int sectionID, String title, String description,
                                String imageURL, int sortOrder, Date createdDate, Date updatedDate) {
        this.serviceID = serviceID;
        this.sectionID = sectionID;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.sortOrder = sortOrder;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}


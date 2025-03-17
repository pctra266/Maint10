/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

public class MarketingServiceSection {
    private int sectionID;
    private String title;
    private String subTitle;
    private Date createdDate;
    private Date updatedDate;

    public MarketingServiceSection() {
    }

    public MarketingServiceSection(int sectionID, String title, String subTitle, Date createdDate, Date updatedDate) {
        this.sectionID = sectionID;
        this.title = title;
        this.subTitle = subTitle;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
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


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Blog {
    private int blogPostId;
    private String staff;
    private String staffID;
    private String title;
    private String content;
    private String createdDate;
    private String updatedDate;

    public Blog() {
    }

    public Blog(int blogPostId, String staff,String staffID, String title, String content, String createdDate, String updatedDate) {
        this.blogPostId = blogPostId;
        this.staff = staff;
        this.staffID = staffID;
        this.title = title;
        this.content = content;
        this.createdDate = formatDate(createdDate);
        this.updatedDate = formatDate(updatedDate);
    }

    private String formatDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "Null";
        }
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Định dạng gốc từ database
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng mong muốn
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (Exception e) {
            return "Invalid Date"; // Trả về lỗi nếu định dạng không đúng
        }
    }

    public int getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(int blogPostId) {
        this.blogPostId = blogPostId;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    
    
}

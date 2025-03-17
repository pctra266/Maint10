/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Blog;
import Model.ReportComponent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp2.SQLExceptionList;
/**
 *
 * @author ADMIN
 */
public class BlogDAO extends DBContext{
    public ArrayList<Blog> getAllBlogs() {
        ArrayList<Blog> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT BlogPostID,Staff.[Name] , Title, Content, CreatedDate, UpdatedDate \n" +
                    "FROM StaffBlogPosts\n" +
                    "JOIN Staff ON Staff.StaffID = StaffBlogPosts.StaffID";
        
        try{
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int blogPostID = rs.getInt("BlogPostID");
                String staff = rs.getString("Name");
                String title = rs.getString("Title");
                String content = rs.getString("Content");
                String createdDate = rs.getString("CreatedDate");
                String updatedDate = rs.getString("UpdatedDate");
                Blog blog = new Blog(blogPostID, staff, title, content, createdDate, updatedDate);
                list.add(blog);
                
            }
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return list;
    }
    public ArrayList<Blog> getAllBlogs(String searchname, String search, int pageIndex, int pageSize, String column, String sortOrder) {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "SELECT BlogPostID,Staff.[Name] , Title, Content, CreatedDate, UpdatedDate \n" +
                    "FROM StaffBlogPosts\n" +
                    "JOIN Staff ON Staff.StaffID = StaffBlogPosts.StaffID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (searchname != null && !searchname.trim().isEmpty()) {
            if (search.equals("Name")) {
                sql += " WHERE Name LIKE ?";
            }else{
                sql += " WHERE Title LIKE ?";
            }
        }
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by BlogPostID ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        }
        sql += " offset ? rows  fetch next ? rows only;";
        try {
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }
            int startIndex = (pageIndex - 1) * pageSize;
            stm.setInt(count++, startIndex);
            stm.setInt(count++, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                int blogPostID = rs.getInt("BlogPostID");
                String staff = rs.getString("Name");
                String title = rs.getString("Title");
                String content = rs.getString("Content");
                String createdDate = rs.getString("CreatedDate");
                String updatedDate = rs.getString("UpdatedDate");
                Blog blog = new Blog(blogPostID, staff, title, content, createdDate, updatedDate);
                list.add(blog);
            }
        } catch (SQLException e) {
            System.out.println(e);

        }

        return list;
    }
    public ArrayList<Blog> getAllBlogs(String searchname, String search, String column, String sortOrder) {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "SELECT BlogPostID,Staff.[Name] , Title, Content, CreatedDate, UpdatedDate \n" +
                    "FROM StaffBlogPosts\n" +
                    "JOIN Staff ON Staff.StaffID = StaffBlogPosts.StaffID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (searchname != null && !searchname.trim().isEmpty()) {
            if (search.equals("Name")) {
                sql += " WHERE Name LIKE ?";
            }else{
                sql += " WHERE Title LIKE ?";
            }
        }
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by BlogPostID ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        }

        try {
            stm = connection.prepareStatement(sql);
            int count = 1;
            if (searchname != null && !searchname.trim().isEmpty()) {
                stm.setString(count++, "%" + searchname.trim() + "%");
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                int blogPostID = rs.getInt("BlogPostID");
                String staff = rs.getString("Name");
                String title = rs.getString("Title");
                String content = rs.getString("Content");
                String createdDate = rs.getString("CreatedDate");
                String updatedDate = rs.getString("UpdatedDate");
                Blog blog = new Blog(blogPostID, staff, title, content, createdDate, updatedDate);
                list.add(blog);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }
    public boolean InsertBlog(String staffId, String title, String content) {
        ArrayList<Blog> list = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "INSERT INTO StaffBlogPosts (StaffID, Title, Content, CreatedDate, UpdatedDate) VALUES (?, ?, ?, GETDATE(), Null);";
        
        try{
            stm = connection.prepareStatement(sql);
            stm.setString(1, staffId);
            stm.setString(2, title);
            stm.setString(3, content);
            stm.executeUpdate();
            
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return true;
    }
    public ArrayList<Blog> getBlogByID(String blogID) {
    ArrayList<Blog> list = new ArrayList<>();
    PreparedStatement stm = null;
    ResultSet rs = null;
    
    String sql = "SELECT \n" +
                "    BlogPostID, \n" +
                "    Staff.[Name], \n" +
                "    Title, \n" +
                "    REPLACE(Content, CHAR(10), CHAR(10)) AS Content, \n" +
                "    CreatedDate, \n" +
                "    UpdatedDate \n" +
                "FROM StaffBlogPosts \n" +
                "JOIN Staff ON Staff.StaffID = StaffBlogPosts.StaffID \n" +
                "WHERE BlogPostID = ?;";
    
    try {
        stm = connection.prepareStatement(sql);
        stm.setString(1, blogID);
        rs = stm.executeQuery();

        if (rs.next()) {  
            int blogPostID = rs.getInt("BlogPostID");
            String staff = rs.getString("Name");
            String title = rs.getString("Title");
            String content = rs.getString("Content");
            String createdDate = rs.getString("CreatedDate");
            String updatedDate = rs.getString("UpdatedDate");
            Blog blog = new Blog(blogPostID, staff, title, content, createdDate, updatedDate);
            list.add(blog);
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
    return list;  
}

}

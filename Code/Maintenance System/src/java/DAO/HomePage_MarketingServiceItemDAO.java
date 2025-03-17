/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.MarketingServiceItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomePage_MarketingServiceItemDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // CREATE
    public boolean createItem(MarketingServiceItem item) {
        String query = """
            INSERT INTO MarketingServiceItem
            (SectionID, Title, Description, ImageURL, SortOrder, CreatedDate, UpdatedDate)
            VALUES (?, ?, ?, ?, ?, GETDATE(), GETDATE())
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, item.getSectionID());
            ps.setString(2, item.getTitle());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getImageURL());
            ps.setInt(5, item.getSortOrder());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ by ID
    public MarketingServiceItem getItemByID(int serviceID) {
        String query = """
            SELECT ServiceID, SectionID, Title, Description, ImageURL, SortOrder, CreatedDate, UpdatedDate
            FROM MarketingServiceItem
            WHERE ServiceID = ?
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, serviceID);
            rs = ps.executeQuery();
            if (rs.next()) {
                MarketingServiceItem item = new MarketingServiceItem();
                item.setServiceID(rs.getInt("ServiceID"));
                item.setSectionID(rs.getInt("SectionID"));
                item.setTitle(rs.getString("Title"));
                item.setDescription(rs.getString("Description"));
                item.setImageURL(rs.getString("ImageURL"));
                item.setSortOrder(rs.getInt("SortOrder"));
                item.setCreatedDate(rs.getTimestamp("CreatedDate"));
                item.setUpdatedDate(rs.getTimestamp("UpdatedDate"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ all items by SectionID
    public List<MarketingServiceItem> getItemsBySectionID(int sectionID) {
        List<MarketingServiceItem> list = new ArrayList<>();
        String query = """
            SELECT ServiceID, SectionID, Title, Description, ImageURL, SortOrder, CreatedDate, UpdatedDate
            FROM MarketingServiceItem
            WHERE SectionID = ?
            ORDER BY SortOrder ASC
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, sectionID);
            rs = ps.executeQuery();
            while (rs.next()) {
                MarketingServiceItem item = new MarketingServiceItem();
                item.setServiceID(rs.getInt("ServiceID"));
                item.setSectionID(rs.getInt("SectionID"));
                item.setTitle(rs.getString("Title"));
                item.setDescription(rs.getString("Description"));
                item.setImageURL(rs.getString("ImageURL"));
                item.setSortOrder(rs.getInt("SortOrder"));
                item.setCreatedDate(rs.getTimestamp("CreatedDate"));
                item.setUpdatedDate(rs.getTimestamp("UpdatedDate"));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean updateItem(MarketingServiceItem item) {
        String query = """
            UPDATE MarketingServiceItem
            SET SectionID = ?, Title = ?, Description = ?, ImageURL = ?, SortOrder = ?, UpdatedDate = GETDATE()
            WHERE ServiceID = ?
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, item.getSectionID());
            ps.setString(2, item.getTitle());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getImageURL());
            ps.setInt(5, item.getSortOrder());
            ps.setInt(6, item.getServiceID());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean deleteItem(int serviceID) {
        String query = "DELETE FROM MarketingServiceItem WHERE ServiceID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, serviceID);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


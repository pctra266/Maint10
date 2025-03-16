/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.MarketingServiceSection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomePage_MarketingServiceSectionDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // CREATE
    public boolean createSection(MarketingServiceSection section) {
        String query = """
            INSERT INTO MarketingServiceSection (Title, SubTitle, CreatedDate, UpdatedDate)
            VALUES (?, ?, GETDATE(), GETDATE())
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, section.getTitle());
            ps.setString(2, section.getSubTitle());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ by ID
    public MarketingServiceSection getSectionByID(int id) {
        String query = """
            SELECT SectionID, Title, SubTitle, CreatedDate, UpdatedDate
            FROM MarketingServiceSection
            WHERE SectionID = ?
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                MarketingServiceSection section = new MarketingServiceSection();
                section.setSectionID(rs.getInt("SectionID"));
                section.setTitle(rs.getString("Title"));
                section.setSubTitle(rs.getString("SubTitle"));
                section.setCreatedDate(rs.getTimestamp("CreatedDate"));
                section.setUpdatedDate(rs.getTimestamp("UpdatedDate"));
                return section;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ all
    public List<MarketingServiceSection> getAllSections() {
        List<MarketingServiceSection> list = new ArrayList<>();
        String query = """
            SELECT SectionID, Title, SubTitle, CreatedDate, UpdatedDate
            FROM MarketingServiceSection
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                MarketingServiceSection section = new MarketingServiceSection();
                section.setSectionID(rs.getInt("SectionID"));
                section.setTitle(rs.getString("Title"));
                section.setSubTitle(rs.getString("SubTitle"));
                section.setCreatedDate(rs.getTimestamp("CreatedDate"));
                section.setUpdatedDate(rs.getTimestamp("UpdatedDate"));
                list.add(section);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean updateSection(MarketingServiceSection section) {
        String query = """
            UPDATE MarketingServiceSection
            SET Title = ?, SubTitle = ?, UpdatedDate = GETDATE()
            WHERE SectionID = ?
            """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, section.getTitle());
            ps.setString(2, section.getSubTitle());
            ps.setInt(3, section.getSectionID());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean deleteSection(int id) {
        String query = "DELETE FROM MarketingServiceSection WHERE SectionID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


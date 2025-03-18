/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author PC
 */
public class ReportDAO extends DBContext {

    // Report waaranty by Status
    public List<Map<String, Object>> reportWarrantyCardByStatus() {
        List<Map<String, Object>> reportWarranty = new ArrayList<>();
        String sql = "SELECT WarrantyStatus, COUNT(*) AS TotalWarrantyCards\n"
                + "FROM WarrantyCard\n"
                + "GROUP BY WarrantyStatus\n"
                + "ORDER BY TotalWarrantyCards DESC;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("status", rs.getString("WarrantyStatus"));
                data.put("total", rs.getInt("TotalWarrantyCards"));
                reportWarranty.add(data);
            }
        } catch (Exception e) {

        }
        return reportWarranty;
    }

    public List<Map<String, Object>> reportRepairCardByStatus() {
        List<Map<String, Object>> reportWarranty = new ArrayList<>();
        String sql = "SELECT \n"
                + "    WC.WarrantyStatus,\n"
                + "    COUNT(WC.WarrantyCardID) AS TotalWarrantyCards\n"
                + "FROM WarrantyCard WC\n"
                + "JOIN WarrantyProduct WP ON WC.WarrantyProductID = WP.WarrantyProductID\n"
                + "JOIN UnknownProduct UP ON WP.UnknownProductID = UP.CustomerID\n"
                + "GROUP BY WC.WarrantyStatus\n"
                + "ORDER BY TotalWarrantyCards DESC;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("status", rs.getString("WarrantyStatus"));
                data.put("total", rs.getInt("TotalWarrantyCards"));
                reportWarranty.add(data);
            }
        } catch (SQLException e) {

        }
        return reportWarranty;
    }

    /**
     * Report warranty card by type name
     *
     * @return
     */
    public List<Map<String, Object>> reportWarrantyCardByTypeName() {
        List<Map<String, Object>> reportWarranty = new ArrayList<>();
        String sql = "SELECT \n"
                + "     \n"
                + "    COALESCE(pt.TypeName, 'Unknown') AS TypeName, \n"
                + "    COUNT(wc.WarrantyCardID) AS TotalWarrantyCards\n"
                + "FROM WarrantyCard wc\n"
                + "LEFT JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID\n"
                + "LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID\n"
                + "LEFT JOIN Product p ON p.ProductID = pd.ProductID\n"
                + "LEFT JOIN ProductType pt ON pt.ProductTypeID = p.ProductTypeID\n"
                + "GROUP BY pt.ProductTypeID, pt.TypeName\n"
                + "ORDER BY TotalWarrantyCards DESC;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("typeName", rs.getString("TypeName"));
                data.put("total", rs.getInt("TotalWarrantyCards"));
                reportWarranty.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return reportWarranty;
    }

    public List<Map<String, Object>> reportWarrantyCardByBrand() {
        List<Map<String, Object>> reportWarranty = new ArrayList<>();
        String sql = "SELECT \n"
                + "COALESCE(b.BrandName, 'Unknown') AS BrandName, \n"
                + "    COUNT(wc.WarrantyCardID) AS TotalWarrantyCards\n"
                + "FROM WarrantyCard wc\n"
                + "LEFT JOIN WarrantyProduct wp ON wc.WarrantyProductID = wp.WarrantyProductID\n"
                + "LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID\n"
                + "LEFT JOIN Product p ON p.ProductID = pd.ProductID\n"
                + "LEFT JOIN Brand b ON b.BrandID = p.BrandID\n"
                + "GROUP BY b.BrandID,b.BrandName\n"
                + "ORDER BY TotalWarrantyCards DESC;";
             
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("brandName", rs.getString("BrandName"));
                data.put("total", rs.getInt("TotalWarrantyCards"));
                reportWarranty.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return reportWarranty;
    }

    public static void main(String[] args) {
        ReportDAO reportDao = new ReportDAO();
        List<Map<String, Object>> reportWarranty = reportDao.reportWarrantyCardByBrand();
        for (Map<String, Object> entry : reportWarranty) {
            for (Map.Entry<String, Object> data : entry.entrySet()) {
                System.out.println(data.getKey() + " : " + data.getValue());
            }
            System.out.println("---------------------");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ProductDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import Model.WarrantyCardDetail;
import DAO.ComponentDAO;
import jakarta.servlet.jsp.jstl.sql.Result;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class WarrantyCardDetailDAO extends DBContext {

    final static ComponentDAO cdao = new ComponentDAO();
    static final String SELEC_STRING = "select wcd.ComponentID, wcd.Price, wcd.Quantity, wcd.Status, wcd.WarrantyCardDetailID, wcd.WarrantyCardID, wcd.ComponentName, wcd.Note ";

    public List<WarrantyCardDetail> getWarrantyCardDetailOfCard(int wcID) {
        List<WarrantyCardDetail> list = new ArrayList<>();
        String sql = SELEC_STRING + """
                      from WarrantyCard wc
                      join WarrantyCardDetail wcd on wc.WarrantyCardID=wcd.WarrantyCardID
                      where wc.WarrantyCardID=?""";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, wcID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapWarrantyCardDetail(rs));
            }
        } catch (SQLException e) {

        }
        return list;
    }

    public WarrantyCardDetail getWarrantyCardDetailById(Integer warrantyCardDetailId) {
        String sql = SELEC_STRING + """
                       from WarrantyCardDetail wcd
                      where WarrantyCardDetailID=?""";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, warrantyCardDetailId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return mapWarrantyCardDetail(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Thêm ProductDetail mới

    public boolean addWarrantyCardDetailDAO(WarrantyCardDetail wcd) {
        String sql = "INSERT INTO WarrantyCardDetail ([WarrantyCardID], [ComponentID], [Status], Quantity, Price, ComponentName, Note) VALUES (?,?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, wcd.getWarrantyCardID());
            if (wcd.getComponent() != null) {
                statement.setInt(2, wcd.getComponent().getComponentID());
                statement.setString(6, wcd.getComponent().getComponentName());
            } else {
                statement.setNull(2, java.sql.Types.INTEGER);
                statement.setString(6, wcd.getComponentName());
            }
            statement.setString(3, wcd.getStatus());
            statement.setInt(4, wcd.getQuantity());
            statement.setDouble(5, wcd.getPrice());
            statement.setString(6, wcd.getComponentName());
            statement.setString(7, wcd.getNote());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
        }
        return false;
    }

    // Cập nhật ProductDetail
    public boolean updateWarrantyCardDetail(WarrantyCardDetail wcd) {
        String sql = "UPDATE WarrantyCardDetail SET ComponentID = ?, [Status] =?, Quantity = ?, Price = ?, ComponentName = ?, Note = ? WHERE WarrantyCardDetailID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (wcd.getComponent() != null) {
                statement.setInt(1, wcd.getComponent().getComponentID());
                statement.setString(5, wcd.getComponent().getComponentName());
            } else {
                statement.setNull(1, java.sql.Types.INTEGER);
                statement.setString(5, wcd.getComponentName());
            }
            statement.setString(2, wcd.getStatus());
            statement.setInt(3, wcd.getQuantity());
            statement.setDouble(4, wcd.getPrice());
            statement.setString(6, wcd.getNote());
            statement.setInt(7, wcd.getWarrantyCardDetailID());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa ProductDetail theo ID
    public boolean deleteWarrantyCardDetail(int wcdID) {
        String sql = "DELETE FROM WarrantyCardDetail WHERE WarrantyCardDetailID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, wcdID);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private WarrantyCardDetail mapWarrantyCardDetail(ResultSet rs) throws SQLException {
        WarrantyCardDetail wcd = new WarrantyCardDetail();
        wcd.setWarrantyCardDetailID(rs.getInt("WarrantyCardDetailID"));
        wcd.setWarrantyCardID(rs.getInt("WarrantyCardID"));
        wcd.setComponent(cdao.getComponentByID(rs.getInt("ComponentID")));
        wcd.setStatus(rs.getString("Status"));
        wcd.setQuantity(rs.getInt("Quantity"));
        wcd.setPrice(rs.getDouble("Price"));
        wcd.setComponentName(rs.getString("ComponentName"));
        wcd.setNote(rs.getString("Note"));
        return wcd;
    }

    public static void main(String[] args) {
        WarrantyCardDetailDAO wcdd = new WarrantyCardDetailDAO();
        WarrantyCardDetail wcd = new WarrantyCardDetail();
        ComponentDAO cdao = new ComponentDAO();
        wcd.setWarrantyCardDetailID(2);
        wcd.setWarrantyCardID(1);
        wcd.setStatus("replace");
        wcd.setQuantity(1);
        wcd.setPrice(1.5);
        wcd.setComponentName("kjkjk");
        wcd.setNote("asfihiosdfjio");
        // System.out.println(wcdd.updateWarrantyCardDetail(wcd));
        System.out.println(wcdd.getWarrantyCardDetailById(1).getNote());
        System.out.println(wcdd.getWarrantyCardDetailOfCard(71));
    }

}

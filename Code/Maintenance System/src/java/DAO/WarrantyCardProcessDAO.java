package DAO;

import Model.WarrantyCardProcess;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WarrantyCardProcessDAO extends DBContext {

    public WarrantyCardProcess getLatestProcessByWarrantyCardId(int warrantyCardId) {
        String sql = "SELECT TOP 1 * FROM WarrantyCardProcess WHERE WarrantyCardID = ? ORDER BY ActionDate DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, warrantyCardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                WarrantyCardProcess process = new WarrantyCardProcess();
                process.setWarrantyCardProcessID(rs.getInt("WarrantyCardProcessID"));
                process.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                process.setHandlerID(rs.getInt("HandlerID"));
                process.setAction(rs.getString("Action"));
                process.setActionDate(rs.getTimestamp("ActionDate"));
                process.setNote(rs.getString("Note"));
                return process;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addWarrantyCardProcess(WarrantyCardProcess process) {
        String sql = "INSERT INTO WarrantyCardProcess (WarrantyCardID, HandlerID, [Action], Note) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, process.getWarrantyCardID());
            ps.setInt(2, process.getHandlerID());
            ps.setString(3, process.getAction());
            ps.setString(4, process.getNote());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        WarrantyCardProcessDAO d = new WarrantyCardProcessDAO();
        System.out.println(d.getLatestProcessByWarrantyCardId(95));
    }
}

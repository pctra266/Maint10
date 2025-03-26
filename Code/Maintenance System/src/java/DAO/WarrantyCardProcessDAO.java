package DAO;

import Model.WarrantyCardProcess;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    public boolean deleteAllProcessOfCard(int id){
        String sql = "delete from [WarrantyCardProcess] where WarrantyCardID = ? ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<WarrantyCardProcess> getAllProcessesOfCard(int warrantyCardId) {
        String sql = "SELECT * FROM WarrantyCardProcess WHERE WarrantyCardID = ?";
        List<WarrantyCardProcess> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, warrantyCardId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WarrantyCardProcess process = new WarrantyCardProcess();
                process.setWarrantyCardProcessID(rs.getInt("WarrantyCardProcessID"));
                process.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                process.setHandlerID(rs.getInt("HandlerID"));
                process.setAction(rs.getString("Action"));
                process.setActionDate(rs.getTimestamp("ActionDate"));
                process.setNote(rs.getString("Note"));
                list.add(process);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<WarrantyCardProcess> getAllProcessesOfCard(int pageIndex, int pageSize, String column, String sortOrder) {
        String sql = "SELECT * FROM WarrantyCardProcess";       
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by WarrantyCardID ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        }
        sql += " offset ? rows  fetch next ? rows only;";
        
        List<WarrantyCardProcess> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int count = 1;
            int startIndex = (pageIndex - 1) * pageSize;
            ps.setInt(count++, startIndex);
            ps.setInt(count++, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WarrantyCardProcess process = new WarrantyCardProcess();
                process.setWarrantyCardProcessID(rs.getInt("WarrantyCardProcessID"));
                process.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                process.setHandlerID(rs.getInt("HandlerID"));
                process.setAction(rs.getString("Action"));
                process.setActionDate(rs.getTimestamp("ActionDate"));
                process.setNote(rs.getString("Note"));
                list.add(process);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<WarrantyCardProcess> getAllProcessesOfCard(String column, String sortOrder) {
        String sql = "SELECT * FROM WarrantyCardProcess";       
        if (column != null && !column.trim().isEmpty()) {
            sql += " order by " + column + " ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        } else {
            sql += " order by WarrantyCardID ";
            if (sortOrder != null && !sortOrder.trim().isEmpty()) {
                sql += sortOrder;
            }
        }
        
        List<WarrantyCardProcess> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WarrantyCardProcess process = new WarrantyCardProcess();
                process.setWarrantyCardProcessID(rs.getInt("WarrantyCardProcessID"));
                process.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                process.setHandlerID(rs.getInt("HandlerID"));
                process.setAction(rs.getString("Action"));
                process.setActionDate(rs.getTimestamp("ActionDate"));
                process.setNote(rs.getString("Note"));
                list.add(process);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Integer> getAllWarrantyCardID() {
    String sql = "SELECT DISTINCT [WarrantyCardID] FROM WarrantyCardProcess";           
    List<Integer> list = new ArrayList<>(); // Chỉ lưu danh sách WarrantyCardID

    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) { 

        while (rs.next()) {
            list.add(rs.getInt("WarrantyCardID"));
        }
        return list; // Trả về danh sách ID duy nhất
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    
    
    public static void main(String[] args) {
        WarrantyCardProcessDAO d = new WarrantyCardProcessDAO();
        System.out.println(d.getAllProcessesOfCard(41));
    }
}

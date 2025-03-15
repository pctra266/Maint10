package DAO;

import Model.SupplementRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplementRequestDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public boolean addSupplementRequest(SupplementRequest request) {
        String query = """
            INSERT INTO MissingComponentRequest (ComponentName, ComponentType, TypeID, BrandID, RequestedBy, Status, Note)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, request.getComponentName());
            ps.setString(2, request.getComponentType());
            ps.setObject(3, request.getTypeID(), Types.INTEGER);
            ps.setObject(4, request.getBrandID(), Types.INTEGER);
            ps.setInt(5, request.getRequestedBy());
            ps.setString(6, "waiting");
            ps.setString(7, request.getNote());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SupplementRequest> getAllSupplementRequests() {
        List<SupplementRequest> list = new ArrayList<>();
        String query = "SELECT * FROM MissingComponentRequest ORDER BY RequestDate DESC";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new SupplementRequest(
                        rs.getInt("MissingComponentRequestID"),
                        rs.getString("ComponentName"),
                        rs.getString("ComponentType"),
                        (Integer) rs.getObject("TypeID"),
                        (Integer) rs.getObject("BrandID"),
                        rs.getInt("RequestedBy"),
                        rs.getTimestamp("RequestDate"),
                        rs.getString("Status"),
                        rs.getString("Note")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public SupplementRequest getSupplementRequestByID(int requestID) {
        String query = "SELECT * FROM MissingComponentRequest WHERE MissingComponentRequestID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, requestID);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new SupplementRequest(
                        rs.getInt("MissingComponentRequestID"),
                        rs.getString("ComponentName"),
                        rs.getString("ComponentType"),
                        (Integer) rs.getObject("TypeID"),
                        (Integer) rs.getObject("BrandID"),
                        rs.getInt("RequestedBy"),
                        rs.getTimestamp("RequestDate"),
                        rs.getString("Status"),
                        rs.getString("Note")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        SupplementRequestDAO dao  = new SupplementRequestDAO();
//        SupplementRequest supplementRequest = new SupplementRequest();
//        supplementRequest.setComponentName("pham cong tra");
//        supplementRequest.setComponentType("product");
//        supplementRequest.setTypeID(1);
//        supplementRequest.setBrandID(2);
//        supplementRequest.setRequestedBy(3);
//        supplementRequest.setRequestDate(new java.util.Date());
//        supplementRequest.setStatus("waiting");
//        supplementRequest.setNote("trq");
//        
//        dao.addSupplementRequest(supplementRequest);
        
        System.out.println(dao.getAllSupplementRequests());
    }
}

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
            if (request.getTypeID() == 0) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, request.getTypeID());
            }

            if (request.getBrandID() == 0) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, request.getBrandID());
            }
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
        String query = """
                    SELECT mcq.MissingComponentRequestID,mcq.ComponentName,mcq.ComponentType,mcq.TypeID,mcq.BrandID,mcq.RequestedBy,mcq.RequestDate,mcq.Status,mcq.Note,cb.BrandName,ct.TypeName 
                    FROM MissingComponentRequest mcq
                    left JOIN Brand cb ON mcq.BrandID = cb.BrandID
                    left JOIN ComponentType ct ON mcq.TypeID = ct.TypeID  ORDER BY RequestDate DESC
                       """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                 SupplementRequest request = new SupplementRequest(); 

                request.setRequestID(rs.getInt("MissingComponentRequestID"));
                request.setComponentName(rs.getString("ComponentName"));
                request.setComponentType(rs.getString("ComponentType"));
                request.setTypeID((Integer) rs.getObject("TypeID"));
                request.setBrandID((Integer) rs.getObject("BrandID"));
                request.setType(rs.getString("TypeName"));
                request.setBrand(rs.getString("BrandName"));
                request.setRequestedBy(rs.getInt("RequestedBy"));
                request.setRequestDate(rs.getTimestamp("RequestDate"));
                request.setStatus(rs.getString("Status"));
                request.setNote(rs.getString("Note"));

                list.add(request); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
        public ArrayList<SupplementRequest> getMissingComponentRequests(String status, String typeID, String brandID, String componentName, int page, int pageSize) {
        ArrayList<SupplementRequest> list = new ArrayList<>();
        String query = "SELECT mcq.MissingComponentRequestID, mcq.ComponentName, mcq.ComponentType, mcq.TypeID, mcq.BrandID, mcq.RequestedBy, mcq.RequestDate, mcq.Status, mcq.Note, cb.BrandName, ct.TypeName " +
                       "FROM MissingComponentRequest mcq " +
                       "LEFT JOIN Brand cb ON mcq.BrandID = cb.BrandID " +
                       "LEFT JOIN ComponentType ct ON mcq.TypeID = ct.TypeID " +
                       "WHERE 1=1 ";
        if(status != null && !status.trim().isEmpty()){
            query += " AND mcq.Status LIKE ? ";
        }
        if(typeID != null && !typeID.trim().isEmpty()){
            query += " AND mcq.TypeID = ? ";
        }
        if(brandID != null && !brandID.trim().isEmpty()){
            query += " AND mcq.BrandID = ? ";
        }
        if(componentName != null && !componentName.trim().isEmpty()){
            query += " AND mcq.ComponentName LIKE ? ";
        }
        query += " ORDER BY mcq.RequestDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if(status != null && !status.trim().isEmpty()){
                ps.setString(count++, "%" + status.trim() + "%");
            }
            if(typeID != null && !typeID.trim().isEmpty()){
                ps.setInt(count++, Integer.parseInt(typeID.trim()));
            }
            if(brandID != null && !brandID.trim().isEmpty()){
                ps.setInt(count++, Integer.parseInt(brandID.trim()));
            }
            if(componentName != null && !componentName.trim().isEmpty()){
                ps.setString(count++, "%" + componentName.trim() + "%");
            }
            int offset = (page - 1) * pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
            rs = ps.executeQuery();
            while(rs.next()){
                SupplementRequest mcr = new SupplementRequest();
                mcr.setRequestID(rs.getInt("MissingComponentRequestID"));
                mcr.setComponentName(rs.getString("ComponentName"));
                mcr.setComponentType(rs.getString("ComponentType"));
                mcr.setTypeID(rs.getInt("TypeID"));
                mcr.setBrandID(rs.getInt("BrandID"));
                mcr.setRequestedBy(rs.getInt("RequestedBy"));
                mcr.setRequestDate(rs.getDate("RequestDate"));
                mcr.setStatus(rs.getString("Status"));
                mcr.setNote(rs.getString("Note"));
                mcr.setBrand(rs.getString("BrandName"));
                mcr.setType(rs.getString("TypeName"));
                list.add(mcr);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
        
         public int totalMissingComponentRequests(String status, String typeID, String brandID, String componentName) {
        String query = "SELECT COUNT(*) FROM MissingComponentRequest WHERE 1=1 ";
        if(status != null && !status.trim().isEmpty()){
            query += " AND Status LIKE ? ";
        }
        if(typeID != null && !typeID.trim().isEmpty()){
            query += " AND TypeID = ? ";
        }
        if(brandID != null && !brandID.trim().isEmpty()){
            query += " AND BrandID = ? ";
        }
        if(componentName != null && !componentName.trim().isEmpty()){
            query += " AND ComponentName LIKE ? ";
        }
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if(status != null && !status.trim().isEmpty()){
                ps.setString(count++, "%" + status.trim() + "%");
            }
            if(typeID != null && !typeID.trim().isEmpty()){
                ps.setInt(count++, Integer.parseInt(typeID.trim()));
            }
            if(brandID != null && !brandID.trim().isEmpty()){
                ps.setInt(count++, Integer.parseInt(brandID.trim()));
            }
            if(componentName != null && !componentName.trim().isEmpty()){
                ps.setString(count++, "%" + componentName.trim() + "%");
            }
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
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
    public boolean updateStatus(int requestID, String newStatus) {
    String query = "UPDATE MissingComponentRequest SET Status = ? WHERE MissingComponentRequestID = ?";
    
    try {
        conn = new DBContext().connection;
        ps = conn.prepareStatement(query);
        ps.setString(1, newStatus);
        ps.setInt(2, requestID);
        
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
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

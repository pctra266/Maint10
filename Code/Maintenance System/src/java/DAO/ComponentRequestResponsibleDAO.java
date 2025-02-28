/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.ComponentRequestResponsible;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tra Pham
 */
public class ComponentRequestResponsibleDAO {
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
 public ArrayList<ComponentRequestResponsible> getAllComponentRequestResponsible(String componentRequestId, String staffName, 
         String staffPhone, String staffEmail, String componentRequestAction, int page, int pageSize){
        ArrayList<ComponentRequestResponsible> list = new ArrayList<>();
        String query = """
                       select crr.ComponentRequestResponsibleID, crr.ComponentRequestID,s.StaffID,s.Name, s.Phone, s.Email,r.RoleName, crr.Action, crr.CreateDate
                                              from ComponentRequestResponsible crr
                                              join Staff s on crr.StaffID = s.StaffID
                                              join Role r on s.RoleID = r.RoleID
                                              where 1=1""";
        if(componentRequestId != null && !componentRequestId.trim().isEmpty()){
            query += " and crr.ComponentRequestID like ?";
        }
        if(staffName != null && !staffName.trim().isEmpty()){
            query += " and s.Name like ?";
        }
        if(staffPhone != null && !staffPhone.trim().isEmpty()){
            query += " and s.Phone like ?";
        }
        if(staffEmail != null && !staffEmail.trim().isEmpty()){
            query += " and s.Email like ?";
        }
        if(componentRequestAction != null && !componentRequestAction.trim().isEmpty()){
            query += " and crr.Action like ?";
        }
        query += " order by crr.CreateDate desc";
        query += " offset ? rows  fetch next ? rows only";
        try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            
            if(componentRequestId != null && !componentRequestId.trim().isEmpty()){
            ps.setString(count++, "%"+ componentRequestId+ "%");
            }
            if(staffName != null && !staffName.trim().isEmpty()){
            ps.setString(count++, "%"+ staffName+ "%");
            }
            if(staffPhone != null && !staffPhone.trim().isEmpty()){
            ps.setString(count++, "%"+ staffPhone+ "%");
            }
            if(staffEmail != null && !staffEmail.trim().isEmpty()){
            ps.setString(count++, "%"+ staffEmail+ "%");
            }
            if(componentRequestAction != null && !componentRequestAction.trim().isEmpty()){
            ps.setString(count++, "%"+ componentRequestAction+ "%");
            }
            int offset = (page-1)*pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
            rs = ps.executeQuery();
            while(rs.next()){
                ComponentRequestResponsible componentRequestResponsible = new ComponentRequestResponsible();
                componentRequestResponsible.setAction(rs.getString("Action"));
                componentRequestResponsible.setComponentRequestID(rs.getInt("ComponentRequestID"));
                componentRequestResponsible.setComponentRequestResponsibleID(rs.getInt("ComponentRequestResponsibleID"));
                componentRequestResponsible.setCreateDate(rs.getDate("CreateDate"));
                componentRequestResponsible.setRoleName(rs.getString("RoleName"));
                componentRequestResponsible.setStaffEmail(rs.getString("Email"));
                componentRequestResponsible.setStaffID(rs.getInt("StaffID"));
                componentRequestResponsible.setStaffName(rs.getString("Name"));
                componentRequestResponsible.setStaffPhone(rs.getString("Phone"));
                
                list.add(componentRequestResponsible);
            }
        }catch (Exception e){
            
        }
        
        return list;
    }
    
 public int totalComponentRequestLog(String componentRequestId, String staffName, 
         String staffPhone, String staffEmail, String componentRequestAction){
     String query = """
                    select count(*)
                    from  ComponentRequestResponsible crr
                                           join Staff s on crr.StaffID = s.StaffID
                                           join Role r on s.RoleID = r.RoleID
                                           where 1=1
                    """;
     if(componentRequestId != null && !componentRequestId.trim().isEmpty()){
            query += " and crr.ComponentRequestID like ?";
        }
        if(staffName != null && !staffName.trim().isEmpty()){
            query += " and s.Name like ?";
        }
        if(staffPhone != null && !staffPhone.trim().isEmpty()){
            query += " and s.Phone like ?";
        }
        if(staffEmail != null && !staffEmail.trim().isEmpty()){
            query += " and s.Email like ?";
        }
        if(componentRequestAction != null && !componentRequestAction.trim().isEmpty()){
            query += " and crr.Action like ?";
        }
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            
            if(componentRequestId != null && !componentRequestId.trim().isEmpty()){
            ps.setString(count++, "%"+ componentRequestId+ "%");
            }
            if(staffName != null && !staffName.trim().isEmpty()){
            ps.setString(count++, "%"+ staffName+ "%");
            }
            if(staffPhone != null && !staffPhone.trim().isEmpty()){
            ps.setString(count++, "%"+ staffPhone+ "%");
            }
            if(staffEmail != null && !staffEmail.trim().isEmpty()){
            ps.setString(count++, "%"+ staffEmail+ "%");
            }
            if(componentRequestAction != null && !componentRequestAction.trim().isEmpty()){
            ps.setString(count++, "%"+ componentRequestAction+ "%");
            }
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
     } catch (Exception e) {
     }
     return 0;
 }
 
 
    public void createComponentRequestResponsible(String staffId, String componentRequestId, String action){
        String query ="""
                      insert into ComponentRequestResponsible(StaffID,ComponentRequestID,Action,CreateDate)
                      \tvalues (?,?,?,GETDATE())""";
         try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, staffId);
            ps.setString(2, componentRequestId);
            ps.setString(3, action);
            rs = ps.executeQuery();
            while(rs.next()){
                
            }
        }catch (Exception e){
            
        }
    }
 
    public static void main(String[] args) {
        ComponentRequestResponsibleDAO dao = new ComponentRequestResponsibleDAO();
//        ArrayList<ComponentRequestResponsible> list = dao.getAllComponentRequestResponsible("","","","","",1,5);
//        for (ComponentRequestResponsible x : list) {
//            System.out.println(x);
//        }
        System.out.println(dao.totalComponentRequestLog("", "", "", "", ""));
    }
}

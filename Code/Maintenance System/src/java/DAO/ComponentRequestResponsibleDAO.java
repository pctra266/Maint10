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
    
 public ArrayList<ComponentRequestResponsible> getAllComponentRequestResponsible(){
        ArrayList<ComponentRequestResponsible> list = new ArrayList<>();
        String query = """
                       select crr.ComponentRequestResponsibleID, crr.ComponentRequestID,s.StaffID,s.Name, s.Phone, s.Email,r.RoleName, crr.Action, crr.CreateDate
                       \tfrom ComponentRequestResponsible crr
                       \tjoin Staff s on crr.StaffID = s.StaffID\t\t
                       \tjoin Role r on s.RoleID = r.RoleID
                       \twhere 1=1""";
        try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
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
        ArrayList<ComponentRequestResponsible> list = dao.getAllComponentRequestResponsible();
        for (ComponentRequestResponsible x : list) {
            System.out.println(x);
        }
    }
}

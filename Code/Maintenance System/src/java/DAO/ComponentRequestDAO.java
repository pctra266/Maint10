/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.ProductDetail;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tra Pham
 */
public class ComponentRequestDAO {
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
// public ArrayList<OBJECT> getAll***(){
//        ArrayList<OBJECT> list = new ArrayList<>();
//        String query = "YOUR QUERY";
//        try{
//            conn = new DBContext().connection;
//            ps = conn.prepareStatement(query);
//            rs = ps.executeQuery();
//            while(rs.next()){
//                list.add(new OBJECT);
//            }
//        }catch (Exception e){
//            
//        }
//        
//        return list;
//    }
    public ArrayList<ProductDetail> getAllListProductUnderMaintain(String warrantyCardCode, String productCode,
          String unknownProductCode,  String warrantyStatus,String typeMaintain, String sort, String order, int page, int pageSize){
        ArrayList<ProductDetail> list = new ArrayList<>();
        String query = """
                       select wc.WarrantyCardID, wc.WarrantyCardCode,wc.CreatedDate ,p.Code as ProductCode, up.ProductCode as UnknownProductCode,p.ProductName, up.ProductName as UnknownProductName 
                       , wc.IssueDescription, wc.WarrantyStatus
                       \tfrom WarrantyCard wc
                       \tleft join WarrantyProduct wp on wc.WarrantyProductID = wp.WarrantyProductID
                       \tleft join ProductDetail pd on wp.ProductDetailID = pd.ProductDetailID
                       \tleft join UnknowProduct up on wp.UnknowProductID = up.UnknowProductID
                       \tleft join Product p on pd.ProductID = p.ProductID
                       \twhere 1 =1 """;
        if(warrantyCardCode!= null && !warrantyCardCode.trim().isEmpty()){
            query += "	and WarrantyCardCode like ?";
        }
        if(productCode!= null && !productCode.trim().isEmpty()){
            query += "	and ( up.ProductCode like ? or p.Code like ?)";
        }
        if(warrantyStatus!= null && !warrantyStatus.trim().isEmpty()){
            query += "	and WarrantyStatus like ?";
        }
        if (typeMaintain != null && !typeMaintain.trim().isEmpty()) {
            if (typeMaintain.equalsIgnoreCase("maintain")) {
                query += " and p.ProductName is not null";
            } else { query += " and p.ProductName is null"; }
        }
        if (sort != null && !sort.trim().isEmpty()) {
            query += " order by " + sort + " ";
            if (order != null && !order.trim().isEmpty()) {
                query += order;
            }
        } else {
            query += " order by CreatedDate desc";
        }
                 query += " offset ? rows  fetch next ? rows only;";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if(warrantyCardCode!= null && !warrantyCardCode.trim().isEmpty()){
                ps.setString(count++, "%"+warrantyCardCode+"%");
            }
            if(productCode!= null && !productCode.trim().isEmpty()){
                ps.setString(count++, "%"+productCode+"%");
                ps.setString(count++, "%"+productCode+"%");
            }
            if(warrantyStatus!= null && !warrantyStatus.trim().isEmpty()){
                ps.setString(count++, warrantyStatus);
            }
            int offset = (page-1)*pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
            System.out.println(query);
            rs = ps.executeQuery();
            while(rs.next()){
                ProductDetail productDetail = new ProductDetail();
                productDetail.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                productDetail.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                productDetail.setCreatedDate(rs.getDate("CreatedDate"));
                productDetail.setProductCode(rs.getString("ProductCode"));
                productDetail.setUnknownProductCode(rs.getString("UnknownProductCode"));
                productDetail.setProductName(rs.getString("ProductName"));
                productDetail.setUnknownProductName(rs.getString("UnknownProductName"));
                productDetail.setIssueDescription(rs.getString("IssueDescription"));
                productDetail.setWarrantyStatus(rs.getString("WarrantyStatus"));
                list.add(productDetail);
            }
        } catch (Exception e) {
        }
        
        return list;
    }
    
    public int totalProductUnderMaintain(String warrantyCardCode, String productCode,
          String unknownProductCode,  String warrantyStatus,String typeMaintain){
        int total =0;
        String query = """
                       select count(*)
                       \tfrom WarrantyCard wc
                       \tleft join WarrantyProduct wp on wc.WarrantyProductID = wp.WarrantyProductID
                       \tleft join ProductDetail pd on wp.ProductDetailID = pd.ProductDetailID
                       \tleft join UnknowProduct up on wp.UnknowProductID = up.UnknowProductID
                       \tleft join Product p on pd.ProductID = p.ProductID
                       \twhere 1 =1 """;
        if(warrantyCardCode!= null && !warrantyCardCode.trim().isEmpty()){
            query += "	and WarrantyCardCode like ?";
        }
        if(productCode!= null && !productCode.trim().isEmpty()){
            query += "	and ( up.ProductCode like ? or p.Code like ?)";
        }
        if(warrantyStatus!= null && !warrantyStatus.trim().isEmpty()){
            query += "	and WarrantyStatus like ?";
        }
        if (typeMaintain != null && !typeMaintain.trim().isEmpty()) {
            if (typeMaintain.equalsIgnoreCase("maintain")) {
                query += " and p.ProductName is not null";
            } else { query += " and p.ProductName is null"; }
        }
        try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if(warrantyCardCode!= null && !warrantyCardCode.trim().isEmpty()){
                ps.setString(count++, "%"+warrantyCardCode+"%");
            }
            if(productCode!= null && !productCode.trim().isEmpty()){
                ps.setString(count++, "%"+productCode+"%");
                ps.setString(count++, "%"+productCode+"%");
            }
            if(warrantyStatus!= null && !warrantyStatus.trim().isEmpty()){
                ps.setString(count++, "%"+warrantyStatus+"%");
            }
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
        }catch(Exception e){
            
        }
        return total;
    }
    public static void main(String[] args) {
        ArrayList<ProductDetail> list = new ArrayList<>();
        ComponentRequestDAO dao = new ComponentRequestDAO();
        list = dao.getAllListProductUnderMaintain("", "", "", "fixing", "", "", "", 1, 5);
        for (ProductDetail productDetail : list) {
            System.out.println(productDetail);
        }
//        System.out.println(dao.totalProductUnderMaintain("", "", "", "fixing", ""));
    }
}

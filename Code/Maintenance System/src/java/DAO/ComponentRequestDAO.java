/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Brand;
import Model.Component;
import Model.ComponentRequest;
import Model.ComponentRequestDetail;
import Model.ComponentType;
import Model.ProductDetail;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Tra Pham
 */
public class ComponentRequestDAO {
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public ArrayList<ComponentRequest> getAllComponentRequest(String warrantyCardCode,int page, int pageSize){
           ArrayList<ComponentRequest> list = new ArrayList<>();
           String query = """
                          select cr.ComponentRequestID,wc.WarrantyCardID ,wc.WarrantyCardCode, wc.CreatedDate as WCardCreateDate,cr.Date as CRequestCreateDate, cr.Status, cr.Note 
                          	from ComponentRequest cr 
                          	join WarrantyCard wc on cr.WarrantyCardID = wc.WarrantyCardID
                          	where 1=1""";
           if(warrantyCardCode!= null && !warrantyCardCode.trim().isEmpty()){
            query += "	and wc.WarrantyCardCode like ?";
            }
           query +=" order by cr.Date desc";
           query +=" offset ? rows fetch next ? rows only;";
           try{
               conn = new DBContext().connection;
               ps = conn.prepareStatement(query);
               int count =1;
               if(warrantyCardCode!= null && !warrantyCardCode.trim().isEmpty()){
                   ps.setString(count++, "%"+ warrantyCardCode+"%");
                }
               int offset = (page-1)*pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
               rs = ps.executeQuery();
               while(rs.next()){
                   ComponentRequest componentRequest = new ComponentRequest();
                   componentRequest.setComponentRequestID(rs.getInt("ComponentRequestID"));
                   componentRequest.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                   componentRequest.setWarrantyCode(rs.getString("WarrantyCardCode"));
                   componentRequest.setDate(rs.getDate("CRequestCreateDate"));
                   componentRequest.setWarrantyCreateDate(rs.getDate("WCardCreateDate"));
                   componentRequest.setStatus(rs.getString("Status"));
                   componentRequest.setNote(rs.getString("Note"));
                   
                   list.add(componentRequest);
               }
           }catch (Exception e){
           }

           return list;
       }
    public int totalComponentRequest(String warrantyCardCode){
        String query = """
                          select count(*) 
                          	from ComponentRequest cr 
                          	join WarrantyCard wc on cr.WarrantyCardID = wc.WarrantyCardID
                          	where 1=1""";
           if(warrantyCardCode!= null && !warrantyCardCode.trim().isEmpty()){
            query += "	and wc.WarrantyCardCode like ?";
            }
           try{
               conn = new DBContext().connection;
               ps = conn.prepareStatement(query);
               int count =1;
               if(warrantyCardCode!= null && !warrantyCardCode.trim().isEmpty()){
                   ps.setString(count++, "%"+ warrantyCardCode+"%");
                }
               rs = ps.executeQuery();
               while(rs.next()){
                   return rs.getInt(1);
               }
           }catch (Exception e){
           }

           return 0;
    }
    public ArrayList<ProductDetail> getAllListProductUnderMaintain(String warrantyCardCode, String productCode,
          String unknownProductCode,  String warrantyStatus,String typeMaintain, String sort, String order, int page, int pageSize){
        ArrayList<ProductDetail> list = new ArrayList<>();
        String query = """
                       select wc.WarrantyCardID, wc.WarrantyCardCode,wc.CreatedDate ,p.Code as ProductCode, up.ProductCode as UnknownProductCode,p.ProductName, up.ProductName as UnknownProductName 
                       , wc.IssueDescription, wc.WarrantyStatus
                       \tfrom WarrantyCard wc
                       \tleft join WarrantyProduct wp on wc.WarrantyProductID = wp.WarrantyProductID
                       \tleft join ProductDetail pd on wp.ProductDetailID = pd.ProductDetailID
                       \tleft join UnknownProduct up on wp.UnknownProductID = up.UnknownProductID
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
                       \tleft join UnknownProduct up on wp.UnknownProductID = up.UnknownProductID
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
    private  ArrayList<Component> getallListComponent(String componentCode, String componentName,
            String typeID, String brandID, int page, int pageSize){
        ArrayList<Component> list = new ArrayList<>();
            String query = """
                           select c.ComponentID, c.ComponentCode, c.ComponentName,c.TypeID, c.BrandID, b.BrandName, ct.TypeName
                           from Component c 
                           join Brand b on c.BrandID = b.BrandID
                           join ComponentType ct on c.TypeID = ct.TypeID
                           where 1=1""";
            if(componentCode != null && !componentCode.trim().isEmpty()){
                query += " and c.ComponentCode like ?";
            }
            if(componentName != null && !componentName.trim().isEmpty()){
                query += " and c.ComponentName like ?";
            }
            if(brandID != null && !brandID.trim().isEmpty()){
                query += " and c.BrandID = ?";
            }
            if(typeID != null && !typeID.trim().isEmpty()){
                query += " and c.TypeID = ?";
            }
            query += " order by ComponentID asc";
            query += " offset ? rows  fetch next ? rows only;";
        try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if(componentCode != null && !componentCode.trim().isEmpty()){
                ps.setString(count++, "%"+ componentCode + "%");
            }
            if(componentName != null && !componentName.trim().isEmpty()){
               ps.setString(count++,  "%"+ componentName + "%");
            }
            if(brandID != null && !brandID.trim().isEmpty()){
                ps.setString(count++, brandID);
            }
            if(typeID != null && !typeID.trim().isEmpty()){
                ps.setString(count++, typeID);
            }
            int offset = (page-1) * pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
            rs = ps.executeQuery();
            while(rs.next()){
                Component component = new Component();
                component.setComponentID(rs.getInt("ComponentID"));
                component.setComponentCode(rs.getString("ComponentCode"));
                component.setComponentName(rs.getString("ComponentName"));
                component.setBrandId(rs.getInt("BrandID"));
                component.setTypeId(rs.getInt("TypeID"));
                component.setBrand(rs.getString("BrandName"));
                component.setType(rs.getString("TypeName"));
                list.add(component);
            }
        }catch(Exception e){
            
        }
        return list;
    }
    
    public ArrayList<Component> getallListComponentByProductCode(String productCode, String componentCode, String componentName,
            String typeID, String brandID, int page, int pageSize){
        ArrayList<Component> list = new ArrayList<>();
            String query = """
                           select c.ComponentID, c.ComponentCode, c.ComponentName,c.TypeID, c.BrandID, b.BrandName, ct.TypeName
                           	from Product p 
                           	join ProductComponents pc on p.ProductID = pc.ProductID
                           	join Component c on pc.ComponentID = c.ComponentID
                           	join Brand b on c.BrandID = b.BrandID
                           	join ComponentType ct on c.TypeID = ct.TypeID
                           	where 1=1""";
            if(productCode != null && !productCode.trim().isEmpty()){
                query += " and p.Code like ?";
            }else{
                return getallListComponent(componentCode, componentName,
            typeID,  brandID,  page,  pageSize);
            }
            
            if(componentCode != null && !componentCode.trim().isEmpty()){
                query += " and c.ComponentCode like ?";
            }
            if(componentName != null && !componentName.trim().isEmpty()){
                query += " and c.ComponentName like ?";
            }
            if(brandID != null && !brandID.trim().isEmpty()){
                query += " and c.BrandID = ?";
            }
            if(typeID != null && !typeID.trim().isEmpty()){
                query += " and c.TypeID = ?";
            }
            query += " order by ComponentID asc";
            query += " offset ? rows  fetch next ? rows only;";
            
        try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if(productCode != null && !productCode.trim().isEmpty()){
                ps.setString(count++, productCode);
            }
            if(componentCode != null && !componentCode.trim().isEmpty()){
                ps.setString(count++, "%"+ componentCode + "%");
            }
            if(componentName != null && !componentName.trim().isEmpty()){
               ps.setString(count++,  "%"+ componentName + "%");
            }
            if(brandID != null && !brandID.trim().isEmpty()){
                ps.setString(count++, brandID);
            }
            if(typeID != null && !typeID.trim().isEmpty()){
                ps.setString(count++, typeID);
            }
            int offset = (page-1) * pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
            rs = ps.executeQuery();
            while(rs.next()){
                Component component = new Component();
                component.setComponentID(rs.getInt("ComponentID"));
                component.setComponentCode(rs.getString("ComponentCode"));
                component.setComponentName(rs.getString("ComponentName"));
                component.setBrandId(rs.getInt("BrandID"));
                component.setTypeId(rs.getInt("TypeID"));
                component.setBrand(rs.getString("BrandName"));
                component.setType(rs.getString("TypeName"));
                list.add(component);
            }
        }catch(Exception e){
            
        }
        return list;
    }
    public int totalComponentByProductCode(String productCode, String componentCode, String componentName,
            String typeID, String brandID){
        String query = """
                           select count(*)
                           	from Product p 
                           	join ProductComponents pc on p.ProductID = pc.ProductID
                           	join Component c on pc.ComponentID = c.ComponentID
                           	where 1=1""";
            if(productCode != null && !productCode.trim().isEmpty()){
                query += " and p.Code like ?";
            }
            
            if(componentCode != null && !componentCode.trim().isEmpty()){
                query += " and c.ComponentCode like ?";
            }
            if(componentName != null && !componentName.trim().isEmpty()){
                query += " and c.ComponentName like ?";
            }
            if(brandID != null && !brandID.trim().isEmpty()){
                query += " and c.BrandID = ?";
            }
            if(typeID != null && !typeID.trim().isEmpty()){
                query += " and c.TypeID = ?";
            }
            try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if(productCode != null && !productCode.trim().isEmpty()){
                ps.setString(count++, productCode);
            }
            if(componentCode != null && !componentCode.trim().isEmpty()){
                ps.setString(count++, "%"+ componentCode + "%");
            }
            if(componentName != null && !componentName.trim().isEmpty()){
               ps.setString(count++,  "%"+ componentName + "%");
            }
            if(brandID != null && !brandID.trim().isEmpty()){
                ps.setString(count++, brandID);
            }
            if(typeID != null && !typeID.trim().isEmpty()){
                ps.setString(count++, typeID);
            }
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
        }catch(Exception e){
            
        }
        return 0;
            
    }
    
        public int insertComponentRequest(int warrantyCardID, String note, Connection conn) throws SQLException {
        int componentRequestID = -1;
        String query = "INSERT INTO ComponentRequest (WarrantyCardID, [Date], Status, Note) "
                + "VALUES (?, GETDATE(), 'waiting', ?)";
        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, warrantyCardID);
            ps.setString(2, note);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    componentRequestID = rs.getInt(1);
                }
            }
        }
        return componentRequestID;
        }
        
        public void insertComponentRequestDetails(int componentRequestID, List<Integer> componentIDs, List<Integer> quantities, Connection conn) throws SQLException {
        String query = "INSERT INTO ComponentRequestDetail (ComponentID, ComponentRequestID, Quantity) VALUES (?, ?, ?)";
        if (componentIDs.size() != quantities.size()) {
            throw new IllegalArgumentException("ComponentIDs and Quantities must have the same size.");
        }

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (int i = 0; i < componentIDs.size(); i++) {
                ps.setInt(1, componentIDs.get(i));
                ps.setInt(2, componentRequestID);
                ps.setInt(3, quantities.get(i));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
        public boolean createComponentRequest(int warrantyCardID, String note, List<Integer> componentIDs, List<Integer> quantities) {
        boolean success = false;
        try {
            conn = new DBContext().connection;
            conn.setAutoCommit(false);

            int componentRequestID = insertComponentRequest(warrantyCardID, note, conn);
            if (componentRequestID > 0) {
                insertComponentRequestDetails(componentRequestID, componentIDs, quantities, conn);
                conn.commit(); 
                success = true;
            } else {
                conn.rollback(); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
        
        public ArrayList<ComponentRequestDetail> getListComponentRequestDetailById(String componentRequestID){
            ArrayList<ComponentRequestDetail> list = new ArrayList<>();
            String query = """
                          select crd.ComponentRequestDetailID, crd.ComponentID,crd.ComponentRequestID, crd.Quantity, c.ComponentCode, c.ComponentName
                          	from ComponentRequestDetail crd join Component c on crd.ComponentID = c.ComponentID where 1=1""";
            if(componentRequestID != null && !componentRequestID.trim().isEmpty()){
                query += " and ComponentRequestID = ?";
            }
           try{
               conn = new DBContext().connection;
               ps = conn.prepareStatement(query);
               int count = 1;
               if(componentRequestID != null && !componentRequestID.trim().isEmpty()){
                ps.setString(count++, componentRequestID);
                }
               rs = ps.executeQuery();
               while(rs.next()){
                   ComponentRequestDetail componentRequestDetail = new ComponentRequestDetail();
                   componentRequestDetail.setComponentID(rs.getInt("ComponentID"));
                   componentRequestDetail.setComponentRequestDetailID(rs.getInt("ComponentRequestDetailID"));
                   componentRequestDetail.setComponentRequestID(rs.getInt("ComponentRequestID"));
                   componentRequestDetail.setQuantity(rs.getInt("Quantity"));
                   componentRequestDetail.setComponentName(rs.getString("ComponentName"));
                   componentRequestDetail.setComponentCode(rs.getString("ComponentCode"));
                   list.add(componentRequestDetail);
               }
           }catch (Exception e){
           }
            return list;
        }
        
        public ArrayList<Brand> getAllBrand(){
            ArrayList<Brand> list = new ArrayList<>();
           String query = """
                        select BrandID, BrandName
                        from Brand """;
           try{
               conn = new DBContext().connection;
               ps = conn.prepareStatement(query);
               rs = ps.executeQuery();
               while(rs.next()){
                   Brand brand = new Brand();
                   brand.setBrandId(rs.getInt("BrandID"));
                   brand.setBrandName(rs.getString("BrandName"));
                   
                   list.add(brand);
               }
           }catch (Exception e){
           }

           return list;
        }
        public ArrayList<ComponentType> getAllComponentType(){
            ArrayList<ComponentType> list = new ArrayList<>();
           String query = """
                          select TypeID, TypeName
                          from ComponentType """;
           try{
               conn = new DBContext().connection;
               ps = conn.prepareStatement(query);
               rs = ps.executeQuery();
               while(rs.next()){
                   ComponentType componentType = new ComponentType();
                   componentType.setTypeID(rs.getInt("TypeID"));
                   componentType.setTypeName(rs.getString("TypeName"));
                   
                   list.add(componentType);
               }
           }catch (Exception e){
           }

           return list;
        }
        
        public void updateStatusComponentRequest(String componentRequestId, String status){
            String query ="""
                          update ComponentRequest
                          set Status = ?
                          where ComponentRequestID = ?""";
            try {
                conn = new DBContext().connection;
               ps = conn.prepareStatement(query);
               ps.setString(1, status);
               ps.setString(2, componentRequestId);
               ps.executeUpdate();
            } catch (Exception e) {
            }
        }
        
        public int getLastComponentRequestId(){
            String query ="""
                          SELECT ComponentRequestID FROM ComponentRequest
                          WHERE ComponentRequestID = (SELECT MAX(ComponentRequestID) FROM ComponentRequest);""";
            try {
                conn = new DBContext().connection;
               ps = conn.prepareStatement(query);
               rs = ps.executeQuery();
               while(rs.next()){
                   return rs.getInt(1);
               }
            } catch (Exception e) {
            }
            return 0;
        }
        
        
            
    public static void main(String[] args) {
//        ArrayList<ProductDetail> list = new ArrayList<>();
        ComponentRequestDAO dao = new ComponentRequestDAO();
//            ArrayList<ComponentRequest> list2 = dao.getAllComponentRequest("",1   , 5);
//            for (ComponentRequest x : list2) {
//                System.out.println(x);
//        }
            dao.updateStatusComponentRequest("10", "cancel");
    }
}

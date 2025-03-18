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
public class HomePage_searchWarrantyDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public ArrayList<ProductDetail> searchWarrantyDAO(String searchType, String searchValue) {
        ArrayList<ProductDetail> list = new ArrayList<>();
        if(searchValue == null || searchValue.isEmpty()){
            return list;
        }
        String query = """
          SELECT c.Name as customerName, c.Email as customerEmail, c.Phone as customerPhone,
                            wc.WarrantyCardCode, wc.CreatedDate, wc.IssueDescription, wc.WarrantyStatus,
                           up.ProductCode AS unknownProductCode, up.ProductName AS unknownProductName,
                           p.ProductName, pd.ProductCode
                    FROM WarrantyProduct wp
                    LEFT JOIN WarrantyCard wc ON wc.WarrantyProductID = wp.WarrantyProductID
                    LEFT JOIN ProductDetail pd ON wp.ProductDetailID = pd.ProductDetailID
                    LEFT JOIN UnknownProduct up ON wp.UnknownProductID = up.UnknownProductID
                    LEFT JOIN Customer c ON c.CustomerID = COALESCE(pd.CustomerID, up.CustomerID)
                    LEFT JOIN Product p ON pd.ProductID = p.ProductID
                    WHERE 1=1
                    and wc.WarrantyCardCode IS NOT NULL
        """;
        
        if ("productCode".equals(searchType)) {
            query += " AND (pd.ProductCode = ? OR up.ProductCode = ?)";
        } else if ("phone".equals(searchType)) {
            query += " AND c.Phone = ?";
        } else if ("email".equals(searchType)) {
            query += " AND c.Email = ?";
        }

        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;

            if ("productCode".equals(searchType)) {
                ps.setString(count++, searchValue);
                ps.setString(count++, searchValue);
            } else {
                ps.setString(count++, searchValue);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setName(rs.getString("customerName"));
                productDetail.setEmail(rs.getString("customerEmail"));
                productDetail.setPhone(rs.getString("customerPhone"));
                productDetail.setWarrantyCardCode(rs.getString("WarrantyCardCode"));
                productDetail.setCreatedDate(rs.getDate("CreatedDate"));
                productDetail.setIssueDescription(rs.getString("IssueDescription"));
                productDetail.setWarrantyStatus(rs.getString("WarrantyStatus"));
                productDetail.setUnknownProductCode(rs.getString("unknownProductCode"));
                productDetail.setUnknownProductName(rs.getString("unknownProductName"));
                productDetail.setProductName(rs.getString("ProductName"));
                productDetail.setProductCode(rs.getString("ProductCode"));
                list.add(productDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        HomePage_searchWarrantyDAO dao = new HomePage_searchWarrantyDAO();
        ArrayList<ProductDetail> list = dao.searchWarrantyDAO("productCode", "P2118");
        System.out.println(list);
    }
}


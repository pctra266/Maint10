/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Product;
import Model.ProductDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sonNH
 */
public class ProductDAO extends DBContext {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT \n"
                + "    p.ProductID,\n"
                + "    p.Code,\n"
                + "    p.ProductName,\n"
                + "    cb.BrandName,\n"
                + "    p.[Type],\n"
                + "    p.Quantity,\n"
                + "    p.WarrantyPeriod,\n"
                + "    p.[Status],\n"
                + "    p.Image\n"
                + "FROM \n"
                + "    Product p\n"
                + "JOIN \n"
                + "    ComponentBrand cb ON p.BrandID = cb.BrandID;";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setCode(rs.getString("Code"));
                product.setProductName(rs.getString("ProductName"));
                product.setBrandName(rs.getString("BrandName"));
                product.setType(rs.getString("Type"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setWarrantyPeriod(rs.getInt("WarrantyPeriod"));
                product.setStatus(rs.getString("Status"));
                product.setImage(rs.getString("Image"));

                products.add(product);
            }
        } catch (SQLException e) {
        }
        return products;
    }
//    get list product by customer 

    public ArrayList<ProductDetail> getListProductByCustomerID(String customerID) {
        ArrayList<ProductDetail> list = new ArrayList<>();
        String query = "select p.ProductName, wc.WarrantyCardID \n"
                + "from Customer c \n"
                + "left join ProductDetail pd on c.CustomerID = pd.CustomerID\n"
                + "join WarrantyCard wc on pd.ProductDetailID = wc.ProductDetailID\n"
                + "join Product p on pd.ProductID = p.ProductID\n"
                + "where c.CustomerID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query);){
            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProductName(rs.getString(1));
            productDetail.setWarrantyCardID(rs.getInt(2));
            list.add(productDetail);
            }
            
        }catch (SQLException e) {
        }
        return list;
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
//        List<Product> products = productDAO.getAllProducts();
//        for (Product p : products) {
//            System.out.println(p.getBrandName());
//        }
        ArrayList<ProductDetail> list = productDAO.getListProductByCustomerID("1");
        for (ProductDetail productDetail : list) {
            System.out.println(productDetail);
        }
        

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Payment;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Tra Pham
 */
public class PaymentDAO {
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
 public ArrayList<Payment> getAllPayment(){
        ArrayList<Payment> list = new ArrayList<>();
        String query = "select *from Payment";
        try{
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Payment(rs.getInt(1), rs.getInt(2), rs.getDate(3), 
                        rs.getString(4), rs.getDouble(5), rs.getString(6)));
            }
        }catch (Exception e){
        }
        
        return list;
    }
    public static void main(String[] args) {
        
        ArrayList<Payment> list = new PaymentDAO().getAllPayment();
        for (Payment payment : list) {
            System.out.println(payment);
        }
    }
 
}

package DAO;

import Model.CustomerContact;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerContactDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean addCustomerContact(CustomerContact contact) {
        String query = """
            INSERT INTO CustomerContact (Name, Email, Phone, Message, CreatedAt)
            VALUES (?, ?, ?, ?, ?)
        """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getEmail());
            ps.setString(3, contact.getPhone());
            ps.setString(4, contact.getMessage());
            ps.setTimestamp(5, new Timestamp(new Date().getTime())); // Dùng Timestamp để khớp với DATETIME

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<CustomerContact> getAllCustomerContacts() {
        List<CustomerContact> list = new ArrayList<>();
        String query = """
            SELECT ContactID, Name, Email, Phone, Message, CreatedAt 
            FROM CustomerContact ORDER BY CreatedAt DESC
        """;
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                CustomerContact contact = new CustomerContact();
                contact.setContactID(rs.getInt("ContactID"));
                contact.setName(rs.getString("Name"));
                contact.setEmail(rs.getString("Email"));
                contact.setPhone(rs.getString("Phone"));
                contact.setMessage(rs.getString("Message"));
                contact.setCreatedAt(rs.getTimestamp("CreatedAt")); // Dùng Timestamp cho DATETIME

                list.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<CustomerContact> getCustomerContacts(String name, String phone, String email, int page, int pageSize) {
        ArrayList<CustomerContact> list = new ArrayList<>();
        String query = "SELECT ContactID, Name, Email, Phone,Message, CreatedAt FROM CustomerContact WHERE 1=1";
        if(name != null && !name.trim().isEmpty()){
            query += " AND Name LIKE ?";
        }
        if(phone != null && !phone.trim().isEmpty()){
            query += " AND Phone LIKE ?";
        }
        if(email != null && !email.trim().isEmpty()){
            query += " AND Email LIKE ?";
        }
        query += " ORDER BY CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if(name != null && !name.trim().isEmpty()){
                ps.setString(count++, "%" + name.trim() + "%");
            }
            if(phone != null && !phone.trim().isEmpty()){
                ps.setString(count++, "%" + phone.trim() + "%");
            }
            if(email != null && !email.trim().isEmpty()){
                ps.setString(count++, "%" + email.trim() + "%");
            }
            int offset = (page - 1) * pageSize;
            ps.setInt(count++, offset);
            ps.setInt(count++, pageSize);
            rs = ps.executeQuery();
            while(rs.next()){
                CustomerContact cc = new CustomerContact();
                cc.setContactID(rs.getInt("ContactID"));
                cc.setName(rs.getString("Name"));
                cc.setEmail(rs.getString("Email"));
                cc.setPhone(rs.getString("Phone"));
                cc.setMessage(rs.getString("Message"));
                cc.setCreatedAt(rs.getDate("CreatedAt"));
                list.add(cc);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public int totalCustomerContacts(String name, String phone, String email) {
        String query = "SELECT COUNT(*) FROM CustomerContact WHERE 1=1";
        if(name != null && !name.trim().isEmpty()){
            query += " AND Name LIKE ?";
        }
        if(phone != null && !phone.trim().isEmpty()){
            query += " AND Phone LIKE ?";
        }
        if(email != null && !email.trim().isEmpty()){
            query += " AND Email LIKE ?";
        }
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            int count = 1;
            if(name != null && !name.trim().isEmpty()){
                ps.setString(count++, "%" + name.trim() + "%");
            }
            if(phone != null && !phone.trim().isEmpty()){
                ps.setString(count++, "%" + phone.trim() + "%");
            }
            if(email != null && !email.trim().isEmpty()){
                ps.setString(count++, "%" + email.trim() + "%");
            }
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}

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
}

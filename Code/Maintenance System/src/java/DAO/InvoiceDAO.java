/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ADMIN
 */
import Model.Invoice;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO extends DBContext {

    public boolean addInvoice(Invoice invoice) {
        String sql = "INSERT INTO Invoice (InvoiceNumber, InvoiceType, WarrantyCardID, Amount,  DueDate, Status, CreatedBy, ReceivedBy, CustomerID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, invoice.getInvoiceNumber());
            stmt.setString(2, invoice.getInvoiceType());
            stmt.setInt(3, invoice.getWarrantyCardID());
            stmt.setDouble(4, invoice.getAmount());
            stmt.setDate(5, new java.sql.Date(invoice.getDueDate().getTime()) );
            stmt.setString(6, invoice.getStatus());
            stmt.setInt(7, invoice.getCreatedBy());
            if (invoice.getReceivedBy() != null) {
                stmt.setInt(8, invoice.getReceivedBy());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }
            if (invoice.getCustomerID() != null) {
                stmt.setInt(9, invoice.getCustomerID());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }
            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Invoice getInvoiceById(int invoiceID) {
        String sql = "SELECT * FROM Invoice WHERE InvoiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, invoiceID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToInvoice(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoice";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    public void updateInvoiceStatus(int invoiceID, String status) {
        String sql = "UPDATE Invoice SET Status = ? WHERE InvoiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, invoiceID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteInvoice(int invoiceID) {
        String sql = "DELETE FROM Invoice WHERE InvoiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, invoiceID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Invoice mapResultSetToInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceID(rs.getInt("InvoiceID"));
        invoice.setInvoiceNumber(rs.getString("InvoiceNumber"));
        invoice.setInvoiceType(rs.getString("InvoiceType"));
        invoice.setWarrantyCardID(rs.getInt("WarrantyCardID"));
        invoice.setAmount(rs.getDouble("Amount"));
        invoice.setIssuedDate(rs.getTimestamp("IssuedDate"));
        invoice.setDueDate(rs.getTimestamp("DueDate"));
        invoice.setStatus(rs.getString("Status"));
        invoice.setCreatedBy(rs.getInt("CreatedBy"));
        invoice.setReceivedBy(rs.getObject("ReceivedBy") != null ? rs.getInt("ReceivedBy") : null);
        invoice.setCustomerID(rs.getObject("CustomerID") != null ? rs.getInt("CustomerID") : null);
        return invoice;
    }
}

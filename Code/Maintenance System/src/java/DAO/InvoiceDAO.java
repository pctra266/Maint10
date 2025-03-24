package DAO;

/**
 *
 * @author ADMIN
 */
import Model.Invoice;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceDAO extends DBContext {

    private final String SELECT = """
                                  SELECT i.[InvoiceID]
                                        ,i.[InvoiceNumber]
                                        ,i.[InvoiceType]
                                        ,i.[WarrantyCardID]
                                        ,i.[Amount]
                                        ,i.[IssuedDate]
                                        ,i.[DueDate]
                                        ,i.[Status]
                                        ,i.[CreatedBy]
                                        ,i.[ReceivedBy]
                                        ,i.[CustomerID]
                                    FROM [dbo].[Invoice] i
                                  """;

    public boolean addInvoice(Invoice invoice) {
        String sql = "INSERT INTO Invoice (InvoiceNumber, InvoiceType, WarrantyCardID, Amount,  DueDate, Status, CreatedBy, ReceivedBy, CustomerID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, invoice.getInvoiceNumber());
            stmt.setString(2, invoice.getInvoiceType());
            stmt.setInt(3, invoice.getWarrantyCardID());
            stmt.setDouble(4, invoice.getAmount());
            stmt.setDate(5, new java.sql.Date(invoice.getDueDate().getTime()));
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
        String sql = SELECT + " WHERE InvoiceID = ?";
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

    public Invoice getInvoiceByCode(String code) {
        String sql = SELECT + " WHERE InvoiceNumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, code);
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
        String sql = SELECT;
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    public List<Invoice> getAllInvoicesOfCard(int card) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = SELECT + "join WarrantyCard wc on i.WarrantyCardID = wc.WarrantyCardID\n"
                + "  where i.WarrantyCardID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, card);
            ResultSet rs = stmt.executeQuery();
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

    public boolean updateInvoice(Invoice invoice) {
        String sql = "Update Invoice Set InvoiceNumber=?, InvoiceType=?, WarrantyCardID=?, Amount=?,  DueDate=?, Status=?, CreatedBy=?, ReceivedBy=?, CustomerID=? "
                + "WHERE InvoiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, invoice.getInvoiceNumber());
            stmt.setString(2, invoice.getInvoiceType());
            stmt.setInt(3, invoice.getWarrantyCardID());
            stmt.setDouble(4, invoice.getAmount());
            stmt.setDate(5, new java.sql.Date(invoice.getDueDate().getTime()));
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
            stmt.setInt(10, invoice.getInvoiceID());
            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countInvoiceByCreatorWithSearch(int staffId,
            String invoiceNumber, String issueDate, String dueDate) {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total "
                + "FROM Invoice "
                + "WHERE CreatedBy = ? ";

        List<Object> params = new ArrayList<>();
        params.add(staffId);

        if (invoiceNumber != null && !invoiceNumber.trim().isEmpty()) {
            sql += " AND InvoiceNumber LIKE ? ";
        }
        if (issueDate != null && !issueDate.trim().isEmpty()) {
            sql += " AND CONVERT(date, IssuedDate) = ? ";
        }
        if (dueDate != null && !dueDate.trim().isEmpty()) {
            sql += " AND CONVERT(date, DueDate) = ? ";
        }

        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            ps.setInt(index++, staffId);

            if (invoiceNumber != null && !invoiceNumber.trim().isEmpty()) {
                ps.setString(index++, "%" + invoiceNumber + "%");
            }
            if (issueDate != null && !issueDate.trim().isEmpty()) {
                ps.setString(index++, issueDate);
            }
            if (dueDate != null && !dueDate.trim().isEmpty()) {
                ps.setString(index++, dueDate);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return total;
    }

    public List<Invoice> getListInvoiceByCreatorWithSearch(int staffId,
            String invoiceNumber, String issueDate, String dueDate,
            int offset, int limit) {
        List<Invoice> list = new ArrayList<>();

        String sql = "SELECT * FROM Invoice "
                + "WHERE CreatedBy = ? ";

        List<Object> params = new ArrayList<>();
        params.add(staffId);

        if (invoiceNumber != null && !invoiceNumber.trim().isEmpty()) {
            sql += " AND InvoiceNumber LIKE ? ";
        }
        if (issueDate != null && !issueDate.trim().isEmpty()) {
            sql += " AND CONVERT(date, IssuedDate) = ? ";
        }
        if (dueDate != null && !dueDate.trim().isEmpty()) {
            sql += " AND CONVERT(date, DueDate) = ? ";
        }

        // Sắp xếp
        sql += " ORDER BY InvoiceID DESC ";

        // Phân trang: SQL Server 2012+ => OFFSET/FETCH
        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            int index = 1;
            ps.setInt(index++, staffId);
            if (invoiceNumber != null && !invoiceNumber.trim().isEmpty()) {
                ps.setString(index++, "%" + invoiceNumber + "%");
            }
            if (issueDate != null && !issueDate.trim().isEmpty()) {
                ps.setString(index++, issueDate);
            }
            if (dueDate != null && !dueDate.trim().isEmpty()) {
                ps.setString(index++, dueDate);
            }

            ps.setInt(index++, offset);
            ps.setInt(index++, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Invoice inv = new Invoice();
                inv.setInvoiceID(rs.getInt("InvoiceID"));
                inv.setInvoiceNumber(rs.getString("InvoiceNumber"));
                inv.setInvoiceType(rs.getString("InvoiceType"));
                inv.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                inv.setAmount(rs.getDouble("Amount"));
                inv.setIssuedDate(rs.getTimestamp("IssuedDate"));
                inv.setDueDate(rs.getTimestamp("DueDate"));
                inv.setStatus(rs.getString("Status"));
                inv.setCreatedBy(rs.getInt("CreatedBy"));

                int rBy = rs.getInt("ReceivedBy");
                inv.setReceivedBy(rs.wasNull() ? null : rBy);

                int cID = rs.getInt("CustomerID");
                inv.setCustomerID(rs.wasNull() ? null : cID);

                list.add(inv);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Invoice getInvoiceByIdRepair(int invoiceId) {
        Invoice inv = null;
        String sql = "SELECT * FROM Invoice WHERE InvoiceID = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                inv = new Invoice();
                inv.setInvoiceID(rs.getInt("InvoiceID"));
                inv.setInvoiceNumber(rs.getString("InvoiceNumber"));
                inv.setInvoiceType(rs.getString("InvoiceType"));
                inv.setWarrantyCardID(rs.getInt("WarrantyCardID"));
                inv.setAmount(rs.getDouble("Amount"));
                inv.setIssuedDate(rs.getTimestamp("IssuedDate"));
                inv.setDueDate(rs.getTimestamp("DueDate"));
                inv.setStatus(rs.getString("Status"));
                inv.setCreatedBy(rs.getInt("CreatedBy"));

                int rBy = rs.getInt("ReceivedBy");
                inv.setReceivedBy(rs.wasNull() ? null : rBy);

                int cID = rs.getInt("CustomerID");
                inv.setCustomerID(rs.wasNull() ? null : cID);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return inv;
    }

    public Map<String, Object> getInvoiceDetails(int invoiceId) {
        Map<String, Object> invoiceDetails = new HashMap<>();
        String sql = "SELECT i.InvoiceID,i.InvoiceNumber, i.Amount, i.IssuedDate, i.DueDate, i.Status, "
                + "creator.[Name] AS CreatedByName, "
                + "receiver.[Name] AS ReceivedByName, "
                + "w.WarrantyCardCode, w.IssueDescription "
                + "FROM Invoice i "
                + "JOIN Staff creator ON i.CreatedBy = creator.StaffID "
                + "LEFT JOIN Staff receiver ON i.ReceivedBy = receiver.StaffID "
                + "JOIN WarrantyCard w ON i.WarrantyCardID = w.WarrantyCardID "
                + "WHERE i.InvoiceID = ?";
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, invoiceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    invoiceDetails.put("InvoiceID", rs.getString("InvoiceID"));
                    invoiceDetails.put("InvoiceNumber", rs.getString("InvoiceNumber"));
                    invoiceDetails.put("Amount", rs.getFloat("Amount"));
                    invoiceDetails.put("IssuedDate", rs.getDate("IssuedDate"));
                    invoiceDetails.put("DueDate", rs.getDate("DueDate"));
                    invoiceDetails.put("Status", rs.getString("Status"));
                    invoiceDetails.put("CreatedByName", rs.getString("CreatedByName"));
                    invoiceDetails.put("ReceivedByName", rs.getString("ReceivedByName"));
                    invoiceDetails.put("WarrantyCardCode", rs.getString("WarrantyCardCode"));
                    invoiceDetails.put("IssueDescription", rs.getString("IssueDescription"));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return invoiceDetails;
    }

}

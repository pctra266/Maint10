/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Payment;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class PaymentDAO extends DBContext {
    
       public boolean addPayment(Payment payment) {
        String query = "INSERT INTO Payment (PaymentDate, PaymentMethod, Amount, Status, InvoiceID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, payment.getPaymentDate()!=null ? new java.sql.Timestamp(payment.getPaymentDate().getTime()):null);
            stmt.setString(2, payment.getPaymentMethod());
            stmt.setDouble(3, payment.getAmount());
            stmt.setString(4, payment.getStatus());
            if (payment.getInvoiceID() != null) {
                stmt.setInt(5, payment.getInvoiceID());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm ánh xạ ResultSet -> Payment
    private Payment mapPayment(ResultSet rs) throws SQLException {
        return new Payment(
                rs.getInt("PaymentID"),
                rs.getDate("PaymentDate"),
                rs.getString("PaymentMethod"),
                rs.getDouble("Amount"),
                rs.getString("Status"),
                rs.getObject("InvoiceID") != null ? rs.getInt("InvoiceID") : null // Xử lý giá trị null
        );
    }

    // Lấy tất cả Payment
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT PaymentID, PaymentDate, PaymentMethod, Amount, Status, InvoiceID FROM Payment";

        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                payments.add(mapPayment(rs)); // Gọi hàm mapPayment
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    // Lấy Payment theo ID
    public Payment getPaymentById(int paymentId) {
        String query = "SELECT PaymentID, PaymentDate, PaymentMethod, Amount, Status, InvoiceID FROM Payment WHERE PaymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, paymentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPayment(rs); // Gọi hàm mapPayment
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Xóa Payment theo ID
    public boolean deletePayment(int paymentId) {
        String query = "DELETE FROM Payment WHERE PaymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, paymentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật Payment
    public boolean updatePayment(Payment payment) {
        String query = "UPDATE Payment SET PaymentDate = ?, PaymentMethod = ?, Amount = ?, Status = ?, InvoiceID = ? WHERE PaymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, payment.getPaymentDate()!=null ? new java.sql.Timestamp(payment.getPaymentDate().getTime()):null);
            stmt.setString(2, payment.getPaymentMethod());
            stmt.setDouble(3, payment.getAmount());
            stmt.setString(4, payment.getStatus());
            if (payment.getInvoiceID() != null) {
                stmt.setInt(5, payment.getInvoiceID());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            stmt.setInt(6, payment.getPaymentID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

package Controller.WarrantyCard;

import DAO.InvoiceDAO;
import Model.Invoice;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author sonNH
 */
public class RepairCreateInvoice extends HttpServlet {

    private String generateRandomInvoiceNumber() {
        int randomNum = (int) (Math.random() * 90000000) + 10000000; // Số ngẫu nhiên 8 chữ số
        return "INV" + randomNum;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy các tham số từ form
        String amountStr = request.getParameter("amount");
        String dueDateStr = request.getParameter("dueDate");
        String invoiceType = request.getParameter("invoiceType");
        String status = request.getParameter("status");
        String staffIdStr = request.getParameter("staffId");
        String contractorCardIDStr = request.getParameter("contractorCardID");
        String warrantyCardIDStr = request.getParameter("warrantyCardID");

        double amount = Double.parseDouble(amountStr);
        int warrantyCardID = Integer.parseInt(warrantyCardIDStr);
        int contractorCardID = Integer.parseInt(contractorCardIDStr);
        int staffID = Integer.parseInt(staffIdStr);

        java.sql.Date dueDate = java.sql.Date.valueOf(dueDateStr);

        // Tạo số hoá đơn ngẫu nhiên
        String invoiceNumber = generateRandomInvoiceNumber();

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoiceType(invoiceType);
        invoice.setWarrantyCardID(warrantyCardID);
        invoice.setAmount(amount);
        invoice.setDueDate(dueDate);
        invoice.setStatus(status);
        // Giả sử CreatedBy là contractorCardID, ReceivedBy là staffID
        invoice.setCreatedBy(contractorCardID);
        invoice.setReceivedBy(staffID);
        invoice.setCustomerID(null);

        // Sử dụng DAO để thêm invoice mới
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        boolean result = invoiceDAO.addInvoice(invoice);
        if (result) {
            request.setAttribute("successMessage", "Invoice created successfully with Invoice Number: " + invoiceNumber);
        } else {
            request.setAttribute("errorMessage", "Error creating invoice.");
        }
        request.getRequestDispatcher("repairInvoice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String warrantyCardIDStr = request.getParameter("cardId");
        String contractorCardID = request.getParameter("code");
        String staffId = request.getParameter("staffId");
        // Set các thuộc tính vào request để truyền sang trang JSP
        request.setAttribute("warrantyCardID", warrantyCardIDStr);
        request.setAttribute("contractorCardID", contractorCardID);
        request.setAttribute("staffId", staffId);
        request.getRequestDispatcher("repairInvoice.jsp").forward(request, response);
    }

}

package Controller.WarrantyCard;

import DAO.ContractorCardDAO;
import DAO.InvoiceDAO;
import Model.ContractorCard;
import Model.Invoice;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 * @author sonNH
 */
public class RepairCreateInvoice extends HttpServlet {

    private String generateRandomInvoiceNumber() {
        int randomNum = (int) (Math.random() * 90000000) + 10000000;
        return "INV" + randomNum;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String amountStr = request.getParameter("amount");
        String dueDateStr = request.getParameter("dueDate");
        String invoiceType = request.getParameter("invoiceType");
        String status = request.getParameter("status");
        String staffIdStr = request.getParameter("staffId");
        String contractorCardIDStr = request.getParameter("contractorCardID");
        String warrantyCardIDStr = request.getParameter("warrantyCardID");

        PrintWriter out = response.getWriter();
        out.println("warrantyCardIDStr: " + warrantyCardIDStr);
        out.println("contractorCardIDStr: " + contractorCardIDStr);
        out.println("staffIdStr: " + staffIdStr);
        out.println("amountStr: " + amountStr);
        out.println("dueDateStr: " + dueDateStr);
        out.println("invoiceType: " + invoiceType);
        out.println("status: " + status);

        ContractorCardDAO contractorDAO = new ContractorCardDAO();

        ContractorCard c = contractorDAO.getContractorCardById(Integer.parseInt(contractorCardIDStr));
        int contractorid = c.getContractorID();

        double amount = Double.parseDouble(amountStr);
        int warrantyCardID = Integer.parseInt(warrantyCardIDStr);
        int staffID = Integer.parseInt(staffIdStr);

        java.sql.Date dueDate = java.sql.Date.valueOf(dueDateStr);

        String invoiceNumber = generateRandomInvoiceNumber();

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoiceType(invoiceType);
        invoice.setWarrantyCardID(warrantyCardID);
        invoice.setAmount(amount);
        invoice.setDueDate(dueDate);
        invoice.setStatus(status);
        invoice.setCreatedBy(contractorid);
        invoice.setReceivedBy(staffID);
        invoice.setCustomerID(null);
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        boolean result = invoiceDAO.addInvoice(invoice);
        if (result) {
            request.setAttribute("successMessage", "Invoice created successfully with Invoice Number: " + invoiceNumber);
        } else {
            request.setAttribute("errorMessage", "Error creating invoice.");
        }

        request.setAttribute("amount", amountStr);
        request.setAttribute("dueDate", dueDateStr);
        request.setAttribute("invoiceType", invoiceType);
        request.setAttribute("status", status);
        request.setAttribute("staffId", staffIdStr);
        request.setAttribute("contractorCardID", contractorCardIDStr);
        request.setAttribute("warrantyCardID", warrantyCardIDStr);

        request.getRequestDispatcher("repairInvoice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String warrantyCardIDStr = request.getParameter("warrantyCardId");
        String contractorCardID = request.getParameter("code");
        String staffId = request.getParameter("staffId");
        
        request.setAttribute("warrantyCardID", warrantyCardIDStr);
        request.setAttribute("contractorCardID", contractorCardID);
        request.setAttribute("staffId", staffId);

        request.getRequestDispatcher("repairInvoice.jsp").forward(request, response);
    }

}

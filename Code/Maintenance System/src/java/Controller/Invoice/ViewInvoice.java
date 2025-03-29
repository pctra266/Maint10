package Controller.Invoice;

import DAO.InvoiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author sonNH
 */
public class ViewInvoice extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String invoiceIdParam = request.getParameter("invoiceId");

        // Nếu invoiceId không tồn tại, rỗng hoặc không phải là số, chuyển hướng đến 404Page.jsp
        if (invoiceIdParam == null || invoiceIdParam.trim().isEmpty() || !invoiceIdParam.matches("\\d+")) {
            request.getRequestDispatcher("404Page.jsp").forward(request, response);
            return;
        }
        try {
            int invoiceId = Integer.parseInt(invoiceIdParam);
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            Map<String, Object> invoiceDetail = invoiceDAO.getInvoiceDetails(invoiceId);
            if (invoiceDetail != null && !invoiceDetail.isEmpty()) {
                request.setAttribute("invoiceDetail", invoiceDetail);
                request.getRequestDispatcher("viewInvoiceDetail.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("errorMessage", "Invoice not exists.");
            }
        } catch (NumberFormatException e) {
            request.getRequestDispatcher("404Page.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("listInvoiceRepair.jsp").forward(request, response);
    }
}

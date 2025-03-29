package Controller.Invoice;

import DAO.InvoiceDAO;
import Model.Invoice;
import Model.Staff;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class ListInvoiceRepair extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        int staffId = staff.getStaffID();

        String invoiceNumber = request.getParameter("invoiceNumber");
        String issueDate = request.getParameter("issueDate");
        String dueDate = request.getParameter("dueDate");

        String pageSizeStr = request.getParameter("pageSize");
        String customPageSizeStr = request.getParameter("customPageSize");

        int pageSize = 5; 
        if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
            if (pageSizeStr.equals("Custom")) {
                if (customPageSizeStr != null && !customPageSizeStr.trim().isEmpty()) {
                    try {
                        pageSize = Integer.parseInt(customPageSizeStr.trim());
                    } catch (NumberFormatException e) {
                        pageSize = 5;
                    }
                }
            } else {
                try {
                    pageSize = Integer.parseInt(pageSizeStr);
                } catch (NumberFormatException e) {
                    pageSize = 5;
                }
            }
        }

        int pageIndex = 1;
        String pageIndexStr = request.getParameter("pageIndex");
        if (pageIndexStr != null) {
            try {
                pageIndex = Integer.parseInt(pageIndexStr);
            } catch (NumberFormatException e) {
            }
        }

        int offset = (pageIndex - 1) * pageSize;
        InvoiceDAO invoiceDAO = new InvoiceDAO();

        int totalRecords = invoiceDAO.countInvoiceByCreatorWithSearch(staffId, invoiceNumber, issueDate, dueDate);
        int totalPage = (int) Math.ceil((double) totalRecords / pageSize);

        List<Invoice> listInvoice = invoiceDAO.getListInvoiceByCreatorWithSearch(staffId, invoiceNumber, issueDate, dueDate, offset, pageSize);
        request.setAttribute("listInvoice", listInvoice);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("invoiceNumber", invoiceNumber);
        request.setAttribute("issueDate", issueDate);
        request.setAttribute("dueDate", dueDate);
        request.setAttribute("customPageSize", customPageSizeStr);

        request.getRequestDispatcher("ListInvoiceRepair.jsp").forward(request, response);
    }

}

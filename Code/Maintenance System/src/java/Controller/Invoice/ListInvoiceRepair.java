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

        // Lấy các tham số từ request
        String invoiceNumber = request.getParameter("invoiceNumber");
        String issueDate = request.getParameter("issueDate");
        String dueDate = request.getParameter("dueDate");

        // Lấy giá trị pageSize (hoặc "Custom") và customPageSize
        String pageSizeStr = request.getParameter("pageSize");
        String customPageSizeStr = request.getParameter("customPageSize");

        int pageSize = 5; // giá trị mặc định nếu không chọn gì
        if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
            if (pageSizeStr.equals("Custom")) {
                // Nếu chọn Custom thì đọc giá trị customPageSize
                if (customPageSizeStr != null && !customPageSizeStr.trim().isEmpty()) {
                    try {
                        pageSize = Integer.parseInt(customPageSizeStr.trim());
                    } catch (NumberFormatException e) {
                        // Nếu người dùng nhập bậy thì gán về mặc định
                        pageSize = 5;
                    }
                }
            } else {
                // Nếu không phải "Custom" thì parse pageSize bình thường
                try {
                    pageSize = Integer.parseInt(pageSizeStr);
                } catch (NumberFormatException e) {
                    // Nếu lỗi parse thì gán về mặc định
                    pageSize = 5;
                }
            }
        }

        // Lấy pageIndex
        int pageIndex = 1;
        String pageIndexStr = request.getParameter("pageIndex");
        if (pageIndexStr != null) {
            try {
                pageIndex = Integer.parseInt(pageIndexStr);
            } catch (NumberFormatException e) {
            }
        }

        // Tính offset
        int offset = (pageIndex - 1) * pageSize;

        // Gọi DAO
        InvoiceDAO invoiceDAO = new InvoiceDAO();

        int totalRecords = invoiceDAO.countInvoiceByCreatorWithSearch(staffId, invoiceNumber, issueDate, dueDate);
        int totalPage = (int) Math.ceil((double) totalRecords / pageSize);

        List<Invoice> listInvoice = invoiceDAO
                .getListInvoiceByCreatorWithSearch(staffId, invoiceNumber, issueDate, dueDate, offset, pageSize);

        // Set các attribute cho JSP
        request.setAttribute("listInvoice", listInvoice);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPage", totalPage);

        request.setAttribute("invoiceNumber", invoiceNumber);
        request.setAttribute("issueDate", issueDate);
        request.setAttribute("dueDate", dueDate);

        // Đừng quên set thêm customPageSize (nếu cần) để JSP giữ lại giá trị cũ khi reload
        request.setAttribute("customPageSize", customPageSizeStr);

        request.getRequestDispatcher("ListInvoiceRepair.jsp").forward(request, response);
    }

}

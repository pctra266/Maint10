package Controller.WarrantyCard;

import DAO.ContractorCardDAO;
import Model.ContractorCard;
import Model.Staff;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

public class WarrantyCardRepairContractor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Staff s = (Staff) session.getAttribute("staff");
        if (session == null || s == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int contractorId = s.getStaffID();
        String warrantyCardCode = request.getParameter("warrantyCardCode");
        String staffName = request.getParameter("staffName");
        String date = request.getParameter("date");
        String status = request.getParameter("status");
        String note = request.getParameter("note");
        PrintWriter out = response.getWriter();
//
//        out.print(warrantyCardCode);
//        out.print(staffName);
//        out.print(date);
//        out.print(status);
//        out.print(note);

        // Lấy tham số pageSize (5,10,15,20,25, hoặc "custom") và customPageSize
        String pageSizeParam = request.getParameter("pageSize"); // giá trị select
        String customPageSizeParam = request.getParameter("customPageSize"); // nếu user nhập
        int pageSize = 10; // default nếu không có tham số

        try {
            if ("custom".equals(pageSizeParam)) {
                // Nếu user chọn "Custom" thì lấy giá trị từ customPageSize
                if (customPageSizeParam != null && !customPageSizeParam.trim().isEmpty()) {
                    pageSize = Integer.parseInt(customPageSizeParam);
                }
            } else if (pageSizeParam != null && !pageSizeParam.trim().isEmpty()) {
                pageSize = Integer.parseInt(pageSizeParam);
            }
        } catch (NumberFormatException e) {
        }

        // Lấy tham số currentPage
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        } catch (NumberFormatException e) {
        }
        int offset = (currentPage - 1) * pageSize;

        ContractorCardDAO dao = new ContractorCardDAO();
        int totalCount = dao.countContractorCards(
                contractorId, warrantyCardCode, staffName, date, status, note);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<ContractorCard> contractorCards = dao.searchContractorCards(
                contractorId, warrantyCardCode, staffName, date, status, note, offset, pageSize);

        request.setAttribute("contractorCards", contractorCards);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);

        request.getRequestDispatcher("listWarrantyCardRepairContractor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}

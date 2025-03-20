package Controller.WarrantyCard;

import DAO.ContractorCardDAO;
import DAO.WarrantyCardDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author sonNH
 */
public class WarrantyCardDetailContractor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
        String idStr = request.getParameter("cardId");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.getWriter().write("Parameter warrantyCardId is required.");
            return;
        }
        int warrantyCardId = Integer.parseInt(idStr);

        Map<String, Object> warrantyDetails = warrantyCardDAO.getWarrantyCardDetailsMap(warrantyCardId);

        request.setAttribute("warrantyDetails", warrantyDetails);
        request.getRequestDispatcher("detailWarrantyCardContractor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ContractorCardDAO contractorDAO = new ContractorCardDAO();
        WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();

        // Lấy tham số từ request
        String contractorCardID = request.getParameter("code");
        String status = request.getParameter("status");
        String subStatus = request.getParameter("subStatus");
        String idStr = request.getParameter("cardId");

        if (contractorCardID == null || status == null || idStr == null || idStr.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid parameters!");
            return;
        }

        int warrantyCardId = Integer.parseInt(idStr);

        boolean success = contractorDAO.updateContractorStatus(Integer.parseInt(contractorCardID), status);

        String staffID = request.getParameter("staffId");
        if (staffID == null) {
            request.setAttribute("errorMessage", "Update Status Failed");
        }

        // Xử lý cho bảng WarrantyCardProcess:
        // Nếu subStatus không được truyền lên, tự động thiết lập theo quy tắc:
        // - Nếu status = "receive" -> action = "accept_outsource"
        // - Nếu status = "done" -> action = "fixed_outsource"
        // - Nếu status = "cancel" -> action = "refuse_outsource"
        if (subStatus == null || subStatus.trim().isEmpty()) {
            if ("receive".equals(status)) {
                subStatus = "accept_outsource";
            } else if ("done".equals(status)) {
                subStatus = "fixed_outsource";
            } else if ("cancel".equals(status)) {
                subStatus = "refuse_outsource";
            }
        }

        boolean processUpdated = warrantyCardDAO.addWarrantyCardProcess(warrantyCardId, Integer.parseInt(staffID), subStatus, "");

        Map<String, Object> warrantyDetails = warrantyCardDAO.getWarrantyCardDetailsMap(warrantyCardId);
        request.setAttribute("warrantyDetails", warrantyDetails);

        if (success && processUpdated) {
            request.setAttribute("successMessage", "Update Status Successfully");
        } else {
            request.setAttribute("errorMessage", "Update Status Failed");
        }

        request.getRequestDispatcher("detailWarrantyCardContractor.jsp").forward(request, response);
    }
}

package Controller.WarrantyCard;

import DAO.ContractorCardDAO;
import DAO.WarrantyCardDAO;
import Model.ContractorCard;
import Model.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

/**
 *
 * @author sonNH
 */
public class WarrantyCardDetailContractor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
        String idStr = request.getParameter("cardId");
        // Kiểm tra nếu idStr không phải là số hợp lệ
        if (idStr == null || idStr.trim().isEmpty() || !idStr.matches("\\d+")) {
            request.getRequestDispatcher("404Page.jsp").forward(request, response);
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
        String contractorCardID = request.getParameter("code");
        String status = request.getParameter("status");
        String subStatus = request.getParameter("subStatus");
        String idStr = request.getParameter("cardId");
        String staffIdStr = request.getParameter("staffId");

        if (contractorCardID == null || status == null || idStr == null || staffIdStr == null
                || contractorCardID.trim().isEmpty() || status.trim().isEmpty()
                || idStr.trim().isEmpty() || staffIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Invalid parameters!");
            request.getRequestDispatcher("detailWarrantyCardContractor.jsp").forward(request, response);
            return;
        }

        int warrantyCardId = Integer.parseInt(idStr);
        int staffId = Integer.parseInt(staffIdStr);
        ContractorCardDAO contractorDAO = new ContractorCardDAO();
        WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();

        ContractorCard c = contractorDAO.getContractorCardById(Integer.parseInt(contractorCardID));
        int contractorid = c.getContractorID();

        boolean success = contractorDAO.updateContractorStatus(Integer.parseInt(contractorCardID), status);

        String note = "";
        if (subStatus.equalsIgnoreCase("refuse_outsource")) {
            note = "Repair Contractor " + contractorid + " refused warranty for Technician " + staffIdStr;
        } else if (subStatus.equalsIgnoreCase("lost")) {
            note = "The repair contractor " + contractorid + " reported that the product was missing.";
        } else if (subStatus.equalsIgnoreCase("receive_outsource")) {
            note = "The repair contractor " + contractorid + " received the product from Technician " + staffIdStr;
        } else if (subStatus.equalsIgnoreCase("fixed_outsource")) {
            note = "Repair Contractor " + contractorid + " has completed the repair.";
        } else if (subStatus.equalsIgnoreCase("unfixable_outsource")) {
            note = "Repair Contractor " + contractorid + " failed to repair the product.";
        } else if (subStatus.equalsIgnoreCase("back_outsource")) {
            note = "Repair Contractor " + contractorid + " returned the product to Technician " + staffIdStr;
        } else if (subStatus.equalsIgnoreCase("accept_outsource")) {
            note = "Repair Contractor " + contractorid + " accepted the outsource request for Technician " + staffIdStr;
        } else if (subStatus.equalsIgnoreCase("send_outsource")) {
            note = "Technician " + staffIdStr + " has sent the product.";
        }

        //process lay tu session
        HttpSession session = request.getSession();
        Staff currentStaff = (Staff) session.getAttribute("staff");
        if (currentStaff != null) {
            staffId = currentStaff.getStaffID();
        }
        boolean processUpdated = warrantyCardDAO.addWarrantyCardProcess(warrantyCardId, staffId, subStatus, note);

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

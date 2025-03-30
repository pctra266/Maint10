package Controller.WarrantyCard;

import DAO.ContractorCardDAO;
import DAO.WarrantyCardDAO;
import Model.ContractorCard;
import Model.Staff;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarrantyCardDetailContractor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("cardId");
        String wStr = request.getParameter("warrantyId");

        // Kiểm tra null, rỗng và chuỗi chỉ chứa số
        if (idStr == null || idStr.trim().isEmpty() || !idStr.matches("\\d+")
                || wStr == null || wStr.trim().isEmpty() || !wStr.matches("\\d+")) {
            request.getRequestDispatcher("404Page.jsp").forward(request, response);
            return;
        }
        try {
            // Sử dụng BigInteger để kiểm tra giới hạn của int
            BigInteger idBig = new BigInteger(idStr);
            BigInteger wBig = new BigInteger(wStr);
            BigInteger maxInt = BigInteger.valueOf(Integer.MAX_VALUE);

            if (idBig.compareTo(maxInt) > 0 || wBig.compareTo(maxInt) > 0) {
                request.getRequestDispatcher("404Page.jsp").forward(request, response);
                return;
            }

            int contractorCardId = idBig.intValue();
            int warrantyCardId = wBig.intValue();

            WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
            Map<String, Object> warrantyDetails = warrantyCardDAO.getContractorCardWarrantyDetails(contractorCardId);
            try {
                String action = warrantyCardDAO.getLatestAction(warrantyCardId, contractorCardId);
                request.setAttribute("action", action);
            } catch (SQLException ex) {
                // Xử lý ngoại lệ nếu cần
            }
            request.setAttribute("warrantyDetails", warrantyDetails);
            request.getRequestDispatcher("detailWarrantyCardContractor.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Nếu có bất kỳ lỗi chuyển đổi nào, chuyển hướng đến trang 404
            request.getRequestDispatcher("404Page.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contractorCardID = request.getParameter("code");
        String status = request.getParameter("status");
        String subStatus = request.getParameter("subStatus");
        String warrantyCardIdStr = request.getParameter("warrantyCardId");
        String staffIdStr = request.getParameter("staffId");

        PrintWriter out = response.getWriter();

//        out.println(contractorCardID);
//        out.println(status);
//        out.println(subStatus);
//        out.println(warrantyCardId);
//        out.println(staffIdStr);
        if (contractorCardID == null || status == null || warrantyCardIdStr == null || staffIdStr == null
                || contractorCardID.trim().isEmpty() || status.trim().isEmpty()
                || warrantyCardIdStr.trim().isEmpty() || staffIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Invalid parameters!");
            request.getRequestDispatcher("detailWarrantyCardContractor.jsp").forward(request, response);
            return;
        }

        int warrantyCardId = Integer.parseInt(warrantyCardIdStr);
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

        HttpSession session = request.getSession();
        Staff currentStaff = (Staff) session.getAttribute("staff");
        if (currentStaff != null) {
            staffId = currentStaff.getStaffID();
        }
        boolean processUpdated = warrantyCardDAO.addWarrantyCardProcess(warrantyCardId, staffId, subStatus, note);

        Map<String, Object> warrantyDetails = warrantyCardDAO.getContractorCardWarrantyDetails(Integer.parseInt(contractorCardID));
        request.setAttribute("warrantyDetails", warrantyDetails);
        if (success && processUpdated) {
            request.setAttribute("successMessage", "Update Status Successfully");
        } else {
            request.setAttribute("errorMessage", "Update Status Failed");
        }

        try {
            String action = warrantyCardDAO.getLatestAction(warrantyCardId, Integer.parseInt(contractorCardID));
            request.setAttribute("action", action);
        } catch (SQLException ex) {
        }
        request.getRequestDispatcher("detailWarrantyCardContractor.jsp").forward(request, response);
    }
}

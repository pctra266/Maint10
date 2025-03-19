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
        ContractorCardDAO c = new ContractorCardDAO();
        String contractorCardID = request.getParameter("code");
        String status = request.getParameter("status");
        String idStr = request.getParameter("cardId");

        if (contractorCardID == null || status == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid parameters!");
            return;
        }

        boolean success = c.updateContractorStatus(Integer.parseInt(contractorCardID), status);
        WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
        if (idStr == null || idStr.trim().isEmpty()) {
            response.getWriter().write("Parameter warrantyCardId is required.");
            return;
        }
        int warrantyCardId = Integer.parseInt(idStr);

        Map<String, Object> warrantyDetails = warrantyCardDAO.getWarrantyCardDetailsMap(warrantyCardId);

        request.setAttribute("warrantyDetails", warrantyDetails);
        if (success) {
            request.setAttribute("successMessage", "Update Status Sucessfully");
            request.getRequestDispatcher("detailWarrantyCardContractor.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Update Status Failed");
            request.getRequestDispatcher("detailWarrantyCardContractor.jsp").forward(request, response);
        }

    }
}

package Controller.WarrantyCard;

import DAO.ComponentDAO;
import DAO.ComponentRequestDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardDetailDAO;
import Model.Component;
import Model.ComponentRequestDetail;
import Model.WarrantyCard;
import Model.WarrantyCardDetail;
import Utils.FormatUtils;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "WarrantyCardDetail", urlPatterns = {"/WarrantyCard/Detail"})
public class WarrantyCardDetailServlet extends HttpServlet {

    private final WarrantyCardDetailDAO wcdDao = new WarrantyCardDetailDAO();
    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final ComponentDAO componentDAO = new ComponentDAO();
    private final ComponentRequestDAO componentRequestDAO = new ComponentRequestDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idPara = request.getParameter("ID");
        Integer id = FormatUtils.tryParseInt(idPara);
        if (id == null || warrantyCardDAO.getWarrantyCardById(id) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        List<WarrantyCardDetail> cardDetails = wcdDao.getWarrantyCardDetailOfCard(id);
        WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(id);
        List<Component> availableComponents = componentDAO.getAllComponents();
        if ("1".equals(request.getParameter("addSuccess"))) {
            request.setAttribute("addAlert1", "Component added successfully!");
        }
        request.setAttribute("cardDetails", cardDetails);
        request.setAttribute("card", wc);
        request.setAttribute("availableComponents", availableComponents);
        

        request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardDetail.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String warrantyCardIdParam = request.getParameter("ID");
        Integer warrantyCardId = FormatUtils.tryParseInt(warrantyCardIdParam);

        if (warrantyCardId == null || warrantyCardDAO.getWarrantyCardById(warrantyCardId) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        if ("update".equals(action)) {
            String warrantyCardDetailIdParam = request.getParameter("warrantyCardDetailID");
            String status = request.getParameter("status");
            String quantityParam = request.getParameter("quantity");
            String priceParam = request.getParameter("price");
            Integer warrantyCardDetailId = FormatUtils.tryParseInt(warrantyCardDetailIdParam);
            Integer quantity = FormatUtils.tryParseInt(quantityParam);
            Double price = FormatUtils.tryParseDouble(priceParam);

            if (warrantyCardDetailId != null) {
                WarrantyCardDetail detail = wcdDao.getWarrantyCardDetailById(warrantyCardDetailId);
                if (detail != null) {
                    boolean updated = false;
                    if (status != null && isValidStatus(status)) {
                        detail.setStatus(status);
                        if ("warranty_repaired".equals(status) || "warranty_replaced".equals(status)) {
                            detail.setPrice(0.0); // Force price to 0
                        }
                        updated = true;
                    }
                    if (quantity != null && quantity >= 0) {
                        detail.setQuantity(quantity);
                        updated = true;
                    }
                    if (price != null && price >= 0 && !("warranty_repaired".equals(detail.getStatus()) || "warranty_replaced".equals(detail.getStatus()))) {
                        detail.setPrice(price);
                        updated = true;
                    }
                    if (updated) {
                        boolean success = wcdDao.updateWarrantyCardDetail(detail);
                        if (success) {
                            request.setAttribute("updateAlert1", "Update successful!");
                        } else {
                            request.setAttribute("updateAlert0", "Failed to update.");
                        }
                    }
                }
            }
        } else if ("delete".equals(action)) {
            String warrantyCardDetailIdParam = request.getParameter("warrantyCardDetailID");
            Integer warrantyCardDetailId = FormatUtils.tryParseInt(warrantyCardDetailIdParam);

            if (warrantyCardDetailId != null) {
                boolean deleted = wcdDao.deleteWarrantyCardDetail(warrantyCardDetailId);
                if (deleted) {
                    request.setAttribute("updateAlert1", "Component deleted successfully!");
                } else {
                    request.setAttribute("updateAlert0", "Failed to delete component.");
                }
            }
        }

        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private boolean isValidStatus(String status) {
        return "warranty_repaired".equals(status) 
                || "repaired".equals(status) 
                || "replace".equals(status)
                || "warranty_replaced".equals(status)
                || "fixing".equals(status);
    }
}
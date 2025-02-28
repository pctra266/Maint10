package Controller.WarrantyCard;

import DAO.ComponentDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardDetailDAO;
import Model.Component;
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

@WebServlet(name = "WarrantyCardAddComponent", urlPatterns = {"/WarrantyCard/AddComponent"})
public class WarrantyCardAddComponent extends HttpServlet {

    private final WarrantyCardDetailDAO wcdDao = new WarrantyCardDetailDAO();
    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final ComponentDAO componentDAO = new ComponentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String warrantyCardIdParam = request.getParameter("ID");
        Integer warrantyCardId = FormatUtils.tryParseInt(warrantyCardIdParam);

        if (warrantyCardId == null || warrantyCardDAO.getWarrantyCardById(warrantyCardId) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        List<Component> availableComponents = componentDAO.getAllComponents();
        request.setAttribute("warrantyCardID", warrantyCardId);
        request.setAttribute("availableComponents", availableComponents);
                //Truyen du lieu cho nut back
                HttpSession session = request.getSession();
        session.setAttribute("componentWarehouseFrom", request.getContextPath()+request.getServletPath()+"?ID="+warrantyCardId);

        request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardAddComponent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String warrantyCardIdParam = request.getParameter("ID");
        Integer warrantyCardId = FormatUtils.tryParseInt(warrantyCardIdParam);

        if (warrantyCardId == null || warrantyCardDAO.getWarrantyCardById(warrantyCardId) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        String componentIdParam = request.getParameter("componentID");
        String status = request.getParameter("status");
        String quantityParam = request.getParameter("quantity");
        String priceParam = request.getParameter("price");
        Integer componentId = FormatUtils.tryParseInt(componentIdParam);
        Integer quantity = FormatUtils.tryParseInt(quantityParam);
        Double price = FormatUtils.tryParseDouble(priceParam);

        if (componentId != null && isValidStatus(status) && quantity != null && quantity >= 0 && price != null && price >= 0) {
            Component component = componentDAO.getComponentByID(componentId);
            if (component != null) {
                WarrantyCardDetail newDetail = new WarrantyCardDetail();
                newDetail.setWarrantyCardID(warrantyCardId);
                newDetail.setComponent(component);
                newDetail.setStatus(status);
                newDetail.setQuantity(quantity);
                if ("warranty_repaired".equals(status) || "warranty_replaced".equals(status)) {
                    newDetail.setPrice(0.0); // Force price to 0
                } else {
                    newDetail.setPrice(price); // Use provided price
                }

                boolean added = wcdDao.addWarrantyCardDetailDAO(newDetail);
                if (added) {
                    response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?ID=" + warrantyCardId + "&addSuccess=1");
                } else {
                    request.setAttribute("error", "Failed to add component.");
                    doGet(request, response); // Reload the page with error
                }
            }
        } else {
            request.setAttribute("error", "Invalid input data.");
            doGet(request, response);
        }
    }

    private boolean isValidStatus(String status) {
        return "warranty_repaired".equals(status) 
                || "repaired".equals(status) 
                || "replace".equals(status)
                || "warranty_replaced".equals(status)
                || "fixing".equals(status);
    }
}
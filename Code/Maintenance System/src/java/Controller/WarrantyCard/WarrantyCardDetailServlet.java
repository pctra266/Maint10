/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.WarrantyCard;

import DAO.ComponentDAO;
import DAO.CustomerDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardDetailDAO;
import Model.Component;
import Model.WarrantyCardDetail;
import Model.WarrantyCard;
import Utils.FormatUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "WarrantyCardDetail", urlPatterns = {"/WarrantyCard/Detail"})
public class WarrantyCardDetailServlet extends HttpServlet {

    private final WarrantyCardDetailDAO wcdDao = new WarrantyCardDetailDAO();
    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final ComponentDAO componentDAO = new ComponentDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Nut back
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isBlank()) {
            referer = "/MaintenanceSystem/WarrantyCard";
        }
        request.setAttribute("backUrl", referer);
        //
        String idPara = request.getParameter("ID");
        Integer id = FormatUtils.tryParseInt(idPara);
        if (id == null || warrantyCardDAO.getWarrantyCardById(id) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }
        List<WarrantyCardDetail> cardDetails = wcdDao.getWarrantyCardDetailOfCard(id);
        WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(id);
        List<Component> availableComponents = componentDAO.getAllComponents(); // Fetch all components
        request.setAttribute("cardDetails", cardDetails);
        request.setAttribute("card", wc);
        request.setAttribute("availableComponents", availableComponents); // Pass to JSP
        request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardDetail.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            Integer warrantyCardDetailId = FormatUtils.tryParseInt(warrantyCardDetailIdParam);
            Integer quantity = FormatUtils.tryParseInt(quantityParam);

            if (warrantyCardDetailId != null) {
                WarrantyCardDetail detail = wcdDao.getWarrantyCardDetailById(warrantyCardDetailId);
                if (detail != null) {
                    boolean updated = false;
                    if (status != null && isValidStatus(status)) {
                        detail.setStatus(status);
                        updated = true;
                    }
                    if (quantity != null && quantity >= 0) {
                        detail.setQuantity(quantity);
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
        else if ("add".equals(action)) {
            String componentIdParam = request.getParameter("componentID");
            String status = request.getParameter("status");
            String quantityParam = request.getParameter("quantity");
            Integer componentId = FormatUtils.tryParseInt(componentIdParam);
            Integer quantity = FormatUtils.tryParseInt(quantityParam);

            if (componentId != null && isValidStatus(status) && quantity != null && quantity >= 0) {
                Component component = componentDAO.getComponentByID(componentId);
                if (component != null) {
                    WarrantyCardDetail newDetail = new WarrantyCardDetail();
                    newDetail.setWarrantyCardID(warrantyCardId);
                    newDetail.setComponent(component);
                    newDetail.setStatus(status);
                    newDetail.setQuantity(quantity);
                    newDetail.setPrice(component.getPrice()); // Set price from component

                    boolean added = wcdDao.addWarrantyCardDetailDAO(newDetail);
                    if (added) {
                        request.setAttribute("addAlert1", "Component added successfully!");
                    } else {
                        request.setAttribute("updateAlert0", "Failed to add component.");
                    }
                }
            }
        }

        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private boolean isValidStatus(String status) {
        return "under_warranty".equals(status) || "repaired".equals(status) || "replace".equals(status);
    }

}

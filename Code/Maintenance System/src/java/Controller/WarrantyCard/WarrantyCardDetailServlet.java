package Controller.WarrantyCard;

import DAO.ComponentDAO;
import DAO.ComponentRequestDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardDetailDAO;
import DAO.WarrantyCardProcessDAO; // New DAO for WarrantyCardProcess
import Model.Component;
import Model.Staff;
import Model.WarrantyCard;
import Model.WarrantyCardDetail;
import Model.WarrantyCardProcess;
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
    private final WarrantyCardProcessDAO wcpDao = new WarrantyCardProcessDAO(); // New DAO

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPara = request.getParameter("ID");
        Integer id = FormatUtils.tryParseInt(idPara);
        if (id == null || warrantyCardDAO.getWarrantyCardById(id) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        Integer handlerID = null;
        if (staff != null) {
            handlerID = staff.getStaffID();
        }

        List<WarrantyCardDetail> cardDetails = wcdDao.getWarrantyCardDetailOfCard(id);
        WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(id);
        List<Component> availableComponents = componentDAO.getAllComponents();
        WarrantyCardProcess latestProcess = wcpDao.getLatestProcessByWarrantyCardId(id); // Fetch latest process

        if ("1".equals(request.getParameter("addSuccess"))) {
            request.setAttribute("addAlert1", "Component added successfully!");
        }
        request.setAttribute("cardDetails", cardDetails);
        request.setAttribute("card", wc);
        request.setAttribute("availableComponents", availableComponents);
        request.setAttribute("latestProcess", latestProcess); // Pass to JSP
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

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            request.setAttribute("updateAlert0", "You must be logged in to perform this action.");
            processRequest(request, response);
            return;
        }

        WarrantyCardProcess latestProcess = wcpDao.getLatestProcessByWarrantyCardId(warrantyCardId);
        request.setAttribute("l", latestProcess);
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
                            detail.setPrice(0.0);
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
        } else if ("process".equals(action)) {
            System.out.println("Check");

            String processAction = request.getParameter("processAction");
            if (processAction != null && isValidProcessAction(processAction)) {
                boolean canProcess = false;
                switch (processAction) {
                    case "fixing" ->
                        canProcess = latestProcess != null && ("reception".equals(latestProcess.getAction()));
                    case "refix" ->
                        canProcess = latestProcess != null && ("fixed".equals(latestProcess.getAction()) || "completed".equals(latestProcess.getAction()) || "cancel".equals(latestProcess.getAction()));
                    case "outsource" ->
                        canProcess = latestProcess != null && !"completed".equals(latestProcess.getAction()) && ("fixing".equals(latestProcess.getAction()) || "refix".equals(latestProcess.getAction()));
                    case "fixed" ->
                        canProcess = latestProcess != null && !"completed".equals(latestProcess.getAction()) && ("fixing".equals(latestProcess.getAction()) || "refix".equals(latestProcess.getAction()) || "outsource".equals(latestProcess.getAction()));
                    case "completed" ->
                        canProcess = latestProcess != null && "fixed".equals(latestProcess.getAction());
                    case "cancel" ->
                        canProcess = latestProcess != null || !"completed".equals(latestProcess.getAction());
                }

                if (canProcess) {
                    WarrantyCardProcess newProcess = new WarrantyCardProcess();
                    newProcess.setWarrantyCardID(warrantyCardId);
                    newProcess.setHandlerID(staff.getStaffID());
                    newProcess.setAction(processAction);
                    boolean success = wcpDao.addWarrantyCardProcess(newProcess);
                    if (success) {
                        WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
                        if ("completed".equals(processAction) || "cancel".equals(processAction)) {
                            wc.setWarrantyStatus(processAction);
                        }
                        if ("fixed".equals(processAction)) {
                            wc.setWarrantyStatus("done");

                        }
                        warrantyCardDAO.updateWarrantyCard(wc); // Update WarrantyCard status

                        request.setAttribute("updateAlert1", processAction.substring(0, 1).toUpperCase() + processAction.substring(1) + " action successful!");
                    } else {
                        request.setAttribute("updateAlert0", "Failed to process " + processAction + ".");
                    }
                } else {
                    request.setAttribute("updateAlert0", "Cannot perform " + processAction + " at this stage.");
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

    private boolean isValidProcessAction(String action) {
        return "reception".equals(action) || "fixing".equals(action) || "refix".equals(action) || "outsource".equals(action)
                || "fixed".equals(action) || "completed".equals(action) || "cancel".equals(action);
    }
}

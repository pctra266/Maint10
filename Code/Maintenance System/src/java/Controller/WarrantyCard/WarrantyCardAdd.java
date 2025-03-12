/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.WarrantyCard;

import DAO.CustomerDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Customer;
import Model.ProductDetail;
import Model.Staff;
import Model.WarrantyCard;
import Model.WarrantyCardProcess;
import Utils.FormatUtils;
import Utils.OtherUtils;
import java.io.IOException;
import java.util.Date;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddWarrantyCard", urlPatterns = {"/WarrantyCard/Add"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class WarrantyCardAdd extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final WarrantyCardProcessDAO WarrantyCardProcessDAO = new WarrantyCardProcessDAO();

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
        String productCode = request.getParameter("productCode");
        String issue = request.getParameter("issue");
        String action = request.getParameter("action");
        Date returnDate = FormatUtils.parseDate(request.getParameter("returnDate"));
        List<String> mediaPaths = new ArrayList<>();
        boolean canAdd = true;
        if (returnDate != null && returnDate.before(new Date())) {
            request.setAttribute("createFail", "Return Date must be today or later.");
            request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardCreate.jsp").forward(request, response);
            return;
        }
        for (Part part : request.getParts()) {
            if ("mediaFiles".equals(part.getName()) && part.getSize() > 0) {
                String mimeType = part.getContentType();
                String mediaPath;
                if (mimeType != null && mimeType.startsWith("video/")) {
                    mediaPath = OtherUtils.saveVideo(part, request, "media/warranty-card");
                } else {
                    mediaPath = OtherUtils.saveImage(part, request, "media/warranty-card");
                }
                if (mediaPath != null && !mediaPath.startsWith("Invalid") && !mediaPath.startsWith("File is too large")) {
                    mediaPaths.add(mediaPath);
                } else {
                    canAdd = false;
                    request.setAttribute("pictureAlert", mediaPath != null ? mediaPath : "Error uploading media");
                }
            }
        }
        if (issue != null) {
            HttpSession session = request.getSession();
            Staff staff = (Staff) session.getAttribute("staff");
            Customer customer = (Customer) session.getAttribute("customer");
            int handlerID = (staff != null) ? staff.getStaffID() : (customer != null ? customer.getCustomerID() : -1);
            WarrantyCardProcess wcp = new WarrantyCardProcess();
            if (canAdd && wcp.checkAndSetWarrantyCardId(warrantyCardDAO.createWarrantyCard(productCode, issue, returnDate, mediaPaths))) {
                wcp.setHandlerID(handlerID);
                wcp.setAction("create");
                wcp.setNote(staff != null ? "Created by staff" : "Created by customer");
                WarrantyCardProcessDAO.addWarrantyCardProcess(wcp);
                if (customer==null && "receive".equals(action)) {
                    WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(wcp.getWarrantyCardID());
                    wc.setHandlerID(handlerID);
                    warrantyCardDAO.updateWarrantyCard(wc);
                    wcp.setAction("receive");
                    wcp.setHandlerID(handlerID);
                    wcp.setNote("technician received");
                    WarrantyCardProcessDAO.addWarrantyCardProcess(wcp);
                }
                if (staff!= null) response.sendRedirect("../WarrantyCard?create=true");
                else response.sendRedirect("../yourwarrantycard?create=true");
                return;
            } else {
                request.setAttribute("createFail", "Fail to create card");
            }
        }
        ProductDetail pd = warrantyCardDAO.getProductDetailByCode(productCode);
        if (pd == null && productCode != null) {
            request.setAttribute("NotFoundProduct", "No product has this code!");
        } else {
            request.setAttribute("pd", pd);
            if (pd != null) {
                request.setAttribute("cusID", customerDAO.getCustomerByEmail(pd.getEmail()).getCustomerID());
            }
        }

        request.setAttribute("ProductCode", productCode);
        request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardCreate.jsp").forward(request, response);
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

}

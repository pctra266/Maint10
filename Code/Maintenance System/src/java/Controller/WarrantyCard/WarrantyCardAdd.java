/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.WarrantyCard;

import DAO.CustomerDAO;
import DAO.WarrantyCardDAO;
import Model.ProductDetail;
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
import jakarta.servlet.http.Part;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddWarrantyCard", urlPatterns = {"/WarrantyCard/Add"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class WarrantyCardAdd extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

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
        Date returnDate = FormatUtils.parseDate(request.getParameter("returnDate"));
        Part imagePart = request.getPart("newImage");
        String image = OtherUtils.saveImage(imagePart, request, "img/warranty-card");
        boolean canAdd=true;
         if (image == null) {
        } else if (image.equalsIgnoreCase("Invalid picture")) {
            canAdd = false;
            request.setAttribute("pictureAlert", "Invalid picture");
        }
        if (issue != null) {
            if (canAdd&&warrantyCardDAO.createWarrantyCard(productCode, issue, returnDate, image)) {
                response.sendRedirect("../WarrantyCard?create=true");
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
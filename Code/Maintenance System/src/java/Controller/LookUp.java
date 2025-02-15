/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CustomerDAO;
import DAO.WarrantyCardDAO;
import Model.WarrantyCard;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class LookUp extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LookUp</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LookUp at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        WarrantyCardDAO warrantyCardDao = new WarrantyCardDAO();
        CustomerDAO customerDao = new CustomerDAO();
        String phone = request.getParameter("phone");
        String warrantyCode = request.getParameter("code");
        request.setAttribute("phone", phone);
        request.setAttribute("warrantyCode", warrantyCode);
                
        // echeck 
        if (customerDao.getCustomerByPhone(phone) == null) {
            request.setAttribute("phoneError", "Please enter correct phone!");
            request.getRequestDispatcher("LookUpOnline.jsp").forward(request, response);
            return;
        }
        if (warrantyCardDao.getWarrantyCardByCode(warrantyCode) == null) {
            request.setAttribute("codeError", "Please enter correct warranty card code!");
            request.getRequestDispatcher("LookUpOnline.jsp").forward(request, response);
            return;
        }
        WarrantyCard warantycard = warrantyCardDao.getWarrantyCardByPhoneAndCode(phone, warrantyCode);
        request.setAttribute("warrantycard", warantycard);

        request.getRequestDispatcher("LookUpOnlineResult.jsp").forward(request, response);
        //
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

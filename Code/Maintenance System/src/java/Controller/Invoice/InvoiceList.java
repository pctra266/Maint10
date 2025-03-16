/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Invoice;

import DAO.InvoiceDAO;
import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Invoice;
import Model.WarrantyCard;
import Utils.FormatUtils;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name="InvoiceList", urlPatterns={"/Invoice/List"})
public class InvoiceList extends HttpServlet {
     private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final WarrantyCardProcessDAO wcpDAO = new WarrantyCardProcessDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet InvoiceList</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InvoiceList at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String warrantyCardIDParam = request.getParameter("ID");
         Integer warrantyCardId = FormatUtils.tryParseInt(warrantyCardIDParam);

        if (warrantyCardId == null || warrantyCardDAO.getWarrantyCardById(warrantyCardId) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        // Lấy thông tin WarrantyCard
        WarrantyCard warrantyCard = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
        // Lấy danh sách invoice từ database
        List<Invoice> invoices = invoiceDAO.getAllInvoicesOfCard(warrantyCardId);

        request.setAttribute("warrantyCard", warrantyCard);
        request.setAttribute("invoices", invoices);
        request.getRequestDispatcher("/views/Invoice/InvoiceList.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

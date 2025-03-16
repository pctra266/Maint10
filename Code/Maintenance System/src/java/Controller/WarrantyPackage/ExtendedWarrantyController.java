/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.WarrantyPackage;

import DAO.ExtendedWarrantyDAO;
import Model.ExtendedWarranty;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="ExtendedWarrantyController", urlPatterns={"/ExtendedWarrantyController"})
public class ExtendedWarrantyController extends HttpServlet {
   private final ExtendedWarrantyDAO ewDao = new ExtendedWarrantyDAO();
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
            out.println("<title>Servlet ExtendedWarrantyController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExtendedWarrantyController at " + request.getContextPath () + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         String action = request.getParameter("action");
        
        if(action == null || action.trim().isEmpty()){
            ArrayList<ExtendedWarranty> list = ewDao.getListExtendedWarranty();
            request.setAttribute("extendedWarranties", list);
            request.getRequestDispatcher("extendedWarrantyList.jsp").forward(request, response);
        } else if(action.equals("new")){
            request.getRequestDispatcher("addExtendedWarranty.jsp").forward(request, response);
        } else if(action.equals("edit")){
            String id = request.getParameter("extendedWarrantyID");
            ExtendedWarranty ew = ewDao.getExtendedWarrantyByID(id);
            request.setAttribute("extendedWarranty", ew);
            request.getRequestDispatcher("editExtendedWarranty.jsp").forward(request, response);
        } else if(action.equals("delete")){
            String id = request.getParameter("extendedWarrantyID");
            boolean deleted = ewDao.deleteExtendedWarranty(id);
            String message = deleted ? "Extended warranty deleted successfully." : "Delete failed.";
            request.setAttribute("message", message);
            ArrayList<ExtendedWarranty> list = ewDao.getListExtendedWarranty();
            request.setAttribute("extendedWarranties", list);
            request.getRequestDispatcher("extendedWarrantyList.jsp").forward(request, response);
        }
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
        String action = request.getParameter("action");
        if(action.equals("new")){
            ExtendedWarranty ew = new ExtendedWarranty();
            ew.setExtendedWarrantyName(request.getParameter("extendedWarrantyName"));
            ew.setExtendedPeriodInMonths(Integer.parseInt(request.getParameter("extendedPeriodInMonths")));
            ew.setPrice(Double.parseDouble(request.getParameter("price")));
            ew.setExtendedWarrantyDescription(request.getParameter("extendedWarrantyDescription"));
            boolean created = ewDao.createExtendedWarranty(ew);
            String message = created ? "Extended warranty created successfully." : "Creation failed.";
            request.setAttribute("message", message);
        } else if(action.equals("edit")){
            ExtendedWarranty ew = new ExtendedWarranty();
            ew.setExtendedWarrantyID(Integer.parseInt(request.getParameter("extendedWarrantyID")));
            ew.setExtendedWarrantyName(request.getParameter("extendedWarrantyName"));
            ew.setExtendedPeriodInMonths(Integer.parseInt(request.getParameter("extendedPeriodInMonths")));
            ew.setPrice(Double.parseDouble(request.getParameter("price")));
            ew.setExtendedWarrantyDescription(request.getParameter("extendedWarrantyDescription"));
            boolean updated = ewDao.updateExtendedWarranty(ew);
            String message = updated ? "Extended warranty updated successfully." : "Update failed.";
            request.setAttribute("message", message);
        }
        
        ArrayList<ExtendedWarranty> list = ewDao.getListExtendedWarranty();
        request.setAttribute("extendedWarranties", list);
        request.getRequestDispatcher("extendedWarrantyList.jsp").forward(request, response);
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

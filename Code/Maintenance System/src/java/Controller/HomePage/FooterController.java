/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.HomePage;

import DAO.HomePage_FooterDAO;
import Model.Footer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="FooterController", urlPatterns={"/FooterController"})
public class FooterController extends HttpServlet {
   private final HomePage_FooterDAO footerDao = new HomePage_FooterDAO();
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String slogan = request.getParameter("slogan");
        String address = request.getParameter("address");
        String hotline = request.getParameter("hotline");
        String email = request.getParameter("email");
        String copyrightYear = request.getParameter("copyrightYear");
        
        Footer footer = new Footer();
        footer.setSlogan(slogan);
        footer.setAddress(address);
        footer.setHotline(hotline);
        footer.setEmail(email);
        footer.setCopyrightYear(copyrightYear);
        boolean success = footerDao.updateFooter(footer);
        if(success){
            request.setAttribute("messFooter", "update successfully");
        }else{
            request.setAttribute("messFooter", "update fail");
        }
        request.setAttribute("footer", footer);
        request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
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
        processRequest(request, response);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.HomePage;

import DAO.HomePage_ContactDAO;
import DAO.HomePage_FooterDAO;
import DAO.HomePage_MarketingServiceItemDAO;
import DAO.HomePage_MarketingServiceSectionDAO;
import Model.MarketingServiceItem;
import Model.MarketingServiceSection;
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
 * @author Tra Pham
 */
@WebServlet(name="customizeHomepage", urlPatterns={"/customizeHomepage"})
public class customizeHomepage extends HttpServlet {
    private final HomePage_FooterDAO footerDao = new HomePage_FooterDAO();
    private final HomePage_ContactDAO contactDao = new HomePage_ContactDAO();
    private final HomePage_MarketingServiceSectionDAO sectionDAO = new HomePage_MarketingServiceSectionDAO();
    private final HomePage_MarketingServiceItemDAO itemDAO = new HomePage_MarketingServiceItemDAO();
    

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setAttribute("contactText", contactDao.getContactText());
        request.setAttribute("footer", footerDao.getFooter());
        
        MarketingServiceSection section = sectionDAO.getSectionByID(1);
        request.setAttribute("section", section);
        
        // Tích hợp doGet của MarketingServiceItemController
        List<MarketingServiceItem> items = itemDAO.getItemsBySectionID(1);
        request.setAttribute("items", items);
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

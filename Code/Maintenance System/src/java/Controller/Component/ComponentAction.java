/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Component;

import DAO.ComponentDAO;
import Model.Component;
import Utils.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ComponentAction", urlPatterns = {"/ComponentWarehouse/Edit", "/ComponentWarehouse/Delete"})
public class ComponentAction extends HttpServlet {
    private final ComponentDAO componentDAO = new ComponentDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        String componentID = request.getParameter("ID");
        
        if (NumberUtils.tryParseInt(componentID) != null) {
            int id = NumberUtils.tryParseInt(componentID);
            if (action.equals("/ComponentWarehouse/Edit")) {
                // Xử lý chỉnh sửa component
                Component component = componentDAO.getComponentByID(id);
                if(component==null) {
                     response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
                     return;
                }
                request.setAttribute("component", component);
                request.getRequestDispatcher("/Component/ComponentDetail.jsp").forward(request, response);
            } else if (action.equals("/ComponentWarehouse/Delete")) {
                // Xử lý xóa component
                componentDAO.deleteComponent(id);
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
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

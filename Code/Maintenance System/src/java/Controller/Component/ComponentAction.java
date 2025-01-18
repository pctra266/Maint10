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
@WebServlet(name = "ComponentAction", urlPatterns = {"/ComponentWarehouse/Detail", "/ComponentWarehouse/Delete", "/ComponentWarehouse/Edit"})
public class ComponentAction extends HttpServlet {

    private final ComponentDAO componentDAO = new ComponentDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        String componentID = request.getParameter("ID");

        if (NumberUtils.tryParseInt(componentID) != null) {
            //Kiem tra component co ton tai moi xu ly tiep
            int id = NumberUtils.tryParseInt(componentID);
            Component component = componentDAO.getComponentByID(id);
            if (component == null) {
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
                return;
            }
            if (action.equals("/ComponentWarehouse/Detail")) {
                // Xử lý chỉnh sửa component

                request.setAttribute("component", component);
                request.getRequestDispatcher("/Component/ComponentDetail.jsp").forward(request, response);
            } else if (action.equals("/ComponentWarehouse/Delete")) {
                // Xử lý xóa component
                componentDAO.delete(id);
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
            } else if (action.equals("/ComponentWarehouse/Edit")) {
                //Xử lý chỉnh sửa component

                    String newName = request.getParameter("Name");
                    Integer newQuantity = NumberUtils.tryParseInt(request.getParameter("Quantity"));
                    Double newPrice = NumberUtils.tryParseDouble(request.getParameter("Price"));
                    String img = request.getParameter("newImage");
                    boolean canAdd = true;
                    //Kiểm tra xem các dữ liệu đầu vào có valid
                    if (newName.isEmpty() || newName.isBlank()) {
                        request.setAttribute("nameAlert", "Name is invalid!");
                        canAdd = false;
                    }
                    if (newQuantity == null || newQuantity < 0) {
                        request.setAttribute("quantityAlert", "Quantity must be integer greater than 0");
                        canAdd = false;
                    }
                    if (newPrice == null || newPrice < 0) {
                        request.setAttribute("priceAlert", "Price must be float greater than 0");
                        canAdd = false;
                    }
                    if(canAdd) {
                        component.setComponentName(newName);
                        component.setPrice(newPrice);
                        component.setQuantity(newQuantity);
                        if(img.isBlank()||img.isEmpty()){} else component.setImage(request.getContextPath()+"/img/Component/"+img);
                        componentDAO.update(component);
                    }
                
                request.setAttribute("component", component);
                request.getRequestDispatcher("/Component/ComponentDetail.jsp").forward(request, response);
            }
        } else {
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
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

    public boolean isValidID(Integer ID) {
        if (ID == null) {
            return false;
        }
        return componentDAO.getComponentByID(ID) == null;
    }

}

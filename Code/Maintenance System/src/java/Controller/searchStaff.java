/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.StaffDAO;
import Model.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class searchStaff extends HttpServlet {
  

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
        String search = request.getParameter("search");
        String searchname = request.getParameter("searchname");
        StaffDAO dao = new StaffDAO();
        List<Staff> list ;
        int pagesize=5;
        int pageIndex = 1;
        String pageIndexStr = request.getParameter("index");

        if (pageIndexStr != null) {
            try {
                pageIndex = Integer.parseInt(pageIndexStr);
            } catch (NumberFormatException e) {
                pageIndex = 1;
            }
        }
        int totalStaff;
        list = dao.searchStaffWithPagination(search, searchname, pageIndex, pagesize);
        totalStaff = dao.searchStaff(search, searchname).size();
        int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
        request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("search", search);
        request.setAttribute("searchname", searchname);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("list", list);
        request.getRequestDispatcher("Staff.jsp").forward(request, response);
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

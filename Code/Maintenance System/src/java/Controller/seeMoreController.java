/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.StaffDAO;
import DAO.StaffLogDAO;
import Model.Staff;
import Model.StaffLog;
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
public class seeMoreController extends HttpServlet {


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
        StaffLogDAO dao = new StaffLogDAO();
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAll";
        }
        switch(action){
            case "viewAll":
                String search = request.getParameter("search");
                String searchname = request.getParameter("searchname");
                String column = request.getParameter("column");
                String sortOrder = request.getParameter("sortOrder");
                String page_size = request.getParameter("page_size");
                int pagesize = 5; 

                if (page_size != null && !page_size.isEmpty()) {
                    try {
                        pagesize = Integer.parseInt(page_size);
                    } catch (NumberFormatException e) {
                        pagesize = 5; 
                    }
                }
                List<StaffLog> list ;
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
                list = dao.getAllFeedback(searchname, search, pageIndex, pagesize, column, sortOrder);
                totalStaff = dao.getAllFeedback(searchname, search, column, sortOrder).size();
                int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                request.setAttribute("totalPageCount", totalPageCount);
                request.setAttribute("search", search);
                request.setAttribute("searchname", searchname);
                request.setAttribute("column", column);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("pageIndex", pageIndex);
                request.setAttribute("page_size", page_size);
                request.setAttribute("list", list);
                request.getRequestDispatcher("all-changeStaff.jsp").forward(request, response);
                break;
                
            default:
                break;
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
        StaffLogDAO dao = new StaffLogDAO();
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAll";
        }
        switch(action){
            case "viewAll":
                String search = request.getParameter("search");
                String searchname = request.getParameter("searchname");
                String column = request.getParameter("column");
                String sortOrder = request.getParameter("sortOrder");
                List<StaffLog> list ;
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
                list = dao.getAllFeedback(searchname, search, pageIndex, pagesize, column, sortOrder);
                totalStaff = dao.getAllFeedback(searchname, search, column, sortOrder).size();
                int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                request.setAttribute("totalPageCount", totalPageCount);
                request.setAttribute("search", search);
                request.setAttribute("searchname", searchname);
                request.setAttribute("column", column);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("pageIndex", pageIndex);
                request.setAttribute("list", list);
                request.getRequestDispatcher("all-changeStaff.jsp").forward(request, response);
                break;
                
            default:
                break;
        }      
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

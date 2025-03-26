/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.AdminDAO;
import Model.Admin;
import Utils.Pagination;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="InvoiceController", urlPatterns={"/InvoiceController"})
public class InvoiceController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
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
        AdminDAO dao = new AdminDAO();
        List<Admin> list;
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
                String name = "";
                if(searchname != null && !searchname.isEmpty()){
                    name = searchname.trim().replaceAll("\\s+", " ");
                }
                
                int pagesize = 6; 

                if (page_size != null && !page_size.isEmpty()) {
                    try {
                        pagesize = Integer.parseInt(page_size);
                    } catch (NumberFormatException e) {
                        pagesize = 5; 
                    }
                }
                    
                int pageIndex = 1;
                String pageIndexStr = request.getParameter("page");

                if (pageIndexStr != null) {
                    try {
                        pageIndex = Integer.parseInt(pageIndexStr);
                    } catch (NumberFormatException e) {
                        pageIndex = 1;
                    }
                }
                int totalStaff;
                list = dao.getAllInvoicesByCus(search, pageIndex, pagesize);
                totalStaff = dao.getAllInvoicesByCus(search).size();
                int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                
                Pagination pagination = new Pagination();
                
                pagination.setUrlPattern( "/InvoiceController");
                pagination.setCurrentPage(pageIndex);
                pagination.setPageSize(pagesize);
                pagination.setTotalPages(totalPageCount);
                pagination.setTotalPagesToShow(5); // Hiển thị tối đa 5 trang liền kề
                pagination.setSort(column == null ? "" : column);
                pagination.setOrder(sortOrder == null ? "" : sortOrder);
                
                List<String> searchFields = new ArrayList<>();
                List<String> searchValues = new ArrayList<>();
                if(searchname != null && !searchname.isEmpty()){
                    searchFields.add("searchname");
                    searchValues.add(searchname);
                }
                if(search != null && !search.isEmpty()){
                    searchFields.add("search");
                    searchValues.add(search);
                }
                if(page_size != null && !page_size.isEmpty()){
                    searchFields.add("page_size");
                    searchValues.add(page_size);
                }
                pagination.setSearchFields(searchFields.toArray(new String[searchFields.size()]));
                pagination.setSearchValues(searchValues.toArray(new String[searchValues.size()]));

                
                // Gán các thuộc tính cần thiết cho JSP
                request.setAttribute("pagination", pagination);
                
                request.setAttribute("totalPageCount", totalPageCount);
                request.setAttribute("search", search);
                request.setAttribute("searchname", searchname);
                request.setAttribute("column", column);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("pageIndex", pageIndex);
                request.setAttribute("page_size", page_size);
                request.setAttribute("list", list);
                request.getRequestDispatcher("Invoice.jsp").forward(request, response);
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
        AdminDAO dao = new AdminDAO();
        List<Admin> list;
        List<Admin> list1;
        List<Admin> payment;
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAll";
        }
        
        switch(action){
            case "Infor":
                String cusID = request.getParameter("cusID");             
                list = dao.getAllInvoicesByID(cusID);
                request.setAttribute("info", list);
                request.setAttribute("cusID", cusID);
                request.getRequestDispatcher("InvoiceS_I.jsp").forward(request, response);
                break;
            case "Allinfor":
                String staffID = request.getParameter("staffID");
                cusID = request.getParameter("cusID");
                list = dao.getAllInvoicesByID(cusID);
                list1 = dao.getAllInvoicesInformationByID(staffID, cusID);
                request.setAttribute("info1", list);
                request.setAttribute("allinfo", list1);
                request.setAttribute("cusID", cusID);
                request.getRequestDispatcher("InvoiceS_I.jsp").forward(request, response);
                break;
            case "Allinforpayment":
                String invoiceID = request.getParameter("invoiceID");
                staffID = request.getParameter("staffID");
                cusID = request.getParameter("cusID");
                list = dao.getAllInvoicesByID(cusID);
                list1 = dao.getAllInvoicesInformationByID(staffID, cusID);
                payment = dao.getAllInformationByPayment(staffID, cusID, invoiceID);
                request.setAttribute("info1", list);
                request.setAttribute("allinfo", list1);
                request.setAttribute("cusID", cusID);
                request.setAttribute("payment", payment);
                request.getRequestDispatcher("InvoiceS_I.jsp").forward(request, response);
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

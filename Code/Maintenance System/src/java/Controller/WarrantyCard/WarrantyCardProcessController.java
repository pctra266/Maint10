/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.WarrantyCard;

import DAO.WarrantyCardProcessDAO;
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
import Model.WarrantyCardProcess;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="WarrantyCardProcessController", urlPatterns={"/WarrantyCardProcessController"})
public class WarrantyCardProcessController extends HttpServlet {
   
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
            out.println("<title>Servlet WarrantyCardProcessController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WarrantyCardProcessController at " + request.getContextPath () + "</h1>");
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
        if (action == null) {
            action = "viewAll";
        }
        WarrantyCardProcessDAO dao = new WarrantyCardProcessDAO();
        List<WarrantyCardProcess> list;
        List<Integer> warrantyID;
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
                
                int pagesize = 5; 

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
                list = dao.getAllProcessesOfCard(pageIndex, pagesize, column, sortOrder);
                totalStaff = dao.getAllProcessesOfCard(column, sortOrder).size();
                int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                
                Pagination pagination = new Pagination();
                
                pagination.setUrlPattern( "/WarrantyCardProcessController");
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
                warrantyID = dao.getAllWarrantyCardID();
                
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
                request.setAttribute("warrantyID", warrantyID);
                request.getRequestDispatcher("WarrantyProcess.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAll";
        }
        WarrantyCardProcessDAO dao = new WarrantyCardProcessDAO();
        List<WarrantyCardProcess> list;
        List<Integer> warrantyID;
        
        
        
        switch(action){
            case "Search":
                String search = request.getParameter("search");
                String searchname = request.getParameter("searchname");
                String column = request.getParameter("column");
                String sortOrder = request.getParameter("sortOrder");
                String page_size = request.getParameter("page_size");                               
                String name = "";
                if(searchname != null && !searchname.isEmpty()){
                    name = searchname.trim().replaceAll("\\s+", " ");
                }

                int pagesize = 5; 

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
                list = dao.getAllProcessesOfCard(pageIndex, pagesize, column, sortOrder);
                totalStaff = dao.getAllProcessesOfCard(column, sortOrder).size();
                int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);

                Pagination pagination = new Pagination();

                pagination.setUrlPattern( "/WarrantyCardProcessController");
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
                warrantyID = dao.getAllWarrantyCardID();

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
                request.setAttribute("warrantyID", warrantyID);
                String id = request.getParameter("warrantyCardID");
                int number = 0; 
                
                number = Integer.parseInt(id);
                    

                List<WarrantyCardProcess> list1 ;
                list1 = dao.getAllProcessesOfCard(number);
                request.setAttribute("list1", list1);
                PrintWriter o = response.getWriter();
                o.print(id);
                request.getRequestDispatcher("WarrantyProcess.jsp").forward(request, response);
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

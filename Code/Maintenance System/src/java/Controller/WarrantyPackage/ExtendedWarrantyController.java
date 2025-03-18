/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.WarrantyPackage;

import DAO.ExtendedWarrantyDAO;
import Model.ExtendedWarranty;
import Model.Pagination;
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
     private final int PAGE_SIZE = 5;
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
//         String action = request.getParameter("action");
//        
//        if(action == null || action.trim().isEmpty()){
//            ArrayList<ExtendedWarranty> list = ewDao.getListExtendedWarranty();
//            request.setAttribute("extendedWarranties", list);
//            request.getRequestDispatcher("extendedWarrantyList.jsp").forward(request, response);
//        } else if(action.equals("new")){
//            request.getRequestDispatcher("addExtendedWarranty.jsp").forward(request, response);
//        } else if(action.equals("edit")){
//            String id = request.getParameter("extendedWarrantyID");
//            ExtendedWarranty ew = ewDao.getExtendedWarrantyByID(id);
//            request.setAttribute("extendedWarranty", ew);
//            request.getRequestDispatcher("editExtendedWarranty.jsp").forward(request, response);
//        } else if(action.equals("delete")){
//            String id = request.getParameter("extendedWarrantyID");
//            boolean deleted = ewDao.deleteExtendedWarranty(id);
//            String message = deleted ? "Extended warranty deleted successfully." : "Delete failed.";
//            request.setAttribute("message", message);
//            ArrayList<ExtendedWarranty> list = ewDao.getListExtendedWarranty();
//            request.setAttribute("extendedWarranties", list);
//            request.getRequestDispatcher("extendedWarrantyList.jsp").forward(request, response);
//        }
        String action = request.getParameter("action");
        if(action == null || action.trim().isEmpty()){
            action = "view";
        }
        switch(action) {
            case "view":
                viewAction(request, response);
                break;
            case "new":
                newAction(request, response);
                break;
            case "edit":
                editAction(request, response);
                break;
            case "delete":
                deleteAction(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/ExtendedWarrantyController?action=view");
                break;
        }
        
        
    }
    // Xử lý action view với paging
    protected void viewAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchExtendedWarrantyName  = request.getParameter("searchExtendedWarrantyName");
        request.setAttribute("searchExtendedWarrantyName",searchExtendedWarrantyName);
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("page-size");
        int page = (pageParam != null && !pageParam.trim().isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int pageSize = (pageSizeParam != null && !pageSizeParam.trim().isEmpty()) ? Integer.parseInt(pageSizeParam) : PAGE_SIZE;
        
        int total = ewDao.totalExtendedWarranty(searchExtendedWarrantyName);
        int totalPages = (int) Math.ceil((double) total / pageSize);
        if(page > totalPages) page = totalPages;
        if(page < 1) page = 1;
        
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page);
        pagination.setPageSize(pageSize);
        pagination.setTotalPages(totalPages);
        pagination.setTotalPagesToShow(5);
        pagination.setUrlPattern("/ExtendedWarrantyController");
        pagination.setSort(request.getParameter("sort"));
        pagination.setOrder(request.getParameter("order"));
   
        pagination.setSearchFields(new String[]{"searchExtendedWarrantyName"});
        pagination.setSearchValues(new String[]{
            searchExtendedWarrantyName 
            
        });
        
        ArrayList<ExtendedWarranty> list = ewDao.getListExtendedWarranty(searchExtendedWarrantyName,page, pageSize);
        request.setAttribute("extendedWarranties", list);
        request.setAttribute("pagination", pagination);
        request.getRequestDispatcher("extendedWarrantyList.jsp").forward(request, response);
    }
    
    // Chuyển sang trang thêm mới
    protected void newAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addExtendedWarranty.jsp").forward(request, response);
    }
    
    // Xử lý trang chỉnh sửa
    protected void editAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("extendedWarrantyID");
        ExtendedWarranty ew = ewDao.getExtendedWarrantyByID(id);
        request.setAttribute("extendedWarranty", ew);
        request.getRequestDispatcher("editExtendedWarranty.jsp").forward(request, response);
    }
    
    // Xử lý xóa mềm
    protected void deleteAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("extendedWarrantyID");
        boolean deleted = ewDao.deleteExtendedWarranty(id);
        String message = deleted ? "Extended warranty deleted successfully." : "Delete failed.";
        request.setAttribute("message", message);
        // Sau khi xóa, load lại danh sách với paging
        viewAction(request, response);
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
//        
//        String action = request.getParameter("action");
//        if(action.equals("new")){
//            ExtendedWarranty ew = new ExtendedWarranty();
//            ew.setExtendedWarrantyName(request.getParameter("extendedWarrantyName"));
//            ew.setExtendedPeriodInMonths(Integer.parseInt(request.getParameter("extendedPeriodInMonths")));
//            ew.setPrice(Double.parseDouble(request.getParameter("price")));
//            ew.setExtendedWarrantyDescription(request.getParameter("extendedWarrantyDescription"));
//            boolean created = ewDao.createExtendedWarranty(ew);
//            String message = created ? "Extended warranty created successfully." : "Creation failed.";
//            request.setAttribute("message", message);
//        } else if(action.equals("edit")){
//            ExtendedWarranty ew = new ExtendedWarranty();
//            ew.setExtendedWarrantyID(Integer.parseInt(request.getParameter("extendedWarrantyID")));
//            ew.setExtendedWarrantyName(request.getParameter("extendedWarrantyName"));
//            ew.setExtendedPeriodInMonths(Integer.parseInt(request.getParameter("extendedPeriodInMonths")));
//            ew.setPrice(Double.parseDouble(request.getParameter("price")));
//            ew.setExtendedWarrantyDescription(request.getParameter("extendedWarrantyDescription"));
//            boolean updated = ewDao.updateExtendedWarranty(ew);
//            String message = updated ? "Extended warranty updated successfully." : "Update failed.";
//            request.setAttribute("message", message);
//        }
//        
//        ArrayList<ExtendedWarranty> list = ewDao.getListExtendedWarranty();
//        request.setAttribute("extendedWarranties", list);
//        request.getRequestDispatcher("extendedWarrantyList.jsp").forward(request, response);

        // Xử lý POST cho các thao tác new, edit
        String action = request.getParameter("action");
        if(action.equals("new")){
            createAction(request, response);
        } else if(action.equals("edit")){
            updateAction(request, response);
        } else {
            doGet(request, response);
        }
    }
    
    // Xử lý tạo mới ExtendedWarranty (POST)
    protected void createAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExtendedWarranty ew = new ExtendedWarranty();
        ew.setExtendedWarrantyName(request.getParameter("extendedWarrantyName"));
        ew.setExtendedPeriodInMonths(Integer.parseInt(request.getParameter("extendedPeriodInMonths")));
        ew.setPrice(Double.parseDouble(request.getParameter("price")));
        ew.setExtendedWarrantyDescription(request.getParameter("extendedWarrantyDescription"));
        boolean created = ewDao.createExtendedWarranty(ew);
        String message = created ? "Extended warranty created successfully." : "Creation failed.";
        request.setAttribute("message", message);
        viewAction(request, response);
    }
    
    // Xử lý cập nhật ExtendedWarranty (POST)
    protected void updateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExtendedWarranty ew = new ExtendedWarranty();
        ew.setExtendedWarrantyID(Integer.parseInt(request.getParameter("extendedWarrantyID")));
        ew.setExtendedWarrantyName(request.getParameter("extendedWarrantyName"));
        ew.setExtendedPeriodInMonths(Integer.parseInt(request.getParameter("extendedPeriodInMonths")));
        ew.setPrice(Double.parseDouble(request.getParameter("price")));
        ew.setExtendedWarrantyDescription(request.getParameter("extendedWarrantyDescription"));
        boolean updated = ewDao.updateExtendedWarranty(ew);
        String message = updated ? "Extended warranty updated successfully." : "Update failed.";
        request.setAttribute("message", message);
        viewAction(request, response);
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

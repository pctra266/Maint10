/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.ComponentRequest;

import DAO.ComponentRequestResponsibleDAO;
import Model.ComponentRequestResponsible;
import Utils.Pagination;
import Utils.FormatUtils;
import Utils.SearchUtils;
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
@WebServlet(name="componentRequestResponsible", urlPatterns={"/componentRequestResponsible"})
public class componentRequestResponsible extends HttpServlet {
    private static final int PAGE_SIZE = 5;
    private static final ComponentRequestResponsibleDAO daoCRResponsible = new ComponentRequestResponsibleDAO();
   
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
            out.println("<title>Servlet componentRequestResponsible</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet componentRequestResponsible at " + request.getContextPath () + "</h1>");
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
        
//        String , String , 
//         String , String , String , int page, int pageSize
        //parameter
        String sort = request.getParameter("sort");
        request.setAttribute("sort", sort);
        String order = request.getParameter("order");
        request.setAttribute("order", order);
        String componentRequestId = request.getParameter("componentRequestId");
        request.setAttribute("componentRequestId", componentRequestId);
        String staffName = SearchUtils.preprocessSearchQuery(request.getParameter("staffName"));
        request.setAttribute("staffName", staffName);
        String staffPhone = SearchUtils.searchValidateNonSapce(request.getParameter("staffPhone"));
        request.setAttribute("staffPhone", staffPhone);
        String staffEmail = SearchUtils.searchValidateNonSapce(request.getParameter("staffEmail"));
        request.setAttribute("staffEmail", staffEmail);
        String componentRequestAction = request.getParameter("componentRequestAction");
        request.setAttribute("componentRequestAction", componentRequestAction);
        
    
        if(action == null){
            action = "viewComponentRequestResponsible";
        }
        int total = 0;
        switch(action){
            case "viewComponentRequestResponsible":
                total = daoCRResponsible.totalComponentRequestLog(componentRequestId, staffName, staffPhone, staffEmail, componentRequestAction);
                break;
        }
        System.out.println("Total la : " + total);
        //paging
        Pagination pagination = new Pagination();
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("page-size");
        int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
        Integer pageSize;
        pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
        int totalPages = (int) Math.ceil((double) total / pageSize);
        if (page > totalPages) {
            page = totalPages;
        }
        page = page < 1 ? 1 : page;
                pagination.setListPageSize(total); 
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages);
                pagination.setTotalPagesToShow(5); 
                pagination.setPageSize(pageSize);
                pagination.setSort(sort);
                pagination.setOrder(order);
                pagination.setUrlPattern("/componentRequestResponsible");
        switch(action){
                case "viewComponentRequestResponsible":
                //======phan trang
                pagination.setSearchFields(new String[]{"action","componentRequestId","staffName","staffPhone","staffEmail","componentRequestAction"});
                pagination.setSearchValues(new String[]{"viewComponentRequestResponsible",componentRequestId,staffName,staffPhone,staffEmail,componentRequestAction});
                request.setAttribute("pagination", pagination);
                //======end phan trang
                ArrayList<ComponentRequestResponsible> listComponentResquestResponsible = new ArrayList<>();
                listComponentResquestResponsible = daoCRResponsible.getAllComponentRequestResponsible(componentRequestId, staffName, staffPhone, staffEmail, componentRequestAction,page,pageSize);
                request.setAttribute("listComponentResquestResponsible", listComponentResquestResponsible);
                request.getRequestDispatcher("componentRequestResponsible.jsp").forward(request, response);
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

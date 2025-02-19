/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.ComponentRequest;

import DAO.ComponentRequestDAO;
import Model.Pagination;
import Model.ProductDetail;
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
@WebServlet(name="ComponentRequestController", urlPatterns={"/componentRequest"})
public class ComponentRequestController extends HttpServlet {
    private static final int PAGE_SIZE = 5;
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
            out.println("<title>Servlet ComponentRequestController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ComponentRequestController at " + request.getContextPath () + "</h1>");
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
        //dao
        ComponentRequestDAO componentRequestDao = new ComponentRequestDAO();
        
        //action
        String action = request.getParameter("action");
        if(action == null){
            action = "viewComponentRequestDashboard";
        }
//        String warrantyCardCode, String productCode,
//          String unknownProductCode,  String warrantyStatus,String typeMaintain, String sort, 
//          String order, int page, int pageSize
        //parameter
        String warrantyCardCode = SearchUtils.searchValidateNonSapce(request.getParameter("warrantyCardCode"));
        String productCode = SearchUtils.searchValidateNonSapce(request.getParameter("productCode"));
        String unknownProductCode = SearchUtils.searchValidateNonSapce(request.getParameter("unknownProductCode"));
        String warrantyStatus = request.getParameter("warrantyStatus");
        if(warrantyStatus== null || warrantyStatus.trim().isEmpty()){
            warrantyStatus = "fixing";
        }
        if(warrantyStatus.equalsIgnoreCase("all")){
            warrantyStatus = "";
        }
        String typeMaintain = request.getParameter("typeMaintain");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        request.setAttribute("warrantyCardCode", warrantyCardCode);
        request.setAttribute("productCode", productCode);
        request.setAttribute("unknownProductCode", unknownProductCode);
        request.setAttribute("warrantyStatus", warrantyStatus);
        request.setAttribute("typeMaintain", typeMaintain);
        request.setAttribute("sort", sort);
        request.setAttribute("order", order);
        int total = 0;
        switch(action){
            case "viewComponentRequestDashboard":
                total = componentRequestDao.totalProductUnderMaintain(warrantyCardCode, productCode, unknownProductCode, warrantyStatus, typeMaintain);
                break;
        }
        System.out.println("total: "+ total);
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
        
        //do
        switch(action){
            case "viewComponentRequestDashboard":
                //======phan trang
                pagination.setListPageSize(total); 
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages);
                pagination.setTotalPagesToShow(5); 
                pagination.setPageSize(pageSize);
                pagination.setSort(sort);
                pagination.setOrder(order);
                pagination.setUrlPattern("/componentRequest");
                pagination.setSearchFields(new String[]{"action","productCode","unknownProductCode","warrantyStatus","typeMaintain"});
                pagination.setSearchValues(new String[]{"viewComponentRequestDashboard",productCode,unknownProductCode,warrantyStatus,typeMaintain});
                request.setAttribute("pagination", pagination);
                //======end phan trang
                ArrayList<ProductDetail> listProductUnderMaintain = componentRequestDao.getAllListProductUnderMaintain(warrantyCardCode, productCode, 
                unknownProductCode, warrantyStatus, typeMaintain, sort, order, page, pageSize);
                request.setAttribute("listProductUnderMaintain", listProductUnderMaintain);
                request.getRequestDispatcher("requestComponentDashboard.jsp").forward(request, response);
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

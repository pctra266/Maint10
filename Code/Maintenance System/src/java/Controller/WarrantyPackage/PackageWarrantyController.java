/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.WarrantyPackage;

import DAO.ExtendedWarrantyDAO;
import DAO.PackageWarrantyDAO;
import Model.PackageWarranty;
import Model.Pagination;
import Utils.FormatUtils;
import Utils.SearchUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="PackageWarrantyController", urlPatterns={"/packageWarranty"})
public class PackageWarrantyController extends HttpServlet {
    private final int PAGE_SIZE = 5;
   private final PackageWarrantyDAO pkgDao = new PackageWarrantyDAO();
   private final ExtendedWarrantyDAO extDao = new ExtendedWarrantyDAO();
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
            out.println("<title>Servlet PackageWarrantyController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PackageWarrantyController at " + request.getContextPath () + "</h1>");
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
        
        if(action == null || action.trim().isEmpty() || "view".equalsIgnoreCase(action)){
            // param paging
        String sort = request.getParameter("sort");
        request.setAttribute("sort", sort);
        String order = request.getParameter("order");
        request.setAttribute("order", order);
        
        // param
        String searchProductCode = SearchUtils.searchValidateNonSapce(request.getParameter("searchProductCode"));
        request.setAttribute("searchProductCode", searchProductCode);
        String searchCustomerName = SearchUtils.preprocessSearchQuery(request.getParameter("searchCustomerName"));
        request.setAttribute("searchCustomerName", searchCustomerName);
        String searchEmail = SearchUtils.searchValidateNonSapce(request.getParameter("searchEmail"));
        request.setAttribute("searchEmail", searchEmail);
        String searchProductName = SearchUtils.searchValidateNonSapce(request.getParameter("searchProductName"));
        request.setAttribute("searchProductName", searchProductName);
        String filterStatusPackage = request.getParameter("filterStatusPackage");
        request.setAttribute("filterStatusPackage", filterStatusPackage);
            System.out.println("filter: " + filterStatusPackage);
        // Paging
        int total = pkgDao.totalPackageWarranty(searchProductCode, searchCustomerName, searchEmail, searchProductName, filterStatusPackage);
        request.setAttribute("sort", sort);
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
        
        // phan trang
        Pagination pagination = new Pagination();
        
        pagination.setListPageSize(total); 
        pagination.setCurrentPage(page);
        pagination.setTotalPages(totalPages);
        pagination.setTotalPagesToShow(5); 
        pagination.setPageSize(pageSize);
        pagination.setSort(sort);
        pagination.setOrder(order);
        pagination.setUrlPattern("/packageWarranty");
        // take list
        pagination.setSearchFields(new String[]{"searchProductCode", "searchCustomerName", "searchEmail", "searchProductName","filterStatusPackage"});
        pagination.setSearchValues(new String[]{searchProductCode, searchCustomerName, searchEmail, searchProductName, filterStatusPackage});
        

        ArrayList<PackageWarranty> list = pkgDao.getAllPackageWarranties(searchProductCode, searchCustomerName, searchEmail, searchProductName, filterStatusPackage, sort, order, page, pageSize);
        request.setAttribute("packageWarrantyList", list);
        request.setAttribute("pagination", pagination);
        
        request.getRequestDispatcher("packageWarrantyList.jsp").forward(request, response);
        } else if(action.equals("edit")){
            // Lấy thông tin một PackageWarranty theo ID để chỉnh sửa
            String id = request.getParameter("packageWarrantyID");
            PackageWarranty pkg = pkgDao.getPackageWarrantyByID(id);
            request.setAttribute("packageWarranty", pkg);
            request.setAttribute("extendedWarrantyDetailList", extDao.getExtendedWarrantyDetailList(id));
            request.setAttribute("extendedWarrantyList", extDao.getListExtendedWarranty());
            request.getRequestDispatcher("editPackageWarranty.jsp").forward(request, response);
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
        // Xử lý cập nhật PackageWarranty
        String action = request.getParameter("action");
        if(action.equals("edit")){
            PackageWarranty pkg = new PackageWarranty();
            pkg.setPackageWarrantyID(Integer.parseInt(request.getParameter("packageWarrantyID")));
            // Các trường read-only như productCode, customerName, email, productName, extendedWarrantyName không cần cập nhật
            
            // Định dạng ngày (HTML input type="date" gửi về dạng yyyy-MM-dd)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                pkg.setWarrantyStartDate(sdf.parse(request.getParameter("warrantyStartDate")));
                pkg.setWarrantyEndDate(sdf.parse(request.getParameter("warrantyEndDate")));
                pkg.setStartExtendedWarranty(sdf.parse(request.getParameter("startExtendedWarranty")));
                pkg.setEndExtendedWarranty(sdf.parse(request.getParameter("endExtendedWarranty")));
            } catch(Exception e) {
                e.printStackTrace();
            }
            pkg.setNote(request.getParameter("note"));
            pkg.setActive(Boolean.parseBoolean(request.getParameter("isActive")));
            
            boolean updated0 = pkgDao.updateActive(pkg);
            boolean updated1 = pkgDao.updateDefaultWarrantyPackage(pkg);
            boolean updated2 = pkgDao.updateExtendedWarrantyDetail(pkg);
            String message = updated0 ? "Package warranty updated successfully." : "Update failed.";
            message = updated1 ? "Package warranty updated successfully." : "Update failed.";
            message = updated2 ? "Package warranty updated successfully." : "Update failed.";
            request.setAttribute("message", message);
        }
        // Sau khi xử lý POST, lấy lại danh sách và forward về danh sách
//        ArrayList<PackageWarranty> list = pkgDao.getListPackageWarranty();
//        request.setAttribute("packageWarranties", list);
        request.getRequestDispatcher("packageWarrantyList.jsp").forward(request, response);
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

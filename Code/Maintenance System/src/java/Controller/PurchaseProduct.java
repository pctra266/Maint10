/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ProductDAO;
import DAO.ProductDetailDAO;
import Model.Customer;
import Utils.Pagination;
import Model.ProductDetail;
import Utils.FormatUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class PurchaseProduct extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet PurchaseProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PurchaseProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session = request.getSession();
        //truyen tham so cho nut back
        session.setAttribute("createWarrantyCardFrom", request.getContextPath()+request.getServletPath());

        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            response.sendRedirect("HomePage.jsp");
            return;
        }
        String productCode = request.getParameter("productCode");
        String code = request.getParameter("code");
        String purchaseDate = request.getParameter("purchaseDate");
        String productName = request.getParameter("productName");
        String sortBy = request.getParameter("sort");
        String sortOrder = request.getParameter("order");
        String pageSizeParam = request.getParameter("page-size");
        String pageParam = request.getParameter("page");

        int warrantyPeriod = 0;
        String warrantyParam = request.getParameter("warrantyPeriod");
        if (warrantyParam != null && !warrantyParam.isEmpty()) {
            try {
                warrantyPeriod = Integer.parseInt(warrantyParam);
            } catch (NumberFormatException e) {
                System.out.println("Invalid warrantyPeriod value: " + warrantyParam);
            }
        }

        int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
        Integer pageSize;
        pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : 5;
        int offset = offset = (page - 1) * pageSize;

        int totalPurchaseProduct = 0;
        int totalPages = 1;

        ProductDetailDAO productDetailDao = new ProductDetailDAO();

        ArrayList<ProductDetail> listPurchaseProduct = productDetailDao.getProductDetailByCustomerID(
                customer.getCustomerID(), productCode, code, purchaseDate, productName, warrantyPeriod, sortBy, sortOrder, offset, pageSize);

        totalPurchaseProduct = productDetailDao.getProductDetailByCustomerIDItems(
                customer.getCustomerID(), productCode, code, purchaseDate, productName, warrantyPeriod);
        totalPages = (int) Math.ceil((double) totalPurchaseProduct / pageSize);

        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(page);
        pagination.setSearchFields(new String[]{"productCode", "code", "purchaseDate", "productName"});
        pagination.setSearchValues(new String[]{productCode,code,purchaseDate,productName});
        pagination.setSort(sortBy);
        pagination.setOrder(sortOrder);
        pagination.setTotalPagesToShow(5);
        pagination.setTotalPages(totalPages);
        pagination.setUrlPattern("/purchaseproduct");
        pagination.setListPageSize(totalPurchaseProduct);
        request.setAttribute("pagination", pagination);

        // Get Atribute
        request.setAttribute("listPurchaseProduct", listPurchaseProduct);
        request.setAttribute("productCode", productCode);
        request.setAttribute("code", code);

        request.setAttribute("purchaseDate", purchaseDate);
        request.setAttribute("productName", productName);
        request.setAttribute("warrantyPeriod", warrantyPeriod);
        //

        request.setAttribute("size", pageSize);

        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("PurchaseProduct.jsp").forward(request, response);

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

}

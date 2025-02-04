/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Product;

import DAO.ProductDAO;
import Model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author sonNH
 */
public class ViewProduct extends HttpServlet {

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
            out.println("<title>Servlet ViewProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>hello ViewProduct at " + request.getContextPath() + "</h1>");
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
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList;
        productList = productDAO.getAllProducts();
//        int pageSize = 5;
//        int pageIndex = 1;
//        String pageIndexStr = request.getParameter("index");
//
//        if (pageIndexStr != null) {
//            try {
//                pageIndex = Integer.parseInt(pageIndexStr);
//            } catch (NumberFormatException e) {
//                pageIndex = 1;
//            }
//        }
//
//        List<Product> productList;
//        String keyword = request.getParameter("keyword");
//        String quantityStr = request.getParameter("quantity");
//        String warrantyStr = request.getParameter("warrantyPeriod");
//
//        try {
//            boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
//            boolean hasQuantity = quantityStr != null && !quantityStr.trim().isEmpty();
//            boolean hasWarranty = warrantyStr != null && !warrantyStr.trim().isEmpty();
//
//            int totalProductCount;
//            if (hasKeyword || hasQuantity || hasWarranty) {
//                Integer quantity = hasQuantity ? Integer.parseInt(quantityStr) : null;
//                Integer warrantyPeriod = hasWarranty ? Integer.parseInt(warrantyStr) : null;
//
//                productList = productDAO.searchProductsWithPagination(keyword, quantity, warrantyPeriod, pageIndex, pageSize);
//
//                totalProductCount = productDAO.searchProducts(keyword, quantity, warrantyPeriod).size();
//            } else {
//                productList = productDAO.getProductsByPage(pageIndex, pageSize);
//                totalProductCount = productDAO.getTotalProductCount();
//            }
//
//            int totalPageCount = (int) Math.ceil((double) totalProductCount / pageSize);
//
        request.setAttribute("productList", productList);
//            request.setAttribute("keyword", keyword);
//            request.setAttribute("quantity", quantityStr);
//            request.setAttribute("warrantyPeriod", warrantyStr);
//            request.setAttribute("totalPageCount", totalPageCount);
//            request.setAttribute("currentPage", pageIndex);
        request.getRequestDispatcher("/Product/product.jsp").forward(request, response);
//        } catch (Exception e) {
//        }
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

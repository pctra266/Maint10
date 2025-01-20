/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Component;

import DAO.ComponentDAO;
import Model.Component;
import Utils.NumberUtils;
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
@WebServlet(name = "ComponentWarehouse", urlPatterns = {"/ComponentWarehouse"})
public class ComponentWarehouse extends HttpServlet {

    private final ComponentDAO componentDAO = new ComponentDAO();
    private static final int PAGE_SIZE = 5;

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
        String pageParam = request.getParameter("page");
        String paraSearch = request.getParameter("search");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        // Lấy page-size từ request, mặc định là PAGE_SIZE
        String pageSizeParam = request.getParameter("page-size");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        Integer pageSize;
        pageSize = (NumberUtils.tryParseInt(pageSizeParam) != null) ? NumberUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
        List<Component> components = new ArrayList<>();
        if (order != null && sort != null & (order.equals("asc") || order.equals("desc"))) {
            if (sort.equals("quantity") || sort.equals("name") || sort.equals("price")) {
                String sortSQL;
                switch (sort) {
                    case "quantity":
                        sortSQL = "Quantity";
                        break;

                    case "name":
                        sortSQL = "ComponentName";
                        break;
                    default:
                        sortSQL = "Price";
                        break;
                }
                components = paraSearch == null || paraSearch.isBlank() ? componentDAO.getComponentsByPageSorted(page, pageSize, sortSQL, order)
                        : componentDAO.searchComponentsByPageSorted(paraSearch, page, pageSize, sortSQL, order);
            }
        } else {
            components = paraSearch == null || paraSearch.isBlank() ? componentDAO.getComponentsByPage(page, pageSize) : componentDAO.searchComponentsByPage(paraSearch, page, pageSize);
        }
        int totalComponents = components.size();
        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalComponents / pageSize);
        if (page > totalPages) {
            page = totalPages;
        }
        if (page < 1) {
            page = 1;
        }
        // Lấy danh sách Component cho trang hiện tại

        // Đặt các thuộc tính cho request
        request.setAttribute("search", paraSearch);
        request.setAttribute("totalPagesToShow", 5);
        request.setAttribute("size", pageSize);
        request.setAttribute("components", components);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("sort", sort);
        request.setAttribute("order", order);
        // Chuyển tiếp đến trang JSP để hiển thị
        request.getRequestDispatcher("Component/ComponentWarehouse.jsp").forward(request, response);
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
        processRequest(request, response);
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

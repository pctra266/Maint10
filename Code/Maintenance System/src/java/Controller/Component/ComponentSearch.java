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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import Utils.SearchUtils;


/**
 *
 * @author ADMIN
 */
public class ComponentSearch extends HttpServlet {

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
        String paraSearchCode = SearchUtils.preprocessSearchQuery( request.getParameter("searchCode"));
        String paraSearchName = SearchUtils.preprocessSearchQuery(request.getParameter("searchName"));
        int page = (NumberUtils.tryParseInt(pageParam) != null) ? NumberUtils.tryParseInt(pageParam) : 1;
        // Lấy page-size từ request, mặc định là PAGE_SIZE
        String pageSizeParam = request.getParameter("page-size");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String type = request.getParameter("searchType");
        Integer typeID = componentDAO.getTypeID(type);
        String brand = request.getParameter("searchBrand");
        Integer brandID = componentDAO.getBrandID(brand);
        Integer pageSize;
        pageSize = (NumberUtils.tryParseInt(pageSizeParam) != null) ? NumberUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
        String paraSearchQuantityMin = request.getParameter("searchQuantityMin");
        Integer searchQuantityMin = NumberUtils.tryParseInt(paraSearchQuantityMin) != null ? NumberUtils.tryParseInt(paraSearchQuantityMin) : componentDAO.getQuantityMin();
        String paraSearchQuantityMax = request.getParameter("searchQuantityMax");
        Integer searchQuantityMax = NumberUtils.tryParseInt(paraSearchQuantityMax) != null ? NumberUtils.tryParseInt(paraSearchQuantityMax) : componentDAO.getQuantityMax();
        String paraSearchPriceMin = request.getParameter("searchPriceMin");
        Double searchPriceMin = NumberUtils.tryParseDouble(paraSearchPriceMin) != null ? NumberUtils.tryParseDouble(paraSearchPriceMin) : componentDAO.getPriceMin();
        String paraSearchPriceMax = request.getParameter("searchPriceMax");
        Double searchPriceMax = NumberUtils.tryParseDouble(paraSearchPriceMax) != null ? NumberUtils.tryParseDouble(paraSearchPriceMax) : componentDAO.getPriceMax();
        List<Component> components = new ArrayList<>();
        int totalComponents = componentDAO.getTotalSearchComponentsByFields(paraSearchCode, paraSearchName, typeID, brandID, searchQuantityMin, searchQuantityMax, searchPriceMin, searchPriceMax);
        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalComponents / pageSize);
        if (page > totalPages) {
            page = totalPages;
        }
        if (page < 1) {
            page = 1;
        }
        if (order != null && sort != null && (order.equals("asc") || order.equals("desc"))) {
            //xac nhan cac tham so de sort truyen vao la dung
            if (sort.equals("quantity") || sort.equals("name") || sort.equals("price") || sort.equals("code")) {
                String sortSQL;
                sortSQL = switch (sort) {
                    case "quantity" ->
                        "Quantity";
                    case "name" ->
                        "ComponentName";
                    case "code" ->
                        "ComponentCode";
                    default ->
                        "Price";
                };
                components = componentDAO.searchComponentsByFieldsPageSorted(paraSearchCode, paraSearchName, page, pageSize, sortSQL, order, typeID, brandID, searchQuantityMin, searchQuantityMax, searchPriceMin, searchPriceMax);
            } else {
                
                components = componentDAO.searchComponentsByFieldsPage(paraSearchCode, paraSearchName, page, pageSize, typeID, brandID, searchQuantityMin, searchQuantityMax, searchPriceMin, searchPriceMax);
            }
        } else {
            
            components = componentDAO.searchComponentsByFieldsPage(paraSearchCode, paraSearchName, page, pageSize, typeID, brandID, searchQuantityMin, searchQuantityMax, searchPriceMin, searchPriceMax);
        }
        String delete = request.getParameter("delete");
        String deleteStatus;
        if (delete != null) {
            deleteStatus = delete.equals("1") ? "Success to delete" : "Fail to delete";
            request.setAttribute("deleteStatus", deleteStatus);
        }
        // Đặt các thuộc tính cho request
        request.setAttribute("searchQuantityMin", searchQuantityMin);
        request.setAttribute("searchQuantityMax", searchQuantityMax);
        request.setAttribute("quantityMin", componentDAO.getQuantityMin());
        request.setAttribute("quantityMax", componentDAO.getQuantityMax());
        request.setAttribute("searchPriceMin", searchPriceMin);
        request.setAttribute("searchPriceMax", searchPriceMax);
        request.setAttribute("priceMin", componentDAO.getPriceMin());
        request.setAttribute("priceMax", componentDAO.getPriceMax());
        request.setAttribute("totalComponents", totalComponents);
        request.setAttribute("typeList", componentDAO.getListType());
        request.setAttribute("brandList", componentDAO.getListBrand());
        request.setAttribute("searchName", paraSearchName);
        request.setAttribute("searchCode", paraSearchCode);
        request.setAttribute("totalPagesToShow", 5);
        request.setAttribute("size", pageSize);
        request.setAttribute("components", components);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("sort", sort);
        request.setAttribute("order", order);
        request.setAttribute("searchType", type);
        request.setAttribute("searchBrand", brand);
        // Chuyển tiếp đến trang JSP để hiển thị
        request.getRequestDispatcher("/views/Component/ComponentSearch.jsp").forward(request, response);

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

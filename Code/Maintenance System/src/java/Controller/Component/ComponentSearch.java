/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Component;

import DAO.ComponentDAO;
import Model.Component;
import Model.Pagination;
import Utils.FormatUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import Utils.SearchUtils;
import jakarta.servlet.http.HttpSession;


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
        //Xu ly nut back quay ve
        HttpSession session = request.getSession();
        if(session.getAttribute("detailComponentFrom")!=null) session.removeAttribute("from");
        //
        String pageParam = request.getParameter("page");
        String paraSearchCode = SearchUtils.preprocessSearchQuery( request.getParameter("searchCode"));
        String paraSearchName = SearchUtils.preprocessSearchQuery(request.getParameter("searchName"));
        int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
        // Lấy page-size từ request, mặc định là PAGE_SIZE
        String pageSizeParam = request.getParameter("page-size");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String type = request.getParameter("searchType");
        Integer typeID = componentDAO.getTypeID(type);
        String brand = request.getParameter("searchBrand");
        Integer brandID = componentDAO.getBrandID(brand);
        Integer pageSize;
        pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
        String paraSearchQuantityMin = request.getParameter("searchQuantityMin");
        Integer searchQuantityMin = (FormatUtils.tryParseInt(paraSearchQuantityMin) != null&&FormatUtils.tryParseInt(paraSearchQuantityMin)<componentDAO.getQuantityMax()) ? FormatUtils.tryParseInt(paraSearchQuantityMin) : 0;
        String paraSearchQuantityMax = request.getParameter("searchQuantityMax");
        Integer searchQuantityMax = (FormatUtils.tryParseInt(paraSearchQuantityMax) != null && FormatUtils.tryParseInt(paraSearchQuantityMax)<componentDAO.getQuantityMax() )? FormatUtils.tryParseInt(paraSearchQuantityMax) : componentDAO.getQuantityMax();
        String paraSearchPriceMin = request.getParameter("searchPriceMin");
        Double searchPriceMin = (FormatUtils.tryParseDouble(paraSearchPriceMin) != null&&FormatUtils.tryParseDouble(paraSearchPriceMin) <componentDAO.getPriceMax()) ? FormatUtils.tryParseDouble(paraSearchPriceMin) : 0;
        String paraSearchPriceMax = request.getParameter("searchPriceMax");
        Double searchPriceMax = (FormatUtils.tryParseDouble(paraSearchPriceMax) != null && FormatUtils.tryParseDouble(paraSearchPriceMax)<componentDAO.getPriceMax()) ? FormatUtils.tryParseDouble(paraSearchPriceMax) : componentDAO.getPriceMax();
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
                //Phan trang
         Pagination pagination = new Pagination();
        pagination.setListPageSize(totalComponents);
        pagination.setCurrentPage(page);
        pagination.setTotalPages(totalPages);
        pagination.setTotalPagesToShow(5);
        pagination.setPageSize(pageSize);
        pagination.setSort(sort);
        pagination.setOrder(order);
        pagination.setUrlPattern("/ComponentWarehouse/Search");
        pagination.setSearchFields(new String[] {"searchName","searchCode","searchType","searchBrand"});
        pagination.setSearchValues(new String[] {paraSearchName, paraSearchCode, type, brand});
        pagination.setRangeFields(new String[] {"searchPriceMin","searchPriceMax","searchQuantityMin","searchQuantityMax"});
        pagination.setRangeValues(new Object[]{searchPriceMin, searchPriceMax, searchQuantityMin, searchQuantityMax});
        request.setAttribute("pagination", pagination);
        
        // Đặt các thuộc tính cho request
     
        request.setAttribute("quantityMin", componentDAO.getQuantityMin());
        request.setAttribute("quantityMax", componentDAO.getQuantityMax());
        request.setAttribute("priceMin", componentDAO.getPriceMin());
        request.setAttribute("priceMax", componentDAO.getPriceMax());
        request.setAttribute("totalComponents", totalComponents);
        request.setAttribute("typeList", componentDAO.getListType());
        request.setAttribute("brandList", componentDAO.getListBrand());
        request.setAttribute("components", components);
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

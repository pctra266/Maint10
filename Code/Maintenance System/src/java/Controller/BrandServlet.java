/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.BrandDAO;
import Model.Brand;
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
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "BrandServlet", urlPatterns = {"/Brand"})
public class BrandServlet extends HttpServlet {

    private final BrandDAO brandDAO = new BrandDAO();
    private static final int PAGE_SIZE = 10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String pageParam = request.getParameter("page");
        int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
        String pageSizeParam = request.getParameter("page-size");
        int pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
        String search = SearchUtils.preprocessSearchQuery(request.getParameter("search"));
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        if ("delete".equals(action)) {
            int brandID = Integer.parseInt(request.getParameter("brandID"));
            boolean success = brandDAO.deleteBrand(brandID);
            if (success) {
                request.setAttribute("successMessage", "Brand deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete brand. It may be in use.");
            }
        }

        if ("add".equals(action) && "POST".equalsIgnoreCase(request.getMethod())) {
            String brandName = request.getParameter("brandName");
            if (brandName == null || brandName.isBlank()) {
                request.setAttribute("errorMessage", "Failed to add. Name should not be blank.");
            }
            else{
            Brand brand = new Brand();
            brand.setBrandName(brandName.trim());
            boolean success = brandDAO.addBrand(brand);
            if (success) {
                request.setAttribute("successMessage", "Brand added successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to add brand. Name may already exist.");
            }
            }
        }

        // Lấy danh sách brand
        List<Brand> brandList = new ArrayList<>();
        int totalBrands = brandDAO.getTotalBrands(search);
        int totalPages = (int) Math.ceil((double) totalBrands / pageSize);
        if (page > totalPages) {
            page = totalPages;
        }
        page = page < 1 ? 1 : page;
        brandList = brandDAO.getBrands(page, pageSize, search, sort, order);
        // Thiết lập phân trang
        Pagination pagination = new Pagination();
        pagination.setListPageSize(totalBrands);
        pagination.setCurrentPage(page);
        pagination.setTotalPages(totalPages);
        pagination.setTotalPagesToShow(5);
        pagination.setPageSize(pageSize);
        pagination.setSort(sort);
        pagination.setOrder(order);
        pagination.setUrlPattern("/Brand");
        pagination.setSearchFields(new String[]{"search"});
        pagination.setSearchValues(new String[]{search});

        request.setAttribute("brandList", brandList);
        request.setAttribute("totalBrands", totalBrands);
        request.setAttribute("pagination", pagination);
        request.getRequestDispatcher("/views/BrandList.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}

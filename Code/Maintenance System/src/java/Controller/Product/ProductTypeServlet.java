package Controller.Product;

import DAO.ProductTypeDAO;
import Model.ProductType;
import Utils.Pagination;
import Utils.FormatUtils;
import Utils.SearchUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductTypeServlet", urlPatterns = {"/ProductType"})
public class ProductTypeServlet extends HttpServlet {
    private final ProductTypeDAO productTypeDAO = new ProductTypeDAO();
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
            int productTypeID = Integer.parseInt(request.getParameter("productTypeID"));
            boolean success = productTypeDAO.deleteProductType(productTypeID);
            if (success) {
                request.setAttribute("successMessage", "Product Type deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete product type. It may be in use.");
            }
        }

        if ("add".equals(action) && "POST".equalsIgnoreCase(request.getMethod())) {
            String typeName = request.getParameter("typeName");
            if(typeName==null||typeName.isBlank()){
                request.setAttribute("errorMessage", "Failed to add. Name should not be blank.");
            }
            else{
            ProductType type = new ProductType();
            type.setTypeName(typeName.trim());
            boolean success = productTypeDAO.addProductType(type);
            if (success) {
                request.setAttribute("successMessage", "Product Type added successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to add product type. Name may already exist.");
            }
            }
        }

        // Lấy danh sách product type
        List<ProductType> typeList = new ArrayList<>();
        int totalTypes = productTypeDAO.getTotalProductTypes(search);
        int totalPages = (int) Math.ceil((double) totalTypes / pageSize);
        if (page > totalPages) page = totalPages;
        page = page < 1 ? 1 : page;
        typeList = productTypeDAO.getProductTypes(page, pageSize, search, sort, order);

        // Thiết lập phân trang
        Pagination pagination = new Pagination();
        pagination.setListPageSize(totalTypes);
        pagination.setCurrentPage(page);
        pagination.setTotalPages(totalPages);
        pagination.setTotalPagesToShow(5);
        pagination.setPageSize(pageSize);
        pagination.setSort(sort);
        pagination.setOrder(order);
        pagination.setUrlPattern("/ProductType");
        pagination.setSearchFields(new String[]{"search"});
        pagination.setSearchValues(new String[]{search});

        request.setAttribute("typeList", typeList);
        request.setAttribute("totalTypes", totalTypes);
        request.setAttribute("pagination", pagination);
        request.getRequestDispatcher("/views/ProductTypeList.jsp").forward(request, response);
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
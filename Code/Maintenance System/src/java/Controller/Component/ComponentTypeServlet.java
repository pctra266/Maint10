package Controller.Component;

import DAO.ComponentTypeDAO;
import Model.ComponentType;
import Model.Pagination;
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

@WebServlet(name = "ComponentTypeServlet", urlPatterns = {"/ComponentType"})
public class ComponentTypeServlet extends HttpServlet {
    private final ComponentTypeDAO componentTypeDAO = new ComponentTypeDAO();
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

        // Xử lý xóa
        if ("delete".equals(action)) {
            int typeID = Integer.parseInt(request.getParameter("typeID"));
            boolean success = componentTypeDAO.deleteComponentType(typeID);
            if (success) {
                request.setAttribute("successMessage", "Component Type deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete component type. It may be in use.");
            }
        }

        // Xử lý thêm (POST từ modal)
        if ("add".equals(action) && "POST".equalsIgnoreCase(request.getMethod())) {
            String typeName = request.getParameter("typeName");
            ComponentType type = new ComponentType();
            type.setTypeName(typeName);
            boolean success = componentTypeDAO.addComponentType(type);
            if (success) {
                request.setAttribute("successMessage", "Component Type added successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to add component type. Name may already exist.");
            }
        }

        // Lấy danh sách component type
        List<ComponentType> typeList = new ArrayList<>();
        int totalTypes = componentTypeDAO.getTotalComponentTypes(search);
        int totalPages = (int) Math.ceil((double) totalTypes / pageSize);
        if (page > totalPages) page = totalPages;
        page = page < 1 ? 1 : page;
        typeList = componentTypeDAO.getComponentTypes(page, pageSize, search, sort, order);

        // Thiết lập phân trang
        Pagination pagination = new Pagination();
        pagination.setListPageSize(totalTypes);
        pagination.setCurrentPage(page);
        pagination.setTotalPages(totalPages);
        pagination.setTotalPagesToShow(5);
        pagination.setPageSize(pageSize);
        pagination.setSort(sort);
        pagination.setOrder(order);
        pagination.setUrlPattern("/ComponentType");
        pagination.setSearchFields(new String[]{"search"});
        pagination.setSearchValues(new String[]{search});

        request.setAttribute("typeList", typeList);
        request.setAttribute("totalTypes", totalTypes);
        request.setAttribute("pagination", pagination);
        request.getRequestDispatcher("/views/Component/ComponentType.jsp").forward(request, response);
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
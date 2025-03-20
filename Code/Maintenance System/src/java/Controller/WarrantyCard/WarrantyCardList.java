/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.WarrantyCard;

import DAO.WarrantyCardDAO;
import Model.Component;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import Model.WarrantyCard;
import Utils.FormatUtils;
import Model.Pagination;
import Model.Staff;
import Utils.SearchUtils;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "WarrantyCardList", urlPatterns = {"/WarrantyCard"})
public class WarrantyCardList extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private static final int PAGE_SIZE = 10;

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
        //truyen tham so cho nut back
        HttpSession session = request.getSession();
        session.setAttribute("createWarrantyCardFrom", request.getContextPath() + request.getServletPath());
        session.setAttribute("detailWarrantyCardFrom", request.getContextPath() + request.getServletPath());
        Staff staff = (Staff) session.getAttribute("staff");
        Integer staffID = staff == null ? null : staff.getStaffID();
        String pageParam = request.getParameter("page");
        if ("true".equalsIgnoreCase(request.getParameter("delete"))) {
            request.setAttribute("updateAlert1", "Delete success!");
        }
        if ("false".equalsIgnoreCase(request.getParameter("delete"))) {
            request.setAttribute("updateAlert0", "This card should not be delete, it contains important information!");
        }

        int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
        String type = request.getParameter("type");
        if (!("all".equalsIgnoreCase(type) || "new".equalsIgnoreCase(type) || "repair".equalsIgnoreCase(type) || "warranty".equalsIgnoreCase(type))) {
            type = "myCard";
        }
        String paraSearch = SearchUtils.preprocessSearchQuery(request.getParameter("search"));
        // Lấy page-size từ request, mặc định là PAGE_SIZE
        String pageSizeParam = request.getParameter("page-size");
        Integer pageSize;
        pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
        String status = request.getParameter("status");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        //--------------------------------------------------------------------------
        List<WarrantyCard> cards = new ArrayList<>();
        int totalCards = warrantyCardDAO.getTotalCards(paraSearch, status, type, staffID);
        int totalPages = (int) Math.ceil((double) totalCards / pageSize);
        if (page > totalPages) {
            page = totalPages;
        }
        page = page < 1 ? 1 : page;
        cards = warrantyCardDAO.getCards(page, pageSize, paraSearch, status, sort, order, type, staffID);

        String createStatus = request.getParameter("create");
        if (createStatus != null && createStatus.equals("true")) {
            request.setAttribute("createStatus", "Card created successfully");
        }
        //Phan trang
        Pagination pagination = new Pagination();
        pagination.setListPageSize(totalCards);
        pagination.setCurrentPage(page);
        pagination.setTotalPages(totalPages);
        pagination.setTotalPagesToShow(5);
        pagination.setPageSize(pageSize);
        pagination.setSort(sort);
        pagination.setOrder(order);
        pagination.setUrlPattern("/WarrantyCard");
        pagination.setSearchFields(new String[]{"search", "status", "type"});
        pagination.setSearchValues(new String[]{paraSearch, status, type});
        request.setAttribute("pagination", pagination);
        request.setAttribute("totalCards", totalCards);
        request.setAttribute("cardList", cards);
        request.getRequestDispatcher("views/WarrantyCard/WarrantyCardList.jsp").forward(request, response);
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

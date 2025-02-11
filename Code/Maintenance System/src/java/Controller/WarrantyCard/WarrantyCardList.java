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
import Utils.SearchUtils;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "WarrantyCardList", urlPatterns = {"/WarrantyCard"})
public class WarrantyCardList extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
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
        String paraSearch = SearchUtils.preprocessSearchQuery(request.getParameter("search"));
        int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
        // Lấy page-size từ request, mặc định là PAGE_SIZE
        String pageSizeParam = request.getParameter("page-size");
        Integer pageSize;
        pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
        String status = request.getParameter("status");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        //--------------------------------------------------------------------------
        List<WarrantyCard> cards = new ArrayList<>();
        int totalCards = warrantyCardDAO.getTotalCards(paraSearch, status);
        int totalPages = (int) Math.ceil((double) totalCards / pageSize);
        if (page > totalPages) {
            page = totalPages;
        }
        page = page < 1 ? 1 : page;
        cards = warrantyCardDAO.getCards(page, pageSize, paraSearch, status, sort, order);

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
        pagination.setSearchFields(new String[] {"search", "status"});
        pagination.setSearchValues(new String[] {paraSearch, status});
        request.setAttribute("pagination", pagination);

        request.setAttribute("totalCards", totalCards);
        request.setAttribute("cardList", cards);
        request.getRequestDispatcher("views/WarrantyCard/WarrantyCardList.jsp").forward(request, response);
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
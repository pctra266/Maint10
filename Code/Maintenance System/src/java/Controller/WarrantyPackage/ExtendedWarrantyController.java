/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.WarrantyPackage;

import DAO.ExtendedWarrantyDAO;
import Model.ExtendedWarranty;
import Utils.Pagination;
import Utils.SearchUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name = "ExtendedWarrantyController", urlPatterns = {"/extendedWarranty"})
public class ExtendedWarrantyController extends HttpServlet {

    private final int PAGE_SIZE = 5;
    private final ExtendedWarrantyDAO ewDao = new ExtendedWarrantyDAO();

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
            out.println("<title>Servlet ExtendedWarrantyController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExtendedWarrantyController at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "view";
        }
        switch (action) {
            case "view":
                viewAction(request, response);
                break;
            case "new":
                newAction(request, response);
                break;
            case "edit":
                editAction(request, response);
                break;
            case "delete":
                deleteAction(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/extendedWarranty?action=view");
                break;
        }

    }

    protected void viewAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchExtendedWarrantyName = SearchUtils.preprocessSearchQuery(request.getParameter("searchExtendedWarrantyName"));
        request.setAttribute("searchExtendedWarrantyName", searchExtendedWarrantyName);

        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("page-size");
        int page = (pageParam != null && !pageParam.trim().isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int pageSize = (pageSizeParam != null && !pageSizeParam.trim().isEmpty()) ? Integer.parseInt(pageSizeParam) : PAGE_SIZE;

        int total = ewDao.totalExtendedWarranty(searchExtendedWarrantyName);
        int totalPages = (int) Math.ceil((double) total / pageSize);
        if (page > totalPages) {
            page = totalPages;
        }
        if (page < 1) {
            page = 1;
        }

        Pagination pagination = new Pagination();
        pagination.setListPageSize(total);
        pagination.setCurrentPage(page);
        pagination.setTotalPages(totalPages);
        pagination.setTotalPagesToShow(5);
        pagination.setPageSize(pageSize);
        pagination.setSort(sort);
        pagination.setOrder(order);
        pagination.setUrlPattern("/extendedWarranty");
        pagination.setSort(request.getParameter(sort));
        pagination.setOrder(request.getParameter(order));

        pagination.setSearchFields(new String[]{"searchExtendedWarrantyName", "action"});
        pagination.setSearchValues(new String[]{
            searchExtendedWarrantyName,
            "view"

        });

        ArrayList<ExtendedWarranty> list = ewDao.getListExtendedWarranty(searchExtendedWarrantyName, page, pageSize);
        request.setAttribute("extendedWarranties", list);
        request.setAttribute("pagination", pagination);
        request.getRequestDispatcher("extendedWarrantyList.jsp").forward(request, response);
    }

    protected void newAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addExtendedWarranty.jsp").forward(request, response);
    }

    protected void editAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("extendedWarrantyID");
        ExtendedWarranty ew = ewDao.getExtendedWarrantyByID(id);
        request.setAttribute("extendedWarranty", ew);
        request.getRequestDispatcher("editExtendedWarranty.jsp").forward(request, response);
    }

    protected void deleteAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("extendedWarrantyID");
        boolean deleted = ewDao.deleteExtendedWarranty(id);
        String message = deleted ? "Extended warranty deleted successfully." : "Delete failed.";
        request.setAttribute("message", message);

        viewAction(request, response);
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
        String action = request.getParameter("action");
        System.out.println("action la"+ action);
        if (action.equals("new")) {
            createAction(request, response);
        } else if (action.equals("edit")) {
            updateAction(request, response);
        } else {
            doGet(request, response);
        }
    }

    protected void createAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExtendedWarranty ew = new ExtendedWarranty();
        ew.setExtendedWarrantyName(SearchUtils.preprocessSearchQuery(request.getParameter("extendedWarrantyName")));
        ew.setExtendedPeriodInMonths(Integer.parseInt(request.getParameter("extendedPeriodInMonths")));
        ew.setPrice(Double.parseDouble(request.getParameter("price")));
        ew.setExtendedWarrantyDescription(request.getParameter("extendedWarrantyDescription"));
        Map<String, String> listError = validateExtendedWarrantyCreate(ew);
        // check loi 1
        if (!listError.isEmpty()) {
            request.setAttribute("errors", listError);
            request.setAttribute("extendedWarranty", ew);
            request.getRequestDispatcher("addExtendedWarranty.jsp").forward(request, response);
        }else{
            boolean created = ewDao.createExtendedWarranty(ew);
        String message = created ? "Extended warranty created successfully." : "Creation failed.";
        request.setAttribute("message", message);
        viewAction(request, response);
        }
        
    }

    protected void updateAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExtendedWarranty ew = new ExtendedWarranty();
        ew.setExtendedWarrantyID(Integer.parseInt(request.getParameter("extendedWarrantyID")));
        ew.setExtendedWarrantyName(SearchUtils.preprocessSearchQuery(request.getParameter("extendedWarrantyName")));
        ew.setExtendedPeriodInMonths(Integer.parseInt(request.getParameter("extendedPeriodInMonths")));
        ew.setPrice(Double.parseDouble(request.getParameter("price")));
        ew.setExtendedWarrantyDescription(request.getParameter("extendedWarrantyDescription"));
        // check loi 1
        Map<String, String> listError = validateExtendedWarrantyUpdate(ew);
        if (!listError.isEmpty()) {
            request.setAttribute("errors", listError);
            request.setAttribute("extendedWarranty", ew);
            request.getRequestDispatcher("editExtendedWarranty.jsp").forward(request, response);
        }else{
            boolean updated = ewDao.updateExtendedWarranty(ew);
        String message = updated ? "Extended warranty updated successfully." : "Update failed.";
        request.setAttribute("message", message);
        viewAction(request, response);
        }
    }
    

    public Map<String, String> validateExtendedWarranty(ExtendedWarranty ew) {
        Map<String, String> errors = new HashMap<>();
        if (ew.getExtendedWarrantyName() == null || ew.getExtendedWarrantyName().trim().isEmpty()) {
            errors.put("nameError", "Name must not empty");
        } else if (ew.getExtendedWarrantyName().length() > 100) {
            errors.put("nameError", "Name must less than 100 character");
        }
        if (ew.getExtendedPeriodInMonths() < 1) {
            errors.put("monthError", "Month must more than 1");
        }
        if (ew.getPrice() < 0) {
            errors.put("priceError", "Price must more than 0");
        }
        if (ew.getExtendedWarrantyDescription().length() > 500) {
            errors.put("desError", "Description do not than 500 character");
        }
        return errors;
    }
    
    public Map<String, String> validateExtendedWarrantyCreate(ExtendedWarranty ew){
        Map<String, String> errors = validateExtendedWarranty(ew);
        
        String name = ew.getExtendedWarrantyName();
        double price = ew.getPrice();
        int month = ew.getExtendedPeriodInMonths();
        ExtendedWarranty testName = ewDao.findByName(name);
            if(testName != null){
                errors.put("nameError", "Name already exsitng, please choose another name");
            }
            
            
        ExtendedWarranty testValue = ewDao.findByPriceAndPeriod(price,month);
        if(testValue != null){
            errors.put("desError", "Already exsiting extend warranty have the same value, please alter month or price");
        }
        
        
        
        return errors;
    }
    
    public Map<String, String> validateExtendedWarrantyUpdate(ExtendedWarranty ew) {
    Map<String, String> errors = validateExtendedWarranty(ew);
    ExtendedWarranty currentEW = ewDao.getExtendedWarrantyByID(String.valueOf(ew.getExtendedWarrantyID()));
    if (currentEW == null) {
        errors.put("desError", "Extended warranty record not found.");
        return errors;
    }
    if (!currentEW.getExtendedWarrantyName().equals(ew.getExtendedWarrantyName())) {
        ExtendedWarranty testName = ewDao.findByName(ew.getExtendedWarrantyName());
        if (testName != null && testName.getExtendedWarrantyID() != (ew.getExtendedWarrantyID())) {
            errors.put("nameError", "Name already existing, please choose another name");
        }
    }
    if (currentEW.getPrice() != ew.getPrice() || currentEW.getExtendedPeriodInMonths() != ew.getExtendedPeriodInMonths()) {
        ExtendedWarranty testValue = ewDao.findByPriceAndPeriod(ew.getPrice(), ew.getExtendedPeriodInMonths());
        if (testValue != null && testValue.getExtendedWarrantyID() != (ew.getExtendedWarrantyID())) {
            errors.put("desError", "Already existing extend warranty has the same price and period, please alter month or price");
        }
    }
    
    boolean isNameSame = currentEW.getExtendedWarrantyName().equals(ew.getExtendedWarrantyName());
    boolean isPriceSame = currentEW.getPrice() == ew.getPrice();
    boolean isMonthSame = currentEW.getExtendedPeriodInMonths() == ew.getExtendedPeriodInMonths();
    boolean isDescriptionSame = (currentEW.getExtendedWarrantyDescription() == null && ew.getExtendedWarrantyDescription() == null)
                                 || (currentEW.getExtendedWarrantyDescription() != null 
                                     && currentEW.getExtendedWarrantyDescription().equals(ew.getExtendedWarrantyDescription()));
    
    if (isNameSame && isPriceSame && isMonthSame && isDescriptionSame) {
        errors.put("desError", "Nothing change please cancel");
    }
    

    return errors;
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

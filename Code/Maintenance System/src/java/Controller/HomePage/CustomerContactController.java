/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.HomePage;
import Model.CustomerContact;

import DAO.CustomerContactDAO;
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
import java.util.List;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="CustomerContact", urlPatterns={"/customerContact"})
public class CustomerContactController extends HttpServlet {
   private CustomerContactDAO contactDAO = new CustomerContactDAO();
   private final int DEFAULT_PAGE_SIZE = 5;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet CustomerContact</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerContact at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
    try {
        switch (action != null ? action : "") {
            case "createCustomerContact":
                String name = request.getParameter("Name");
                String email = request.getParameter("Email");
                String phone = request.getParameter("Phone");
                String message = request.getParameter("Message");

                if (name == null || phone == null || name.trim().isEmpty() || phone.trim().isEmpty()) {
                    request.setAttribute("error", "Name and Phone are required!");
                    request.getRequestDispatcher("customerContactForm.jsp").forward(request, response);
                    return;
                }

                CustomerContact contact = new CustomerContact();
                contact.setName(name);
                contact.setEmail(email);
                contact.setPhone(phone);
                contact.setMessage(message);

                if (contactDAO.addCustomerContact(contact)) {
                    request.setAttribute("success", "Contact request submitted successfully!");
                    request.getRequestDispatcher("customerContactForm.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Failed to submit request. Please try again.");
                    request.getRequestDispatcher("customerContactForm.jsp").forward(request, response);
                }
                
                break;
            case "view":
                 
        String searchName = SearchUtils.normalizeString(request.getParameter("searchName"));
        String searchPhone = SearchUtils.searchValidateNonSapce(request.getParameter("searchPhone"));
        String searchEmail = SearchUtils.searchValidateNonSapce(request.getParameter("searchEmail"));
        request.setAttribute("searchName", searchName);
        request.setAttribute("searchPhone", searchPhone);
        request.setAttribute("searchEmail", searchEmail);
        
       
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("page-size");
        int page = (pageParam != null && !pageParam.trim().isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int pageSize = (pageSizeParam != null && !pageSizeParam.trim().isEmpty()) ? Integer.parseInt(pageSizeParam) : DEFAULT_PAGE_SIZE;
        
        int total = contactDAO.totalCustomerContacts(searchName, searchPhone, searchEmail);
        int totalPages = (int) Math.ceil((double) total / pageSize);
        if(page > totalPages) page = totalPages;
        if(page < 1) page = 1;
        Pagination pagination = new Pagination();
        pagination.setListPageSize(total); 
        pagination.setCurrentPage(page);
        pagination.setTotalPages(totalPages);
        pagination.setTotalPagesToShow(5); 
        pagination.setPageSize(pageSize);
//                pagination.setSort(sort);
//                pagination.setOrder(order);
        pagination.setUrlPattern("/customerContact");
        pagination.setSearchFields(new String[]{"action","searchName", "searchPhone", "searchEmail"});
        pagination.setSearchValues(new String[]{"view",searchName, searchPhone, searchEmail});
        request.setAttribute("pagination", pagination);
        
        ArrayList<CustomerContact> contactList = contactDAO.getCustomerContacts(searchName, searchPhone, searchEmail, page, pageSize);
        request.setAttribute("contactList", contactList);
        
        
                
                request.getRequestDispatcher("customerContactList.jsp").forward(request, response);
                break;
            default:
                 response.sendRedirect("404Page.jsp");
            return ;

        }
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("404Page.jsp");
        return ;
    }
    
        
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
                String action = request.getParameter("action");

    try {
        switch (action != null ? action : "") {
            case "createCustomerContact":
                createCustomerContact(request, response);
                break;

        }
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("404Page.jsp");
    }
        
    }
    
    protected void createCustomerContact(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String name = request.getParameter("Name");
        String email = request.getParameter("Email");
        String phone = request.getParameter("Phone");
        String message = request.getParameter("Message");

        if (name == null || phone == null || name.trim().isEmpty() || phone.trim().isEmpty()) {
            request.setAttribute("error", "Name and Phone are required!");
            request.getRequestDispatcher("customerContactForm.jsp").forward(request, response);
            return;
        }

        CustomerContact contact = new CustomerContact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setMessage(message);

        if (contactDAO.addCustomerContact(contact)) {
            request.setAttribute("success", "Contact request submitted successfully!");
             request.getRequestDispatcher("customerContactForm.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Failed to submit request. Please try again.");
            request.getRequestDispatcher("customerContactForm.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

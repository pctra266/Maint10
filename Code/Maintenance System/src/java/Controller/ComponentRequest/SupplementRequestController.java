/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.ComponentRequest;

import DAO.ComponentDAO;
import DAO.SupplementRequestDAO;
import Model.Customer;
import Model.Staff;
import Model.SupplementRequest;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="SupplementRequestController", urlPatterns={"/supplementRequest"})
public class SupplementRequestController extends HttpServlet {
   private final ComponentDAO componentDAO = new ComponentDAO();
   private final SupplementRequestDAO supplementRqDAO = new SupplementRequestDAO();
   
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
            out.println("<title>Servlet SupplementRequestController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SupplementRequestController at " + request.getContextPath () + "</h1>");
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
            case "listSupplementRequest":
                showListSupplementRequest(request, response);
                break;

            case "createSupplementRequest":
                showCreateSupplementRequest(request, response);
                break;

            default:
                 response.sendRedirect("404Page.jsp");
            return ;
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("404Page.jsp");
    }
        
    } 
    private void showListSupplementRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setAttribute("listSupplementRequest", supplementRqDAO.getAllSupplementRequests());
    request.getRequestDispatcher("listSupplementRequest.jsp").forward(request, response);
    }
    private void showCreateSupplementRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setAttribute("ComponentType", request.getParameter("ComponentType"));
    request.setAttribute("brandList", componentDAO.getListBrand());
    request.setAttribute("typeList", componentDAO.getListType());
    request.getRequestDispatcher("supplementRequest.jsp").forward(request, response);
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
            case "updateSupplementRequest":
                updateSupplementRequest(request, response);
                break;

            case "createSupplementRequest":
                createSupplementRequest(request, response);
                break;
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("404Page.jsp");
    }
       
    }
    
    private void createSupplementRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
         try {
            // session to get customer and customerId
        HttpSession session = request.getSession();
        Customer currentCustomer = null;
        Staff currentStaff = null;
        try {
            currentCustomer = (Customer) session.getAttribute("customer");
        } catch (Exception e) {
        }
        String customerId = "1";
        if (currentCustomer != null) {
            customerId = String.valueOf(currentCustomer.getCustomerID());
        }
//        if(currentCustomer == null){
//            response.sendRedirect("LoginForm.jsp");
//            return ;
//        }

        try {
            currentStaff = (Staff) session.getAttribute("staff");
        } catch (Exception e) {
        }
        String staffId = "1";
        if (currentStaff != null) {
            staffId = String.valueOf(currentStaff.getStaffID());
        }

        String componentName = request.getParameter("Name");
        String componentType = request.getParameter("ComponentType");
        String typeName = request.getParameter("TypeID");
        String brandName = request.getParameter("BrandID");
        
            System.out.println("componentName from request: " + componentName);
            System.out.println("componentType from request: " + componentType);
        
        Integer typeID = componentDAO.getTypeID(typeName) == null ? 0:componentDAO.getTypeID(typeName) ;
        Integer brandID = componentDAO.getBrandID(brandName) == null ? 0: componentDAO.getBrandID(brandName);

        int requestedBy = Integer.parseInt(staffId);
        String note = request.getParameter("Note");

        
        SupplementRequest supplementRequest = new SupplementRequest();
        supplementRequest.setComponentName(componentName);
        supplementRequest.setComponentType(componentType);
        supplementRequest.setTypeID(typeID);
        supplementRequest.setBrandID(brandID);
        supplementRequest.setRequestedBy(requestedBy);
        supplementRequest.setRequestDate(new Date());
        supplementRequest.setStatus("waiting");
        supplementRequest.setNote(note);
        
            System.out.println(supplementRequest);

        boolean success = supplementRqDAO.addSupplementRequest(supplementRequest);
        if (success) {
            response.sendRedirect("supplementRequest.jsp?success=true");
        } else {
            response.sendRedirect("supplementRequest.jsp?error=true");
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("supplementRequest.jsp?error=true");
    }
    }
    
    private void updateSupplementRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            try {
        int requestID = Integer.parseInt(request.getParameter("requestID"));
        String status = request.getParameter("status");

        // Kiểm tra trạng thái hợp lệ
        if (!status.equals("approved") && !status.equals("cancel")) {
            response.sendRedirect("listSupplementRequest.jsp?error=Invalid status");
            return;
        }

        // Gọi DAO để cập nhật trạng thái
        boolean updated = supplementRqDAO.updateStatus(requestID, status);

        if (updated) {
            response.sendRedirect("supplementRequest?action=listSupplementRequest&success=Status updated successfully");
        } else {
            response.sendRedirect("supplementRequest?action=listSupplementRequest&error=Failed to update status");
        }
    } catch (NumberFormatException e) {
        response.sendRedirect("listSupplementRequest.jsp?error=Invalid request ID");
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

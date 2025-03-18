/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.HomePage;
import Model.CustomerContact;

import DAO.CustomerContactDAO;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="CustomerContact", urlPatterns={"/customerContact"})
public class CustomerContactController extends HttpServlet {
   private CustomerContactDAO contactDAO = new CustomerContactDAO();
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
        List<CustomerContact> contactList = contactDAO.getAllCustomerContacts();
        request.setAttribute("contactList", contactList);
        request.getRequestDispatcher("customerContactList.jsp").forward(request, response);
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

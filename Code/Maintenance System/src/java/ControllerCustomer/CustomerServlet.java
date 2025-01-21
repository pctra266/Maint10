/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ControllerCustomer;

import DAO.CustomerDAO;
import Model.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class CustomerServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CustomerServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerServlet at " + request.getContextPath () + "</h1>");
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
        CustomerDAO customerDao = new CustomerDAO();
        String action = request.getParameter("action");

        switch (action != null ? action : "") {
            case "detail":
                int customerId = Integer.parseInt(request.getParameter("id"));
                Customer customer = customerDao.getCustomerByID(customerId);
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("DetailCustomerForm.jsp").forward(request, response);
                break;

            case "search":
                String textSearch = request.getParameter("text");
                ArrayList<Customer> listSearchCustomer = customerDao.searchCustomerByName(textSearch);
                request.setAttribute("listSearchCustomer", listSearchCustomer);
                request.setAttribute("textSearch", textSearch);
                request.getRequestDispatcher("Customer.jsp").forward(request, response);
                break;
            case "update":
                int updateCustomerId = Integer.parseInt(request.getParameter("id"));
                Customer updateCustomer = customerDao.getCustomerByID(updateCustomerId);
                request.setAttribute("customer", updateCustomer);
                request.getRequestDispatcher("UpdateCustomerForm.jsp").forward(request, response);
                break;
            default:
                ArrayList<Customer> listCustomer = customerDao.getAllCustomer();
                request.setAttribute("listCustomer", listCustomer);
                request.getRequestDispatcher("Customer.jsp").forward(request, response);
                break;
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
         CustomerDAO customerDao = new CustomerDAO();
        String action = request.getParameter("action");

        switch (action != null ? action : "") {

            case "update":

                String id = request.getParameter("customerId");
                try {
                    int customerId = Integer.parseInt(id);
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String customerName = request.getParameter("name");
                    String customerEmail = request.getParameter("email");
                    String customerPhone = request.getParameter("phone");
                    String customerAddress = request.getParameter("address");
                    String customerImage = request.getParameter("image");
                    Customer updateCustomer = new Customer(customerId, username, password, customerName, customerEmail, customerPhone, customerAddress, customerImage);
                    customerDao.updateCustomer(updateCustomer);
                    response.sendRedirect("customer");
                } catch (IOException | NumberFormatException e) {

                }
                break;
            default:
                ArrayList<Customer> listCustomer = customerDao.getAllCustomer();
                request.setAttribute("listCustomer", listCustomer);
                request.getRequestDispatcher("Customer.jsp").forward(request, response);
                break;
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

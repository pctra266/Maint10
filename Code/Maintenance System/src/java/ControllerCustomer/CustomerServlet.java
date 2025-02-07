/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ControllerCustomer;
import DAO.CustomerDAO;
import Model.Customer;
import Utils.SearchUtils;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 *
 * @author PC
 */
@MultipartConfig
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

        int totalPages = customerDao.getNumberPage();

        String index = request.getParameter("index");
        int indexPage = 1;
        if (index != null && !index.isEmpty()) {
            try {
                indexPage = Integer.parseInt(index);
            } catch (NumberFormatException e) {
                indexPage = 1;
            }
        }

        ArrayList<Customer> listPage = customerDao.getCustomerPage(indexPage);

        String action = request.getParameter("action");

        switch (action != null ? action : "") {
            case "detail":
                int customerId = Integer.parseInt(request.getParameter("id"));
                Customer customer = customerDao.getCustomerByID(customerId);
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("DetailCustomerForm.jsp").forward(request, response);
                break;

            case "search":

                String searchQuery = SearchUtils.normalizeString(request.getParameter("text"));
              
                ArrayList<Customer> searchResult;
                if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                    searchResult = customerDao.searchCustomerByName(searchQuery);
                    if (searchResult.isEmpty()) {
                        request.setAttribute("searchMessage", "No resault.");
                    }
                } else {
                    searchResult = customerDao.getAllCustomer();
                }
                request.setAttribute("searchQuery", searchQuery);
                request.setAttribute("listCustomer", searchResult);
                request.getRequestDispatcher("Customer.jsp").forward(request, response);
                break;
            case "update":
                int updateCustomerId = Integer.parseInt(request.getParameter("id"));
                Customer updateCustomer = customerDao.getCustomerByID(updateCustomerId);
                request.setAttribute("customer", updateCustomer);
                request.getRequestDispatcher("UpdateCustomerForm.jsp").forward(request, response);
                break;
            case "add":

                request.getRequestDispatcher("AddCustomer.jsp").forward(request, response);
                break;

            default:

                request.setAttribute("totalPages", totalPages);
                request.setAttribute("listCustomer", listPage);
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
//         CustomerDAO customerDao = new CustomerDAO();
//        String action = request.getParameter("action");
//
//        switch (action != null ? action : "") {
//
//            case "update":
//                // Lay thong tin tu form
//                String id = request.getParameter("customerId");
//                try {
//                    int customerId = Integer.parseInt(id);
//                    String username = request.getParameter("username");
//                    String password = request.getParameter("password");
//                    String customerName = request.getParameter("name");
//                    String customerEmail = request.getParameter("email");
//                    String customerPhone = request.getParameter("phone");
//                    String customerAddress = request.getParameter("address");
//                    // Cap nhat file 
//                    Customer customer = customerDao.getCustomerByID(customerId);
//                    Part filePart = request.getPart("image");
//                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//                    String customerImage;
//
//                    if (fileName == null || fileName.isEmpty()) {
//                        customerImage = customer.getImage();
//
//                    } else {
//
//                        String uploadPath = getServletContext().getRealPath("") + File.separator + "img" + File.separator + "avatar";
//                        File uploadDir = new File(uploadPath);
//                        if (!uploadDir.exists()) {
//                            uploadDir.mkdirs();
//                        }
//                        filePart.write(uploadPath + File.separator + fileName);
//                        customerImage = "img/avatar/" + fileName;
//                    }
//
//                    // update
//                    Customer updateCustomer = new Customer(customerId, username, password, customerName, customerEmail, customerPhone, customerAddress, customerImage);
//                    customerDao.updateCustomer(updateCustomer);
//                    request.setAttribute("mess", "Update sucessfully!");
//                    request.setAttribute("customer", updateCustomer);
//
//                    request.getRequestDispatcher("UpdateCustomerForm.jsp").forward(request, response);
//
//                } catch (IOException | ServletException | NumberFormatException e) {
//
//                    request.setAttribute("error", "An error occurred while updating the customer: " + e.getMessage());
//                    request.getRequestDispatcher("UpdateCustomerForm.jsp").forward(request, response);
//                }
//
//                break;
//
//            case "add":
//                String username = request.getParameter("username");
//                String password = request.getParameter("password");
//                String customerName = request.getParameter("name");
//                String customerEmail = request.getParameter("email");
//                String customerPhone = request.getParameter("phone");
//                String customerAddress = request.getParameter("address");
//               
//                // check exist
//                if (customerDao.getCustomerByUsername(username) != null) {
//                    request.setAttribute("error", "Username is exits!Please choose another username");
//                    request.getRequestDispatcher("AddCustomer.jsp").forward(request, response);
//                    return;
//                }
//                if (customerDao.getCustomerByEmail(customerEmail) != null) {
//                    request.setAttribute("error", "Email is exits!Please choose another email");
//                    request.getRequestDispatcher("AddCustomer.jsp").forward(request, response);
//                    return;
//                }
//                if (customerDao.getCustomerByPhone(customerPhone) != null) {
//                    request.setAttribute("error", "Phone number is exits!Please choose another Phone number");
//                    request.getRequestDispatcher("AddCustomer.jsp").forward(request, response);
//                    return;
//                }
//                // up file img
//
//                Part filePart = request.getPart("image");
//                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//                String   customerImage;
//                String uploadPath = getServletContext().getRealPath("") + File.separator + "img" + File.separator + "avatar";
//                File uploadDir = new File(uploadPath);
//                if (!uploadDir.exists()) {
//                    uploadDir.mkdirs();
//                }
//                filePart.write(uploadPath + File.separator + fileName);
//                customerImage = "img/avatar/" + fileName;
//                // Encrytion Password
//                
//                // add 
//                Customer customer = new Customer(username, password, customerName, customerEmail, customerPhone, customerAddress, customerImage);
//                customerDao.addCustomer(customer);
//                response.sendRedirect("customer");
//                break;
//            default:
//                ArrayList<Customer> listCustomer = customerDao.getAllCustomer();
//                request.setAttribute("listCustomer", listCustomer);
//                request.getRequestDispatcher("Customer.jsp").forward(request, response);
//                break;
//        }
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

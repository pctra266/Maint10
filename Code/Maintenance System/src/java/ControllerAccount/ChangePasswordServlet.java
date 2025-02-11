/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package ControllerAccount;

import DAO.CustomerDAO;
import DAO.StaffDAO;
import Model.Customer;
import Model.Staff;
import Utils.Encryption;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class ChangePasswordServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ChangePasswordServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePasswordServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        StaffDAO staffDao = new StaffDAO();
        HttpSession session = request.getSession();
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");
        String confirmpassword = request.getParameter("confirmpassword");
//
//        if (!newpassword.equalsIgnoreCase(confirmpassword)) {
//            request.setAttribute("error", "Confirm password not the same new password");
//            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
//            return;
//        }
//
//        String encryptionPassword = Encryption.EncryptionPassword(newpassword);
//
//        Staff staff = (Staff) session.getAttribute("staff");
//        Customer customer = (Customer) session.getAttribute("customer");
//
//        if (staff != null) {
//            String currentPassword = staff.getPasswordS();
//            String oldPasswordEncryption = Encryption.EncryptionPassword(oldpassword);
//            if (!currentPassword.equals(oldPasswordEncryption)) {
//                request.setAttribute("error", "Old password is incorrect!");
//                request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
//                return;
//            }
//            Staff updatedStaffPassword = new Staff(staff.getStaffID(), staff.getUsernameS(), encryptionPassword, staff.getRole(),
//                    staff.getName(), staff.getEmail(), staff.getPhone(), staff.getAddress(), staff.getImgage());
//
//            staffDao.changePassword(updatedStaffPassword);
//            request.setAttribute("message", "Change password succesfuly!");
//            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
//           
//        }
//
//        if (customer != null) {
//            String currentPassword = customer.getPasswordC();
//            String oldPasswordEncryption = Encryption.EncryptionPassword(oldpassword);
//            if (!currentPassword.equals(oldPasswordEncryption)) {
//                request.setAttribute("error", "Old password is incorrect!");
//                request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
//                return;
//            }
//            Customer updatedCustomerPassword = new Customer(customer.getCustomerID(), customer.getUsernameC(), encryptionPassword,
//                    customer.getName(), customer.getEmail(), customer.getPhone(), customer.getAddress(), customer.getImage());
//
//            customerDao.changePassword(updatedCustomerPassword);
//            request.setAttribute("message", "Change password succesfuly!");
//            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
//        }

        if (!newpassword.equalsIgnoreCase(confirmpassword)) {
            request.setAttribute("error", "The confirm password is not the same as the new password");
            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
            return;
        }

        Staff staff = (Staff) session.getAttribute("accStaff");
        Customer customer = (Customer) session.getAttribute("accCus");
        // change password staff
        if (staff != null) {
            Staff updatedStaffPassword = new Staff(staff.getStaffID(), staff.getUsernameS(), newpassword, staff.getRole(),
                    staff.getName(), staff.getEmail(), staff.getPhone(), staff.getAddress(),staff.getImgage());

            staffDao.changePassword(updatedStaffPassword);

            request.setAttribute("message", "Password changed successfully!");
            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
        }
        // change password customer
        if (customer != null) {
            Customer updatedCustomerPassword = new Customer(String.valueOf(customer.getCustomerID()), customer.getUsernameC(), newpassword,
                    customer.getName(), customer.getEmail(), customer.getPhone(), customer.getAddress(),customer.getImage());

            customerDao.changePassword(updatedCustomerPassword); 

            
            request.setAttribute("message", "Password changed successfully!");
            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);

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

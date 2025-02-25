/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Account;

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
   
   


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       CustomerDAO customerDao = new CustomerDAO();
        StaffDAO staffDao = new StaffDAO();
        HttpSession session = request.getSession();
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");
        String confirmpassword = request.getParameter("confirmpassword");
        if (oldpassword.isBlank() || newpassword.isBlank() || confirmpassword.isBlank()) {
            request.setAttribute("error", "All fields are required! Please fill in all information.");
            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
            return;
        }
        // Check new password and confirm password
        if (!newpassword.equalsIgnoreCase(confirmpassword)) {
            request.setAttribute("error", "Confirm password not the same new password");
            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
            return;
        }
        // Encription password
        String encryptionPassword = Encryption.EncryptionPassword(newpassword);

        Staff staff = (Staff) session.getAttribute("staff");
        Customer customer = (Customer) session.getAttribute("customer");

        if (staff != null) {
            String currentPassword = staff.getPasswordS();
            String oldPasswordEncryption = Encryption.EncryptionPassword(oldpassword);
            if (!currentPassword.equals(oldPasswordEncryption)) {
                request.setAttribute("error", "Old password is incorrect!");
                request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
                return;
            }
           Staff updatedStaffPassword = new Staff(staff.getStaffID(), staff.getUsernameS(), encryptionPassword, staff.getRole(),
                    staff.getName(),staff.getGender(),staff.getDate(), staff.getEmail(), staff.getPhone(), staff.getAddress(), staff.getImage());

            staffDao.changePassword(updatedStaffPassword);
            session.setAttribute("staff", updatedStaffPassword);
            request.setAttribute("message", "Change password succesfuly!");
            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);

        }

        if (customer != null) {
            String currentPassword = customer.getPasswordC();
            String oldPasswordEncryption = Encryption.EncryptionPassword(oldpassword);
            if (!currentPassword.equals(oldPasswordEncryption)) {
                request.setAttribute("error", "Old password is incorrect!");
                request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
                return;
            }
            Customer updatedCustomerPassword = new Customer(customer.getCustomerID(), customer.getUsernameC(), encryptionPassword,
                    customer.getName(), customer.getGender(),customer.getDateOfBirth(), customer.getEmail(), customer.getPhone(), customer.getAddress(), customer.getImage());

            customerDao.changePassword(updatedCustomerPassword);
            session.setAttribute("customer", updatedCustomerPassword);
            request.setAttribute("message", "Change password succesfuly!");
            request.getRequestDispatcher("ChangePasswordForm.jsp").forward(request, response);
        }

    }

  

}

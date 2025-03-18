/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Account;

import DAO.CustomerDAO;
import DAO.StaffDAO;
import Email.Email;
import Model.Customer;
import Model.Staff;
import Utils.Encryption;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.SecureRandom;

/**
 *
 * @author PC
 */
public class ForgotPasswordServlet extends HttpServlet {
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       Email emailSend = new Email();
        String email = request.getParameter("email");
        StaffDAO staffDao = new StaffDAO();
        CustomerDAO customerDao = new CustomerDAO();

        Staff staff = staffDao.getStaffByEmail(email);
        Customer customer = customerDao.getCustomerByEmail(email);
        if (staff == null && customer == null) {
            request.setAttribute("error", "Email not exitst!");
            request.getRequestDispatcher("ForgotPasswordForm.jsp").forward(request, response);
            return;
        }
        String randomPassword = randomPassword(8);
        String encryptionPassword = Encryption.EncryptionPassword(randomPassword);
        if (staff != null) {
            staff.setPasswordS(encryptionPassword );
            staffDao.changePassword(staff);
        } else if (customer != null) {
            customer.setPasswordC(encryptionPassword );
            customerDao.changePassword(customer);
        }

        emailSend.sendEmail(email, "Password Reset Request",
                "Hello,\n\nYour new password is: " + randomPassword + "\n\nPlease change it after logging in.");
        request.setAttribute("message", "A new password has been sent to your email.");
        request.getRequestDispatcher("ForgotPasswordForm.jsp").forward(request, response);
        }
        private String randomPassword(int length) {
        String character = "0123456789";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(character.length());
            password.append(character.charAt(index));
        }
        return password.toString();
    }
}

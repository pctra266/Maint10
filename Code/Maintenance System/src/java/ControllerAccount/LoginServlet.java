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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        StaffDAO staffDao = new StaffDAO();
        CustomerDAO customerDao = new CustomerDAO();
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberme = request.getParameter("remember-me");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and Password must not be empty!");
            request.getRequestDispatcher("LoginForm.jsp").forward(request, response);
            return;
        }
        String encryptionPassword = Encryption.EncryptionPassword(password);
        Staff staff = staffDao.getStaffByUsenamePassword(username, encryptionPassword);
        Customer customer = customerDao.getCustomerByUsenamePassword(username, encryptionPassword);

        if (staff == null && customer == null) {
            request.setAttribute("error", "Username or Password is incorrect!");
            request.getRequestDispatcher("LoginForm.jsp").forward(request, response);
            return;
        }

        if (staff != null) {
            session.setAttribute("staff", staff);
            saveCookies(response, username, encryptionPassword, rememberme);
            response.sendRedirect("HomePage.jsp");
        } else if (customer != null) {
            session.setAttribute("customer", customer);
            saveCookies(response, username, encryptionPassword, rememberme);
            response.sendRedirect("HomePage.jsp");
        }

    }

    private void saveCookies(HttpServletResponse response, String username, String encryptionPassword, String rememberme) {
        Cookie cusername = new Cookie("cusername", username);
        Cookie cpassword = new Cookie("cpassword", encryptionPassword);
        Cookie crememberme = new Cookie("crememberme", rememberme);

        if (rememberme != null) {
            cusername.setMaxAge(60 * 60 * 24 * 7);
            cpassword.setMaxAge(60 * 60 * 24 * 7);
            crememberme.setMaxAge(60 * 60 * 24 * 7);
        } else {
            cusername.setMaxAge(0);
            cpassword.setMaxAge(0);
            crememberme.setMaxAge(0);
        }

        response.addCookie(cusername);
        response.addCookie(cpassword);
        response.addCookie(crememberme);
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

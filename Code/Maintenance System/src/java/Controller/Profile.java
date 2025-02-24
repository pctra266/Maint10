/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CustomerDAO;
import DAO.StaffDAO;
import Model.Customer;
import Model.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author sonNH
 */
public class Profile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAO();
        StaffDAO staffDAO = new StaffDAO();
        HttpSession session = request.getSession();

        Customer customerOnSession = (Customer) session.getAttribute("customer");
        Staff staffOnSession = (Staff) session.getAttribute("staff");

        PrintWriter out = response.getWriter();

        out.println(customerOnSession.getName());

//        if (customerOnSession != null) {
//            Customer customerProfile = customerDAO.getCustomerByID(customerOnSession.getCustomerID());
//            request.setAttribute("customerProfile", customerProfile);
//            out.println(customerProfile.getName());
//
//        } else if (staffOnSession != null) {
//            Staff staffProfile = staffDAO.getStaffById(staffOnSession.getStaffID());
//            request.setAttribute("staffProfile", staffProfile);
//            out.println(staffProfile.getName());
//
//        }
        //request.getRequestDispatcher("profile.jsp").forward(request, response);
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
    }

}

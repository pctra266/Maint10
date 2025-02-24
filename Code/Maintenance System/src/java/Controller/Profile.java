package Controller;

import DAO.CustomerDAO;
import DAO.StaffDAO;
import Model.Customer;
import Model.Staff;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class Profile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAO();
        StaffDAO staffDAO = new StaffDAO();
        HttpSession session = request.getSession();

        Customer customerOnSession = (Customer) session.getAttribute("customer");
        Staff staffOnSession = (Staff) session.getAttribute("staff");

        if (customerOnSession != null) {
            Customer customerProfile = customerDAO.getCustomerByID(customerOnSession.getCustomerID());
            request.setAttribute("customerProfile", customerProfile);
        } else if (staffOnSession != null) {
            String roleName = staffDAO.getRoleNameByStaffID(staffOnSession.getStaffID());
            Staff staffProfile = staffDAO.getStaffById(staffOnSession.getStaffID());
            request.setAttribute("staffProfile", staffProfile);
            request.setAttribute("roleName", roleName);
        }
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String userType = request.getParameter("userType");
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String dateOfBirth = request.getParameter("dateOfBirth");
     

        HttpSession session = request.getSession();

        if ("customer".equals(userType)) {
            CustomerDAO customerDAO = new CustomerDAO();
            Customer updatedCustomer = new Customer(id, name, gender, Date.valueOf(dateOfBirth), email, phone, address);
            boolean isUpdated = customerDAO.updateCustomerWithNoImage(updatedCustomer);
            if (isUpdated) {
            }
        } else if ("staff".equals(userType)) {
            StaffDAO staffDAO = new StaffDAO();
           boolean isUpdated = staffDAO.updateStaffWithNoImage(id, name, gender, dateOfBirth, email, phone, address);
            if (isUpdated) {
            }
        }
        response.sendRedirect("profile");
    }
}

package Controller;

import DAO.CustomerDAO;
import DAO.StaffDAO;
import Model.Customer;
import Model.Staff;
import Utils.OtherUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.sql.Date;

@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024, // 2MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
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
        String userType = request.getParameter("userType");
        HttpSession session = request.getSession();
        CustomerDAO customerDAO = new CustomerDAO();
        StaffDAO staffDAO = new StaffDAO();
        boolean isUpdated = false;

        if ("customer".equals(userType)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String dateOfBirth = request.getParameter("dateOfBirth");
            Customer updatedCustomer = new Customer(id, name, gender, Date.valueOf(dateOfBirth), email, phone, address);
            isUpdated = customerDAO.updateCustomerWithNoImage(updatedCustomer);

        } else if ("staff".equals(userType)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String dateOfBirth = request.getParameter("dateOfBirth");
            isUpdated = staffDAO.updateStaffWithNoImage(id, name, gender, dateOfBirth, email, phone, address);

        } else if ("updateImage".equals(userType)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Part imagePart = request.getPart("image");

            String fileName = imagePart.getSubmittedFileName();
            if (fileName == null || fileName.isEmpty()) {
                request.setAttribute("message", "Vui lòng tải lên ảnh sản phẩm.");
                doGet(request, response);
                return;
            }

            String lowerCaseFileName = fileName.toLowerCase();
            if (!(lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".png"))) {
                request.setAttribute("message", "Ảnh phải có định dạng JPG, JPEG hoặc PNG.");
                doGet(request, response);
                return;
            }
            String imagePath = OtherUtils.saveImage(imagePart, request, "img/photos");
            Customer customerOnSession = (Customer) session.getAttribute("customer");
            Staff staffOnSession = (Staff) session.getAttribute("staff");
            if (customerOnSession != null) {
                isUpdated = customerDAO.updateCustomerImage(id, imagePath);
                if (isUpdated) {
                    // Lấy lại thông tin khách hàng mới nhất từ CSDL và cập nhật vào session
                    Customer updatedCustomer = customerDAO.getCustomerByID(id);
                    session.setAttribute("customer", updatedCustomer);
                }
            } else if (staffOnSession != null) {
                isUpdated = staffDAO.updateStaffImage(id, imagePath);
                if (isUpdated) {
                    // Lấy lại thông tin nhân viên mới nhất từ CSDL và cập nhật vào session
                    Staff updatedStaff = staffDAO.getStaffById(id);
                    session.setAttribute("staff", updatedStaff);
                }
            }
        }

        if (isUpdated) {
            session.setAttribute("message", "Profile updated successfully!");
        } else {
            session.setAttribute("message", "Update failed!");
        }
        response.sendRedirect("profile");
    }
}

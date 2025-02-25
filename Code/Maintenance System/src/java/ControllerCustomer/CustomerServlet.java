/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerCustomer;

import DAO.CustomerDAO;
import Model.Customer;
import Utils.Encryption;
import Utils.Format;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author PC
 */
@MultipartConfig
public class CustomerServlet extends HttpServlet {

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
                request.getRequestDispatcher("Customer/DetailCustomerForm.jsp").forward(request, response);
                break;
            case "update":
                int updateCustomerId = Integer.parseInt(request.getParameter("id"));
                Customer updateCustomer = customerDao.getCustomerByID(updateCustomerId);
                request.setAttribute("customer", updateCustomer);
                request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);
                break;
            case "add":
                request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
                break;

            default:
                customer(request, response);

                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO customerDao = new CustomerDAO();
        String action = request.getParameter("action");

        switch (action != null ? action : "") {

            case "update":
                updateCustomer(request, response);
                break;
            case "add":
                addCustomer(request, response);
                break;
            default:
                ArrayList<Customer> listCustomer = customerDao.getAllCustomer();
                request.setAttribute("listCustomer", listCustomer);
                request.getRequestDispatcher("Customer/Customer.jsp").forward(request, response);
                break;
        }
    }

    /**
     * Add customer
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO customerDao = new CustomerDAO();
        Format format = new Format();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String customerName = request.getParameter("name").trim();
        String customerGender = request.getParameter("gender").trim();
        String dateOfBirthStr = request.getParameter("dateOfBirth").trim();
        String customerEmail = request.getParameter("email").trim();
        String customerPhone = request.getParameter("phone").trim();
        String customerAddress = request.getParameter("address").trim();

        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("customerName", customerName);
        request.setAttribute("customerGender", customerGender);
        request.setAttribute("customerDateOfBirth", dateOfBirthStr);
        request.setAttribute("customerEmail", customerEmail);
        request.setAttribute("customerPhone", customerPhone);
        request.setAttribute("customerAddress", customerAddress);
        // Check empty
        if (username.isEmpty() || password.isEmpty() || customerName.isEmpty()
                || customerGender.isEmpty() || customerEmail.isEmpty() || customerPhone.isEmpty()
                || customerAddress.isEmpty()) {
            request.setAttribute("error", "All fields are required! Please fill in all information.");
            request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
            return;
        }
        // check space
        if (customerPhone.contains(" ") || customerEmail.contains(" ") || password.contains(" ")) {
            request.setAttribute("error", "Phone and email,password not contain space!");
            request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
            return;
        }
        // Check phone
        if (customerPhone.length() != 10) {
            request.setAttribute("error", "Phone contain 10 number!");
            request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
            return;
        }
        // Check username
        if (customerDao.getCustomerByUsername(username) != null) {
            request.setAttribute("error", "Username already exists! Please choose another username.");
            request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
            return;
        }
        // Check email
        if (customerDao.getCustomerByEmail(customerEmail) != null) {
            request.setAttribute("error", "Email already exists! Please choose another email.");
            request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
            return;
        }
        // Check phone
        if (customerDao.getCustomerByPhone(customerPhone) != null) {
            request.setAttribute("error", "Phone number already exists! Please choose another phone number.");
            request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
            return;
        }
        // Xu ly fomat date
        java.sql.Date dateOfBirth = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = sdf.parse(dateOfBirthStr);
            dateOfBirth = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid date format!");
            request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
            return;
        }

        // Check fomat password
        if (!format.checkPassword(password)) {
            request.setAttribute("error", "Password must be between 8 and 16 characters.");
            request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
            return;
        }
        // Xử lý upload ảnh
        Part filePart = request.getPart("image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String customerImage;
        if (fileName == null || fileName.isEmpty()) {
            customerImage = "img/avatar/defaultavatar.jpg";
        } else {

            String uploadPath = getServletContext().getRealPath("") + File.separator + "img" + File.separator + "avatar";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            filePart.write(uploadPath + File.separator + fileName);
            customerImage = "img/avatar/" + fileName;
        }

        // Check image
        String customerImageCheck = customerImage.toLowerCase();
        if (!customerImageCheck.endsWith(".jpg") && !customerImageCheck.endsWith(".png")) {
            request.setAttribute("error", "File must be jpg or png");

            request.getRequestDispatcher("Customer/AddCustomer.jsp").forward(request, response);
            return;
        }
        String encryptedPassword = Encryption.EncryptionPassword(password);

        Customer newCustomer = new Customer(username, encryptedPassword, customerName, customerGender, dateOfBirth, customerEmail, customerPhone, customerAddress, customerImage);
        customerDao.addCustomer(newCustomer);

        response.sendRedirect("customer");
    }

    /**
     * Update Customer
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO customerDao = new CustomerDAO();
        // Lay thong tin tu form
        String id = request.getParameter("customerId");
        try {
            int customerId = Integer.parseInt(id);
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String customerName = request.getParameter("name").trim();
            String customerGender = request.getParameter("gender").trim();
            String dateOfBirthStr = request.getParameter("dateOfBirth").trim();
            String customerEmail = request.getParameter("email").trim();
            String customerPhone = request.getParameter("phone").trim();
            String customerAddress = request.getParameter("address").trim();
            Customer customer = customerDao.getCustomerByID(customerId);
            // Xu ly date pf birth
            java.sql.Date dateOfBirth = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = sdf.parse(dateOfBirthStr);
                dateOfBirth = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                request.setAttribute("error", "Invalid date format!");
                request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);
                return;
            }
            // Cap nhat file 
            Part filePart = request.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String customerImage;

            if (fileName == null || fileName.isEmpty()) {
                customerImage = customer.getImage();

            } else {

                String uploadPath = getServletContext().getRealPath("") + File.separator + "img" + File.separator + "avatar";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                filePart.write(uploadPath + File.separator + fileName);
                customerImage = "img/avatar/" + fileName;
            }
            if (username.isBlank() || password.isBlank() || customerName.isBlank()
                    || customerGender.isBlank() || customerEmail.isBlank() || customerPhone.isBlank()
                    || customerAddress.isBlank()) {
                request.setAttribute("error", "All fields are required! Please fill in all information.");
                Customer tempCustomer = customerDao.getCustomerByID(customerId);
                request.setAttribute("customer", tempCustomer);
                request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);
                return;
            }

            // Kiểm tra email, phone có tồn tại không
            if (!customerEmail.equals(customer.getEmail()) && customerDao.getCustomerByEmail(customerEmail) != null) {
                request.setAttribute("error", "Email already exists! Please choose another email.");
                Customer tempCustomer = customerDao.getCustomerByID(customerId);
                request.setAttribute("customer", tempCustomer);
                request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);
                return;
            }
            if (!customerPhone.equals(customer.getPhone()) && customerDao.getCustomerByPhone(customerPhone) != null) {
                request.setAttribute("error", "Phone number already exists! Please choose another phone number.");
                Customer tempCustomer = customerDao.getCustomerByID(customerId);
                request.setAttribute("customer", tempCustomer);
                request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);
                return;
            }
            // Check space
            if (customerPhone.contains(" ") || customerEmail.contains(" ")) {
                request.setAttribute("error", "Space");
                Customer tempCustomer = customerDao.getCustomerByID(customerId);
                request.setAttribute("customer", tempCustomer);
                request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);
                return;

            }
            if (customerPhone.length() > 10 || customerPhone.length() < 10) {
                request.setAttribute("error", "Phone contain 10 number!");
                Customer tempCustomer = customerDao.getCustomerByID(customerId);
                request.setAttribute("customer", tempCustomer);
                request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);
                return;
            }

            String customerImageCheck = customerImage.toLowerCase();
            if (!customerImageCheck.endsWith(".jpg") && !customerImageCheck.endsWith(".png")) {
                request.setAttribute("error", "File must be jpg or png");
                Customer tempCustomer = customerDao.getCustomerByID(customerId);
                request.setAttribute("customer", tempCustomer);
                request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);
                return;
            }

            // update
            Customer updateCustomer = new Customer(customerId, username, password, customerName, customerGender, dateOfBirth, customerEmail, customerPhone, customerAddress, customerImage);
            customerDao.updateCustomer(updateCustomer);

            request.setAttribute("customer", updateCustomer);

            request.setAttribute("mess", "Update sucessfully!");
            request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);

        } catch (IOException | ServletException | NumberFormatException e) {

            request.setAttribute("error", "An error occurred while updating the customer: " + e.getMessage());
            request.getRequestDispatcher("Customer/UpdateCustomerForm.jsp").forward(request, response);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void customer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CustomerDAO customerDao = new CustomerDAO();

        // Lấy thông tin tìm kiếm và sắp xếp từ request
        String searchName = SearchUtils.normalizeString(request.getParameter("name"));
        String searchGender = request.getParameter("gender");
        String searchEmail = SearchUtils.normalizeString(request.getParameter("email"));
        String searchPhone = SearchUtils.normalizeString(request.getParameter("phone"));
        String searchAddress = SearchUtils.normalizeString(request.getParameter("address"));
        String searchDay = request.getParameter("searchDate");
        String searchMonth = request.getParameter("searchMonth");
        String searchYear = request.getParameter("searchYear");
        String sortBy = request.getParameter("field");
        String sortOrder = request.getParameter("order");
        // Lấy giá trị ngày, tháng, năm từ request và chuyển đổi thành Integer (nếu có nhập)
        Integer day = request.getParameter("searchDay") != null && !request.getParameter("searchDay").isEmpty()
                ? Integer.parseInt(request.getParameter("searchDay"))
                : null;

        Integer month = request.getParameter("searchMonth") != null && !request.getParameter("searchMonth").isEmpty()
                ? Integer.parseInt(request.getParameter("searchMonth"))
                : null;

        Integer year = request.getParameter("searchYear") != null && !request.getParameter("searchYear").isEmpty()
                ? Integer.parseInt(request.getParameter("searchYear"))
                : null;

        // Lấy giá trị phân trang
        String pageIndex = request.getParameter("index");
        String pageSize = request.getParameter("page-size");

        // Xử lý giá trị mặc định cho pageSize và pageIndex
        if (pageSize
                == null || pageSize.isEmpty()) {
            pageSize = "5";  // Mặc định là 5 bản ghi mỗi trang
        }

        if (pageIndex
                == null || pageIndex.isEmpty()) {
            pageIndex = "1";  // Mặc định là trang đầu tiên
        }

        // Chuyển pageSize và pageIndex sang kiểu số nguyên
        int size = 5;
        int page = 1;
        int offset = 0;

        try {
            size = Integer.parseInt(pageSize);
            page = Integer.parseInt(pageIndex);
            offset = (page - 1) * size;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing page size or index: " + e);
        }

        ArrayList<Customer> listCustomer = new ArrayList<>();
        int totalCustomer = 0;
        int totalPages = 1;

        // Kiểm tra nếu có điều kiện tìm kiếm
        if ((searchName
                != null && !searchName.trim()
                        .isEmpty())
                || (searchGender != null && !searchGender.trim().isEmpty())
                || (searchEmail != null && !searchEmail.trim().isEmpty())
                || (searchPhone != null && !searchPhone.trim().isEmpty())
                || (searchAddress != null && !searchAddress.trim().isEmpty())) {

            // Gọi phương thức tìm kiếm với các tham số tìm kiếm, sắp xếp, phân trang
            listCustomer = customerDao.advancedSearch(searchName, searchGender, searchEmail, searchPhone, searchAddress, day, month, year, sortBy, sortOrder, offset, size);

            // Lấy tổng số khách hàng tìm được
            totalCustomer = customerDao.getCustomerAdvancedSearchPage(searchName, searchGender, searchEmail, searchPhone, searchAddress, day, month, year);

            // Tính toán tổng số trang
            totalPages = (int) Math.ceil((double) totalCustomer / size);

        } else {
            listCustomer = customerDao.advancedSearch(searchName, searchGender, searchEmail, searchPhone, searchAddress, day, month, year, sortBy, sortOrder, offset, size);
            totalCustomer = customerDao.getCustomerAdvancedSearchPage(searchName, searchGender, searchEmail, searchPhone, searchAddress, day, month, year);
            totalPages = (int) Math.ceil((double) totalCustomer / size);

        }

        request.setAttribute("searchName", searchName);
        request.setAttribute("searchGender", searchGender);
        request.setAttribute("searchEmail", searchEmail);
        request.setAttribute("searchPhone", searchPhone);
        request.setAttribute("searchAddress", searchAddress);
        request.setAttribute("searchDate", searchDay);
        request.setAttribute("searchMonth", searchMonth);
        request.setAttribute("searchYear", searchYear);
        request.setAttribute("size", size);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("listCustomer", listCustomer);

        request.getRequestDispatcher("Customer/Customer.jsp").forward(request, response);

    }

    
}

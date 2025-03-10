package Controller.Product;

import DAO.CustomerDAO;
import DAO.ProductDAO;
import DAO.StaffDAO;
import Model.Customer;
import Model.Staff;
import Model.UnknownProduct;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewListUnknownProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // DAO
        ProductDAO unknownProductDAO = new ProductDAO();

        // Lấy các tham số tìm kiếm (nếu có)
        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        String receivedDate = request.getParameter("receivedDate");
        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("phone");

        // Xử lý chuỗi rỗng -> null
        if (productCode != null && productCode.trim().isEmpty()) {
            productCode = null;
        }
        if (productName != null && productName.trim().isEmpty()) {
            productName = null;
        }
        if (description != null && description.trim().isEmpty()) {
            description = null;
        }
        if (receivedDate != null && receivedDate.trim().isEmpty()) {
            receivedDate = null;
        }
        if (customerName != null && customerName.trim().isEmpty()) {
            customerName = null;
        }
        if (customerPhone != null && customerPhone.trim().isEmpty()) {
            customerPhone = null;
        }

        // Lấy số trang hiện tại
        int page = 1;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        // Mặc định pageSize = 5
        int pageSize = 5;
        String selectedPageSize = request.getParameter("pageSize");
        String customPageSize = request.getParameter("customPageSize");

        if (selectedPageSize != null) {
            if ("custom".equals(selectedPageSize)) {
                // Người dùng chọn "Custom"
                if (customPageSize != null && !customPageSize.trim().isEmpty()) {
                    try {
                        pageSize = Integer.parseInt(customPageSize);
                    } catch (NumberFormatException e) {
                        pageSize = 5; // fallback nếu parse sai
                    }
                }
            } else {
                // Người dùng chọn 5, 10, 15, 20, 25
                try {
                    pageSize = Integer.parseInt(selectedPageSize);
                } catch (NumberFormatException e) {
                    pageSize = 5;
                }
            }
        }

        // Đếm tổng số bản ghi
        int totalRecords = unknownProductDAO.countUnknownProducts(
                productCode, productName, description, receivedDate,
                customerName, customerPhone);

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Lấy danh sách sản phẩm không rõ nguồn gốc (có phân trang)
        List<UnknownProduct> unknownProducts = unknownProductDAO.searchUnknownProducts(
                productCode, productName, description, receivedDate,
                customerName, customerPhone, page, pageSize);

        // Gửi dữ liệu về JSP
        request.setAttribute("listUnknownProduct", unknownProducts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);            // Để hiển thị lại trên dropdown
        request.setAttribute("customPageSize", customPageSize); // Nếu có, để hiển thị lại giá trị custom

        // Gửi lại các tham số tìm kiếm để giữ nguyên form
        request.setAttribute("productCode", productCode);
        request.setAttribute("productName", productName);
        request.setAttribute("description", description);
        request.setAttribute("receivedDate", receivedDate);
        request.setAttribute("customerName", customerName);
        request.setAttribute("customerPhone", customerPhone);

        // Forward
        request.getRequestDispatcher("Product/viewUnknownProduct.jsp").forward(request, response);
    }
    
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerId = request.getParameter("customerId");
        String productId = request.getParameter("productId");

        if (customerId == null || productId == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        StaffDAO staffDAO = new StaffDAO();
        Customer customer = customerDAO.getCustomerByID(Integer.parseInt(customerId));
        UnknownProduct unknownProduct = productDAO.getUnknownProductById(Integer.parseInt(productId));
        int warrantyProductId = productDAO.getWarrantyProductIdByUnknownProductId(Integer.parseInt(productId));

        List<Staff> technicians = staffDAO.getAllTechnicians();

        if (customer == null || unknownProduct == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        request.setAttribute("customer", customer);
        request.setAttribute("unknownProduct", unknownProduct);
        request.setAttribute("warrantyProductId", warrantyProductId);
        request.setAttribute("staffList", technicians);
        request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
    }
}

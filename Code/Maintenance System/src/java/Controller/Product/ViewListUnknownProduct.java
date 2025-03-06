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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDAO unknownProductDAO = new ProductDAO();

        // Nhận các tham số tìm kiếm
        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        String receivedDate = request.getParameter("receivedDate");
        String customerName = request.getParameter("customerName");
        String customerPhone = request.getParameter("phone");
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

        // Nhận số trang từ request, mặc định là 1
        int page = 1;
        int pageSize = 5; // Số sản phẩm trên mỗi trang
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // Đếm tổng số bản ghi để tính tổng số trang
        int totalRecords = unknownProductDAO.countUnknownProducts(productCode, productName, description, receivedDate, customerName, customerPhone);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        // Lấy danh sách sản phẩm không rõ nguồn gốc với phân trang
        List<UnknownProduct> unknownProducts = unknownProductDAO.searchUnknownProducts(productCode, productName, description, receivedDate, customerName,customerPhone, page, pageSize);

        // Gửi dữ liệu về JSP
        request.setAttribute("listUnknownProduct", unknownProducts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("productCode", productCode);
        request.setAttribute("productName", productName);
        request.setAttribute("description", description);
        request.setAttribute("receivedDate", receivedDate);
        request.setAttribute("customerName", customerName);

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

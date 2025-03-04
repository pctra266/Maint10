package Controller.Product;

import DAO.ProductDAO;
import Model.Pagination;
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
        int totalRecords = unknownProductDAO.countUnknownProducts(productCode, productName, description, receivedDate, customerName);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        // Lấy danh sách sản phẩm không rõ nguồn gốc với phân trang
        List<UnknownProduct> unknownProducts = unknownProductDAO.searchUnknownProducts(productCode, productName, description, receivedDate, customerName, page, pageSize);

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
    }
}

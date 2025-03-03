package Controller.Product;

import DAO.CustomerDAO;
import DAO.ProductDAO;
import Model.Customer;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author sonNH
 */
public class AddUnknownProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CustomerDAO customerDao = new CustomerDAO();
        List<Customer> listCustomer = customerDao.getAllCustomer();
        request.setAttribute("listCustomer", listCustomer);
        request.getRequestDispatcher("Product/createUnknownProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();

        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        String customerIdStr = request.getParameter("customerId");
        String description = request.getParameter("description");
        String receivedDate = request.getParameter("receivedDate");

        int customerId = customerIdStr != null && !customerIdStr.isEmpty() ? Integer.parseInt(customerIdStr) : -1;

        // Kiểm tra trùng productCode
        if (productDAO.isUnknownProductCodeExists(productCode)) {
            request.setAttribute("message", "Product Code đã tồn tại!");

            // Giữ lại thông tin nhập vào form
            request.setAttribute("productCode", productCode);
            request.setAttribute("productName", productName);
            request.setAttribute("customerId", customerId);
            request.setAttribute("description", description);
            request.setAttribute("receivedDate", receivedDate);

            // Load danh sách khách hàng để hiển thị lại form
            doGet(request, response);
            return;
        }

        // Lưu vào database
        boolean success = productDAO.addUnknownProduct(customerId, productName, productCode, description, receivedDate);

        if (success) {
            request.setAttribute("message", "Thêm sản phẩm thành công!");
        } else {
            request.setAttribute("message", "Lỗi khi thêm sản phẩm!");
        }
        doGet(request, response);
    }

}

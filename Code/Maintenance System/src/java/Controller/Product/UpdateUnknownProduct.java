package Controller.Product;

import DAO.CustomerDAO;
import DAO.ProductDAO;
import Model.Customer;
import Model.UnknownProduct;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonNH
 */
public class UpdateUnknownProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO customerDao = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        String productId = request.getParameter("productId");
        UnknownProduct product = productDAO.getUnknownProductById(Integer.parseInt(productId));
        List<Customer> customerList = customerDao.getAllCustomer();
        request.setAttribute("unknownProduct", product);
        request.setAttribute("customerList", customerList);
        request.getRequestDispatcher("Product/updateUnknownProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        CustomerDAO customerDao = new CustomerDAO();
        String productStr = request.getParameter("productId");
        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        String customerIdStr = request.getParameter("customerId");
        String description = request.getParameter("description");
        String receivedDate = request.getParameter("receivedDate");

        int customerId = customerIdStr != null && !customerIdStr.isEmpty() ? Integer.parseInt(customerIdStr) : -1;
        int productId = productStr != null && !productStr.isEmpty() ? Integer.parseInt(productStr) : -1;

        // Kiểm tra trùng mã, nếu có thì giữ lại dữ liệu nhập từ form
        if (productDAO.isDuplicateUnknownProductCode(productId, productCode)) {
            UnknownProduct tempProduct = new UnknownProduct();
            tempProduct.setUnknownProductId(productId);
            tempProduct.setProductCode(productCode);
            tempProduct.setProductName(productName);
            tempProduct.setCustomerId(customerId);
            tempProduct.setDescription(description);
            tempProduct.setReceivedDate(receivedDate); // Nếu cần chuyển đổi kiểu dữ liệu thì thực hiện tại đây

            request.setAttribute("unknownProduct", tempProduct);
            request.setAttribute("errorMessage", "Product Code already exists!");
            List<Customer> customerList = customerDao.getAllCustomer();
            request.setAttribute("customerList", customerList);
            request.getRequestDispatcher("Product/updateUnknownProduct.jsp").forward(request, response);
            return;
        }

        boolean success = false;
        try {
            success = productDAO.updateUnknownProduct(productId, customerId, productName, productCode, description, receivedDate);
            if (success) {
                request.setAttribute("successMessage", "Product updated successfully!");
            } else {
                request.setAttribute("errorMessage", "Error updating product!");
            }
        } catch (SQLException ex) {
           
        }
        UnknownProduct updatedProduct = productDAO.getUnknownProductById(productId);
        request.setAttribute("unknownProduct", updatedProduct);
        List<Customer> customerList = customerDao.getAllCustomer();
        request.setAttribute("customerList", customerList);
        request.getRequestDispatcher("Product/updateUnknownProduct.jsp").forward(request, response);
    }
}

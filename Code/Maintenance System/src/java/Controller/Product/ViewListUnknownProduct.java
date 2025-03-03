package Controller.Product;

import DAO.ProductDAO;
import Model.UnknownProduct;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author sonNH
 */
public class ViewListUnknownProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();

        // Lấy tham số tìm kiếm
        String productCode = request.getParameter("productCode");
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        String receivedDate = request.getParameter("receivedDate");
        String customerName = request.getParameter("customerName");

        // Xử lý phân trang
        int page = 1;
        int pageSize = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        // Lấy danh sách sản phẩm
        List<UnknownProduct> listUnknownProduct = productDAO.searchUnknownProducts(
                productCode, productName, description, receivedDate, customerName, page, pageSize);

        // Lấy tổng số sản phẩm để tính tổng số trang
        int totalProducts = productDAO.countUnknownProducts(productCode, productName, description, receivedDate, customerName);
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // Gửi dữ liệu về JSP
        request.setAttribute("listUnknownProduct", listUnknownProduct);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("Product/viewUnknownProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}

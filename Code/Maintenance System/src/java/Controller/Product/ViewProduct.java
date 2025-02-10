package Controller.Product;

import DAO.ProductDAO;
import Model.Brand;
import Model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewProduct extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>hello ViewProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        List<Brand> listBrand = productDAO.getAllBrands();
        List<String> productTypes = productDAO.getDistinctProductTypes();
        String errorMessage = null;

        String brandIdParam = request.getParameter("brandId");
        String searchName = request.getParameter("searchName") != null ? request.getParameter("searchName").trim() : "";
        String searchCode = request.getParameter("searchCode") != null ? request.getParameter("searchCode").trim() : "";
        String type = request.getParameter("type") != null ? request.getParameter("type").trim() : "all";
        String sortQuantity = request.getParameter("sortQuantity") != null ? request.getParameter("sortQuantity").trim() : "";
        String sortWarranty = request.getParameter("sortWarranty") != null ? request.getParameter("sortWarranty").trim() : "";

        Integer brandId = (brandIdParam != null && !brandIdParam.isEmpty()) ? Integer.parseInt(brandIdParam) : null;

        // 🔹 Chuẩn hóa input: Xóa khoảng trắng thừa
        searchName = searchName.replaceAll("\\s+", " ");

        // 🔹 Kiểm tra định dạng nhập vào
        if (!searchName.matches("^[a-zA-Z0-9 ]*$")) {
            errorMessage = "Tên sản phẩm chỉ được chứa chữ cái, số và dấu cách!";
        } else if (!searchCode.matches("^[a-zA-Z0-9 ]*$")) {
            errorMessage = "Mã sản phẩm chỉ được chứa chữ cái, số và dấu cách!";
        }

        List<Product> productList;
        int totalPages = 1;

        if (errorMessage != null) {
            // 🔹 Nếu có lỗi, lấy danh sách sản phẩm từ đầu mà không áp dụng bộ lọc tìm kiếm
            int page = 1;
            int recordsPerPage = 8;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            int offset = (page - 1) * recordsPerPage;

            // 🔹 Lấy danh sách sản phẩm dựa trên tìm kiếm & phân trang
            productList = productDAO.searchProducts(null, null, null, null, null, null, offset, recordsPerPage);
            int totalRecords = productDAO.getTotalProducts(null, null, null, null);
            totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
        } else {
            int page = 1;
            int recordsPerPage = 8;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            int offset = (page - 1) * recordsPerPage;

            // 🔹 Lấy danh sách sản phẩm dựa trên tìm kiếm & phân trang
            productList = productDAO.searchProducts(searchName, searchCode, brandId, type, sortQuantity, sortWarranty, offset, recordsPerPage);
            int totalRecords = productDAO.getTotalProducts(searchName, searchCode, brandId, type);
            totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
        }

        if (productList.isEmpty()) {
            errorMessage = "Không tìm thấy sản phẩm phù hợp với tìm kiếm của bạn.";
        }

        // 🔹 Truyền lại dữ liệu vào JSP
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("productList", productList);
        request.setAttribute("currentPage", 1);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchName", searchName);
        request.setAttribute("searchCode", searchCode);
        request.setAttribute("brandID", brandId);
        request.setAttribute("type", type);
        request.setAttribute("sortQuantity", sortQuantity);
        request.setAttribute("sortWarranty", sortWarranty);
        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listType", productTypes);

        request.getRequestDispatcher("/Product/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

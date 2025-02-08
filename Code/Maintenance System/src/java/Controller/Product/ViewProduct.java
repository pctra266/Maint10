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

        // 🔹 Xử lý: Loại bỏ khoảng trắng thừa giữa các từ
        searchName = searchName.replaceAll("\\s+", " "); // Chỉ giữ 1 dấu cách giữa các từ

        // 🔹 Kiểm tra định dạng (chỉ cho phép chữ cái, số, và dấu cách)
        if (!searchName.matches("^[a-zA-Z0-9 ]*$")) {
            errorMessage = "Tên sản phẩm chỉ được chứa chữ cái, số và dấu cách!";
        } else if (!searchCode.matches("^[a-zA-Z0-9 ]*$")) {
            errorMessage = "Mã sản phẩm chỉ được chứa chữ cái, số và dấu cách!";
        }

        if (errorMessage != null) {
            // 🔹 Gửi thông báo lỗi sang JSP nếu có lỗi
            request.setAttribute("errorMessage", errorMessage);
        } else {
            int page = 1;
            int recordsPerPage = 8;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            int offset = (page - 1) * recordsPerPage;

            // 🔹 Lấy danh sách sản phẩm dựa trên tìm kiếm & phân trang
            List<Product> productList = productDAO.searchProducts(searchName, searchCode, brandId, type, sortQuantity, sortWarranty, offset, recordsPerPage);
            int totalRecords = productDAO.getTotalProducts(searchName, searchCode, brandId, type);
            int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

            request.setAttribute("productList", productList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
        }

        // 🔹 Truyền lại dữ liệu nhập để hiển thị trên giao diện
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

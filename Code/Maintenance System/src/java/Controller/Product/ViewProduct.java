package Controller.Product;

import DAO.ProductDAO;
import Model.Brand;
import Model.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewProduct extends HttpServlet {

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
        searchName = searchName.replaceAll("\\s+", " ");

        if (!searchName.matches("^[a-zA-Z0-9 ]*$")) {
            errorMessage = "Tên sản phẩm chỉ được chứa chữ cái, số và dấu cách!";
        } else if (!searchCode.matches("^[a-zA-Z0-9 ]*$")) {
            errorMessage = "Mã sản phẩm chỉ được chứa chữ cái, số và dấu cách!";
        }

        int page = 1;
        int recordsPerPage = 8;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        if (request.getParameter("recordsPerPage") != null) {
            recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
            if (recordsPerPage < 1) {
                recordsPerPage = 8; // Đảm bảo giá trị hợp lệ
            }
        }

        int offset = (page - 1) * recordsPerPage;
        List<Product> productList = productDAO.searchProducts(searchName, searchCode, brandId, type, sortQuantity, sortWarranty, offset, recordsPerPage);
        int totalRecords = productDAO.getTotalProducts(searchName, searchCode, brandId, type);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        if (productList.isEmpty()) {
            errorMessage = "Không tìm thấy sản phẩm phù hợp với tìm kiếm của bạn.";
        }

        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("productList", productList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchName", searchName);
        request.setAttribute("searchCode", searchCode);
        request.setAttribute("brandID", brandId);
        request.setAttribute("type", type);
        request.setAttribute("sortQuantity", sortQuantity);
        request.setAttribute("sortWarranty", sortWarranty);
        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listType", productTypes);
        request.setAttribute("recordsPerPage", recordsPerPage);

        request.getRequestDispatcher("/Product/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}

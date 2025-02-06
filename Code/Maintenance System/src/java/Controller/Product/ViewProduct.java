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
import java.util.ArrayList;
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

        String brandIdParam = request.getParameter("brandId");
        String searchName = (request.getParameter("searchName") != null && !request.getParameter("searchName").isEmpty()) ? request.getParameter("searchName") : "";
        String searchCode = (request.getParameter("searchCode") != null && !request.getParameter("searchCode").isEmpty()) ? request.getParameter("searchCode") : "";
        String type = (request.getParameter("type") != null && !request.getParameter("type").isEmpty()) ? request.getParameter("type") : "all";
        String sortQuantity = (request.getParameter("sortQuantity") != null && !request.getParameter("sortQuantity").isEmpty()) ? request.getParameter("sortQuantity") : "";
        String sortWarranty = (request.getParameter("sortWarranty") != null && !request.getParameter("sortWarranty").isEmpty())
                ? request.getParameter("sortWarranty") : "";

        Integer brandId = (brandIdParam != null && !brandIdParam.isEmpty()) ? Integer.parseInt(brandIdParam) : null;

        int page = 1;
        int recordsPerPage = 8;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * recordsPerPage;

        // Lấy danh sách sản phẩm dựa trên các tham số tìm kiếm và phân trang
        List<Product> productList = productDAO.searchProducts(searchName, searchCode, brandId, type, sortQuantity, sortWarranty, offset, recordsPerPage);

//        PrintWriter out = response.getWriter();
//        out.println(page);
//        out.println(searchName);
//        out.println(searchCode);
        // out.println(productList.size());
        // Tính tổng số sản phẩm
        int totalRecords = productDAO.getTotalProducts(searchName, searchCode, brandId, type);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

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

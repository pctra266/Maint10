/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Product;

import DAO.ProductDAO;
import Model.Brand;
import Model.Product;
import Utils.OtherUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.List;

/**
 *
 * @author sonNH
 */
@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024, // 2MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class AddProduct extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProduct at " + request.getContextPath() + "</h1>");
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

        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listType", productTypes);
        request.getRequestDispatcher("Product/addProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();

        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String brandId = request.getParameter("brandId");
        String type = request.getParameter("type");
        String quantity = request.getParameter("quantity");
        String warrantyPeriod = request.getParameter("warrantyPeriod");
        String status = request.getParameter("status");
        Part imagePart = request.getPart("image");

        // Kiểm tra mã sản phẩm đã tồn tại chưa
        if (productDAO.isProductCodeExists(code)) {
            request.setAttribute("errorMessage", "Mã sản phẩm đã tồn tại. Vui lòng nhập mã khác.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("type", type);
            request.setAttribute("quantity", quantity);
            request.setAttribute("warrantyPeriod", warrantyPeriod);
            request.setAttribute("status", status);
            doGet(request, response);
            return;
        }

        // Kiểm tra định dạng ảnh
        String fileName = imagePart.getSubmittedFileName();
        if (fileName == null || fileName.isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng tải lên ảnh sản phẩm.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("type", type);
            request.setAttribute("quantity", quantity);
            request.setAttribute("warrantyPeriod", warrantyPeriod);
            request.setAttribute("status", status);
            doGet(request, response);
            return;
        }

        String lowerCaseFileName = fileName.toLowerCase();
        if (!(lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".png"))) {
            request.setAttribute("errorMessage", "Ảnh phải có định dạng JPG, JPEG hoặc PNG.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("type", type);
            request.setAttribute("quantity", quantity);
            request.setAttribute("warrantyPeriod", warrantyPeriod);
            request.setAttribute("status", status);
            doGet(request, response);
            return;
        }

        // Lưu ảnh nếu hợp lệ
        String imagePath = OtherUtils.saveImage(imagePart, request, "img/photos");
        Product product = new Product(code, name, Integer.parseInt(brandId), type, Integer.parseInt(quantity), Integer.parseInt(warrantyPeriod), status, imagePath);

        boolean success = productDAO.addProduct(product);
        if (success) {
            response.sendRedirect("viewProduct");
        } else {
            request.setAttribute("errorMessage", "Thêm sản phẩm thất bại. Vui lòng thử lại.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("type", type);
            request.setAttribute("quantity", quantity);
            request.setAttribute("warrantyPeriod", warrantyPeriod);
            request.setAttribute("status", status);
            doGet(request, response);
        }
    }

}

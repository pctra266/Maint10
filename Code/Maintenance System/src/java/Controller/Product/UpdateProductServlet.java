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
public class UpdateProductServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProductServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO p = new ProductDAO();
        PrintWriter out = response.getWriter();

        Product product;
        List<String> productTypes = p.getDistinctProductTypes();
        List<Brand> listBrand = p.getAllBrands();
        int productId = Integer.parseInt(request.getParameter("id"));
        product = p.getProductById(productId);
        request.setAttribute("product", product);
        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listType", productTypes);

        request.getRequestDispatcher("Product/updateProduct1.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO p = new ProductDAO();

        String productIdParam = request.getParameter("pid");
        String productName = request.getParameter("productName");
        String brandIdParam = request.getParameter("brandId");
        String productCodeParam = request.getParameter("productCode");
        String productTypeParam = request.getParameter("type");
        String quantityParam = request.getParameter("quantity");
        String warrantyParam = request.getParameter("warrantyPeriod");
        String status = request.getParameter("status");

        Part imagePart = request.getPart("image");
        String imagePath = null;

        if (p.isProductCodeExists(productCodeParam)) {
            request.setAttribute("errorMessage", "Mã sản phẩm đã tồn tại. Vui lòng nhập mã khác.");
            reloadProductData(request, response, p, productIdParam, productName, brandIdParam, productCodeParam, productTypeParam, quantityParam, warrantyParam, status);
            return;
        }

        // Kiểm tra ảnh nếu có tải lên
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = imagePart.getSubmittedFileName();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".jpeg")) {
                request.setAttribute("errorMessage", "Only JPG, JPEG, and PNG files are allowed!");
                reloadProductData(request, response, p, productIdParam, productName, brandIdParam, productCodeParam, productTypeParam, quantityParam, warrantyParam, status);
                return;
            }
            imagePath = OtherUtils.saveImage(imagePart, request, "img/photos");
        } else {
            imagePath = request.getParameter("existingImage");
        }

        try {
            Product updatedProduct = new Product(
                    Integer.parseInt(productIdParam),
                    productCodeParam,
                    productName,
                    Integer.parseInt(brandIdParam),
                    productTypeParam,
                    Integer.parseInt(quantityParam),
                    Integer.parseInt(warrantyParam),
                    status,
                    imagePath
            );
            boolean success = p.updateProduct(updatedProduct);
            if (success) {
                response.sendRedirect("viewProduct");
            } else {
                request.setAttribute("errorMessage", "Failed to update product");
                reloadProductData(request, response, p, productIdParam, productName, brandIdParam, productCodeParam, productTypeParam, quantityParam, warrantyParam, status);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid input format!");
            reloadProductData(request, response, p, productIdParam, productName, brandIdParam, productCodeParam, productTypeParam, quantityParam, warrantyParam, status);
        }
    }

    private void reloadProductData(HttpServletRequest request, HttpServletResponse response, ProductDAO p,
            String productId, String productName, String brandId, String productCode,
            String productType, String quantity, String warranty, String status)
            throws ServletException, IOException {
        List<String> productTypes = p.getDistinctProductTypes();
        List<Brand> listBrand = p.getAllBrands();
        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listType", productTypes);

        Product product = new Product(
                Integer.parseInt(productId),
                productCode,
                productName,
                Integer.parseInt(brandId),
                productType,
                Integer.parseInt(quantity),
                Integer.parseInt(warranty),
                status,
                request.getParameter("existingImage")
        );

        request.setAttribute("product", product);
        request.getRequestDispatcher("Product/updateProduct1.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

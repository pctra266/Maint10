package Controller.Product;

import DAO.ProductDAO;
import Model.Brand;
import Model.Product;
import Utils.OtherUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024, // 2MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class UpdateProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO p = new ProductDAO();
        List<String> productTypes = p.getDistinctProductTypes();
        List<Brand> listBrand = p.getAllBrands();
        
        int productId = Integer.parseInt(request.getParameter("id"));
        Product product = p.getProductById(productId);

        request.setAttribute("product", product);
        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listType", productTypes);

        request.getRequestDispatcher("Product/updateProduct1.jsp").forward(request, response);
    }

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
        String existingImage = request.getParameter("existingImage");

        Part imagePart = request.getPart("image");
        String imagePath = existingImage;

        if (p.isProductCodeExists(productCodeParam, Integer.parseInt(productIdParam))) {
            request.setAttribute("errorMessage", "Mã sản phẩm đã tồn tại. Vui lòng nhập mã khác.");
            reloadProductData(request, response, p, productIdParam, productName, brandIdParam, productCodeParam, productTypeParam, quantityParam, warrantyParam, status, imagePath);
            return;
        }

        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = imagePart.getSubmittedFileName().toLowerCase();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".jpeg")) {
                request.setAttribute("errorMessage", "Only JPG, JPEG, and PNG files are allowed!");
                reloadProductData(request, response, p, productIdParam, productName, brandIdParam, productCodeParam, productTypeParam, quantityParam, warrantyParam, status, imagePath);
                return;
            }
            imagePath = OtherUtils.saveImage(imagePart, request, "img/photos");
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
                reloadProductData(request, response, p, productIdParam, productName, brandIdParam, productCodeParam, productTypeParam, quantityParam, warrantyParam, status, imagePath);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid input format!");
            reloadProductData(request, response, p, productIdParam, productName, brandIdParam, productCodeParam, productTypeParam, quantityParam, warrantyParam, status, imagePath);
        }
    }

    private void reloadProductData(HttpServletRequest request, HttpServletResponse response, ProductDAO p,
            String productId, String productName, String brandId, String productCode,
            String productType, String quantity, String warranty, String status, String imagePath)
            throws ServletException, IOException {
        List<String> productTypes = p.getDistinctProductTypes();
        List<Brand> listBrand = p.getAllBrands();

        Product product = new Product(
                Integer.parseInt(productId),
                productCode,
                productName,
                Integer.parseInt(brandId),
                productType,
                Integer.parseInt(quantity),
                Integer.parseInt(warranty),
                status,
                imagePath
        );

        request.setAttribute("product", product);
        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listType", productTypes);
        request.getRequestDispatcher("Product/updateProduct1.jsp").forward(request, response);
    }
}

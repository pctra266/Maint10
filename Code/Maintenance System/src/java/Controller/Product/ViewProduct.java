package Controller.Product;

import DAO.ProductDAO;
import Model.Brand;
import Model.Product;
import Model.ProductType;
import Utils.OtherUtils;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024, // 2MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class ViewProduct extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            loadProductForUpdate(request, response);
        } else if ("add".equals(action)) {
            loadAddProductPage(request, response);
        } else if ("delete".equals(action)) {
            deleteProduct(request, response);
        } else {
            loadProductList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        }
    }

    // ========== [1] Hiển thị danh sách sản phẩm ==========
    private void loadProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Brand> listBrand = productDAO.getAllBrands();
        List<ProductType> listType = productDAO.getAllProductTypes();

        String errorMessage = null;

        String brandIdParam = request.getParameter("brandId");
        String searchName = request.getParameter("searchName") != null ? request.getParameter("searchName").trim() : "";
        String searchCode = request.getParameter("searchCode") != null ? request.getParameter("searchCode").trim() : "";

        String typeIdParam = request.getParameter("type");
        String sortQuantity = request.getParameter("sortQuantity") != null ? request.getParameter("sortQuantity").trim() : "";
        String sortWarranty = request.getParameter("sortWarranty") != null ? request.getParameter("sortWarranty").trim() : "";

        Integer brandId = (brandIdParam != null && !brandIdParam.isEmpty()) ? Integer.parseInt(brandIdParam) : null;
        Integer typeId = (typeIdParam != null && !typeIdParam.isEmpty()) ? Integer.parseInt(typeIdParam) : null;

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
                recordsPerPage = 8;
            }
        }

        int offset = (page - 1) * recordsPerPage;

        List<Product> productList = productDAO.searchProducts(searchName, searchCode, brandId, typeId, sortQuantity, sortWarranty, offset, recordsPerPage);
        int totalRecords = productDAO.getTotalProducts(searchName, searchCode, brandId, typeId);
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
        request.setAttribute("productTypeId", typeId);
        request.setAttribute("listBrand", listBrand);
        request.setAttribute("sortQuantity", sortQuantity);
        request.setAttribute("sortWarranty", sortWarranty);
        request.setAttribute("listType", listType);
        request.setAttribute("recordsPerPage", recordsPerPage);

//        PrintWriter out = response.getWriter();
//        for (ProductType p : listType) {
//            out.println(p.getProductTypeId());
//            out.println(p.getTypeName());
//        }
        request.getRequestDispatcher("Product/viewProduct.jsp").forward(request, response);
    }

    // ========== [2] Hiển thị form thêm sản phẩm ==========
    private void loadAddProductPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listBrand", productDAO.getAllBrands());
        request.setAttribute("listType", productDAO.getAllProductTypes());
        request.getRequestDispatcher("Product/addProduct.jsp").forward(request, response);
    }

    // ========== [3] Hiển thị thông tin cập nhật sản phẩm ==========
    private void loadProductForUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.getProductById(productId);

        request.setAttribute("product", product);
        request.setAttribute("listBrand", productDAO.getAllBrands());
        request.setAttribute("listType", productDAO.getAllProductTypes());

        request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
    }

    // ========== [4] Thêm sản phẩm ==========
    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        int brandId = Integer.parseInt(request.getParameter("brandId"));
        int typeId = Integer.parseInt(request.getParameter("type"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));
        String status = request.getParameter("status");
        Part imagePart = request.getPart("image");

        // Kiểm tra mã sản phẩm đã tồn tại chưa
        if (productDAO.isProductCodeExists(code)) {
            request.setAttribute("errorMessage", "Mã sản phẩm đã tồn tại. Vui lòng nhập mã khác.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("productTypeId", typeId);
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
            request.setAttribute("productTypeId", typeId);
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
            request.setAttribute("productTypeId", typeId);
            request.setAttribute("quantity", quantity);
            request.setAttribute("warrantyPeriod", warrantyPeriod);
            request.setAttribute("status", status);
            doGet(request, response);
            return;
        }

        // Lưu ảnh nếu hợp lệ
        String imagePath = OtherUtils.saveImage(imagePart, request, "img/photos");
        Product product = new Product(code, name, brandId, quantity, warrantyPeriod, status, imagePath, typeId);

        boolean success = productDAO.addProduct(product);
        if (success) {
            response.sendRedirect("viewProduct");
        } else {
            request.setAttribute("errorMessage", "Thêm sản phẩm thất bại. Vui lòng thử lại.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("productTypeId", typeId);
            request.setAttribute("quantity", quantity);
            request.setAttribute("warrantyPeriod", warrantyPeriod);
            request.setAttribute("status", status);
            doGet(request, response);
        }

    }

    // ========== [5] Cập nhật sản phẩm ==========
    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("pid"));
        String productCode = request.getParameter("productCode");
        String name = request.getParameter("productName");
        int brandId = Integer.parseInt(request.getParameter("brandId"));
        int typeId = Integer.parseInt(request.getParameter("type"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int warranty = Integer.parseInt(request.getParameter("warrantyPeriod"));
        String status = request.getParameter("status");
        String existingImage = request.getParameter("existingImage");

        Part imagePart = request.getPart("image");
        String imagePath = existingImage;

        if (productDAO.isProductCodeExists(productCode, productId)) {
            request.setAttribute("errorMessage", "Mã sản phẩm đã tồn tại. Vui lòng nhập mã khác.");
            reloadProductData(request, response, productDAO, productId, name, brandId, productCode, typeId, quantity, warranty, status, imagePath);
            return;
        }

        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = imagePart.getSubmittedFileName().toLowerCase();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".jpeg")) {
                request.setAttribute("errorMessage", "Only JPG, JPEG, and PNG files are allowed!");
                reloadProductData(request, response, productDAO, productId, name, brandId, productCode, typeId, quantity, warranty, status, imagePath);
                return;
            }
            imagePath = OtherUtils.saveImage(imagePart, request, "img/photos");
        }
 
        try {
            Product updatedProduct = new Product(
                    productId,
                    productCode,
                    name,
                    brandId,
                    quantity,
                    warranty,
                    status,
                    imagePath,
                    typeId
            );
            boolean success = productDAO.updateProduct(updatedProduct);
            if (success) {
                response.sendRedirect("viewProduct");
            } else {
                request.setAttribute("errorMessage", "Failed to update product");
                reloadProductData(request, response, productDAO, productId, name, brandId, productCode, typeId, quantity, warranty, status, imagePath);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid input format!");
            reloadProductData(request, response, productDAO, productId, name, brandId, productCode, typeId, quantity, warranty, status, imagePath);
        }
    }

    private void reloadProductData(HttpServletRequest request, HttpServletResponse response, ProductDAO p,
            Integer productId, String productName, Integer brandId, String productCode,
            Integer productTypeId, Integer quantity, Integer warranty, String status, String imagePath)
            throws ServletException, IOException {
        List<ProductType> productTypes = p.getAllProductTypes();
        List<Brand> listBrand = p.getAllBrands();

        Product product = new Product(
                productId,
                productCode,
                productName,
                brandId,
                quantity,
                warranty,
                status,
                imagePath,
                productTypeId
        );

        request.setAttribute("product", product);
        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listType", productTypes);
        request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
    }

    // ========== [6] Xóa sản phẩm ==========
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        if (productDAO.deactivateProduct(productId)) {
            response.sendRedirect("viewProduct");
        } else {
            request.setAttribute("errorMessage", "Xóa sản phẩm thất bại.");
            loadProductList(request, response);
        }
    }
}
